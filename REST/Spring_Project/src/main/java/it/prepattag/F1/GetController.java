package it.prepattag.F1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import orm.dao.F1_DAO_Implements;
import orm.dao.Paio;
import orm.entity.Constructor;
import orm.entity.Driver;
import orm.entity.Race;
import orm.entity.Result;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("get")
/**
 * Classe che gestisce le richieste get (/get/<attributo>) della API
 */
public class GetController {

    F1_DAO_Implements impl = new F1_DAO_Implements();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Mapping per la richiesta delle informazioni di un pilota
     *
     * @param id l'id del pilota
     * @return Le informazioni del pilota richiesto in formato JSON
     */
    @RequestMapping(value = "driver", params = "id")
    public Map driver(@RequestParam("id") int id) {
        HashMap<String, Object> m = new HashMap(12);
        Driver d = impl.infoPilota(id);
        if (d != null) {
            long dob = d.getDob().getTime();
            long diff = Date.valueOf(LocalDate.now()).getTime() - dob;
            int age = (int) Math.floor(diff / 3.15576e+10);
            HashMap<Race, Integer> gare = impl.garePerPilota(d);
            int podi = (int) gare.entrySet().stream()
                    .filter(pos -> pos.getValue() >= 1 && pos.getValue() <= 3)
                    .count();
            int garevinte = (int) gare.entrySet().stream()
                    .filter(pos -> pos.getValue() == 1)
                    .count();
            int annocorrente = LocalDate.now().getYear();

            Constructor c = impl.costruttorePilota(d, annocorrente);

            m.put("age", age);
            m.put("id", d.getDriverId());
            m.put("forename", d.getForename());
            m.put("surname", d.getSurname());
            m.put("nationality", d.getNationality());
            m.put("scuderia", c == null ? null : c.getName());
            m.put("numeropodi", podi);
            m.put("punti", impl.puntiPilotaS(d.getDriverId(), annocorrente));
            m.put("datanascita", format.format(d.getDob()));
            m.put("numerogare", gare.size());
            m.put("granpremivinti", garevinte);
        } else {
            m.put("error", "Id inesistente (id test: 10 | Development purpose only)");
        }
        return m;
    }

    /**
     * Mapping per la richiesta di tutti i piloti
     *
     * @return Un array di piloti in formato JSON
     */
    @RequestMapping("drivers/{anno}")
    public HashMap[] drivers(@PathVariable int anno) {
        ArrayList<Integer> idPiloti = impl.pilotiAnno(anno);
        HashMap<String, Object>[] arr = new HashMap[idPiloti.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new HashMap<>();
            Driver d = impl.infoPilota(idPiloti.get(i));
            long dob = d.getDob().getTime();
            long diff = Date.valueOf(LocalDate.now()).getTime() - dob;
            int age = (int) Math.floor(diff / 3.15576e+10);
            HashMap<Race, Integer> gare = impl.garePerPilota(d);
            Race lastrace = (Race) gare.keySet().toArray()[gare.keySet().size() - 1];
            Result res = impl.infoRisultato(lastrace.getRaceId(), d.getDriverId());
            int garevinte = (int) gare.entrySet().stream()
                    .filter(pos -> pos.getValue() == 1)
                    .count();
            int podi = (int) gare.entrySet().stream()
                    .filter(pos -> pos.getValue() >= 1 && pos.getValue() <= 3)
                    .count();

            arr[i].put("age", age);
            arr[i].put("id", d.getDriverId());
            arr[i].put("forename", d.getForename());
            arr[i].put("surname", d.getSurname());
            arr[i].put("nationality", d.getNationality());
            arr[i].put("scuderia", impl.infoCostruttore(res.getConstructorId()).getName());
            arr[i].put("granpremivinti", garevinte);
            arr[i].put("numerogare", gare.size());
            arr[i].put("numeropodi", podi);
            arr[i].put("numero", d.getNumber());
        }
        return arr;
    }

    /**
     * Mapping per la richiesta della classifica dei piloti in base all'anno
     * (posizione nome cognome punteggio, id, id scuderia)
     *
     * @return Un array con n piloti in ordine di punteggio
     */
    @RequestMapping("classificapiloti/{anno}")
    public HashMap[] classificapiloti(@PathVariable int anno) {
        ArrayList<Paio<Integer, Integer>> idPiloti = impl.classificaPilotiS(anno);
        HashMap<String, Object>[] m = new HashMap[idPiloti.size()];
        for (int i = 0; i < m.length; i++) {
            Driver d = impl.infoPilota(idPiloti.get(i).first);
            m[i] = new HashMap<>();
            m[i].put("id", d.getDriverId());
            m[i].put("nome", d.getForename());
            m[i].put("cognome", d.getSurname());
            m[i].put("scuderia", impl.costruttorePilota(d, anno).getName());
            m[i].put("punteggio", idPiloti.get(i).second);
            m[i].put("nazionalita", d.getNationality());
        }
        return m;
    }

    /**
     * Mapping per la richiesta della classifica delle scuderie in base all'anno
     *
     * @return Un array con le n scuderie in ordine di punteggio
     */
    @RequestMapping("classificascuderie/{anno}")
    public HashMap[] classificascuderie(@PathVariable int anno) {
        ArrayList<Paio<Integer, Integer>> classifica = impl.classificaCostruttoriS(anno);
        HashMap<String, Object>[] m = new HashMap[classifica.size()];
        for (int i = 0; i < m.length; i++) {
            Constructor c = impl.infoCostruttore(classifica.get(i).first);
            m[i] = new HashMap<>();
            m[i].put("nome", c.getName());
            m[i].put("punteggio", classifica.get(i).second);
            m[i].put("id", c.getConstructorId());
        }
        return m;
    }

    /**
     * Mapping per la richiesta di tutti i costruttori
     *
     * @return Un array con id costruttore, nome, compionati vinti dal
     * costruttore, sede, manager, numero di poleposition, [nome cognome pilota
     * 1, nome cognome pilota 2]
     */
    @RequestMapping("scuderie/{anno}")
    public ArrayList<HashMap<String, Object>> scuderie(@PathVariable int anno) {
        ArrayList<Integer> ids = impl.indiciTabella("constructors");
        ArrayList<HashMap<String, Object>> m = new ArrayList<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            Constructor c = impl.infoCostruttore(ids.get(i));
            ArrayList<Driver> l = impl.pilotiCostruttore(c, anno);
            String[] piloti = new String[l.size()];
            for (int j = 0; j < piloti.length; j++) {
                piloti[j] = l.get(j).getForename() + " " + l.get(j).getSurname();
            }
            if (piloti.length != 0) //Se la scuderia non ha piloti significa che non gareggia in quest'anno quindi non va messa nell'array
            {
                m.add(new HashMap<>());
                HashMap t = m.get(m.size() - 1);
                t.put("id", c.getConstructorId());
                t.put("nome", c.getName());
                t.put("campionativinti", granPremiVintiScuderia(l, anno));
                t.put("piloti", piloti);
            }
        }
        return m;
    }

    /**
     * Mapping per la richiesta delle informazioni di un costruttore
     *
     * @param id l'id del costruttore / campionati vinti nazione
     * @return Le informazioni del costruttore richiesto in formato JSON
     */
    @RequestMapping("scuderia")
    public HashMap<String, Object> scuderia(@RequestParam("id") int id, @RequestParam("anno") int anno) {
        HashMap<String, Object> m = new HashMap();
        Constructor c = impl.infoCostruttore(id);
        ArrayList<Driver> l = impl.pilotiCostruttore(c, anno);
        String[] piloti = new String[l.size()];
        for (int j = 0; j < piloti.length; j++) {
            piloti[j] = l.get(j).getForename() + " " + l.get(j).getSurname();
        }
        m.put("piloti", piloti);
        m.put("id", c.getConstructorId());
        m.put("nome", c.getName());
        m.put("nazione", c.getNationality());
        m.put("granpremivinti", granPremiVintiScuderia(l, anno));
        return m;
    }

    private int granPremiVintiScuderia(ArrayList<Driver> d, int anno) {
        int gpv = 0;
        for (Driver i : d) {
            HashMap<Race, Integer> gare = impl.garePerPilota(i);
            gpv += (int) gare.entrySet().stream()
                    .filter(pos -> pos.getValue() == 1)
                    .count();
        }
        return gpv;
    }

    /**
     * Mapping per la richiesta dei tracciati, dei luoghi in cui si trovano,
     * nome, giorni mancanti al prossimo gran premio e descrizione del circuito
     *
     * @return Un array con luogo tracciato, nome circuito, data prossimo gran
     * premio e descrizione del percorso
     */
    @RequestMapping("tracciati")
    public HashMap[] tracciati() {
        HashMap<String, Object>[] m = new HashMap[21];
        for (int i = 0; i < m.length; i++) {
            m[i] = new HashMap<>();
            m[i].put("id", 12);
            m[i].put("luogo", "Australia");
            m[i].put("circuito", "Melbourne Gran Prix Circuit");
            m[i].put("data", "21");
            m[i].put("descr", "Il percorso si snoda nel cuore della città di Melbourne ed è ricavato raccordando le strade perimetrali del lago artificiale ricavato nell'Albert Park. Negli altri giorni è adibito alla normale circolazione stradale, eppure l'asfalto dell'Albert Park è tra i meno sconnessi dell'intero Circus.");
        }
        return m;
    }

    @RequestMapping("tracciato")
    public HashMap tracciato(@RequestParam int id) {
        HashMap<String, Object> m = new HashMap();
        m.put("luogo", "Australia");
        m.put("circuito", "Melbourne Gran Prix Circuit");
        m.put("data", "21");
        m.put("descr", "Il percorso si snoda nel cuore della città di Melbourne ed è ricavato raccordando le strade perimetrali del lago artificiale ricavato nell'Albert Park. Negli altri giorni è adibito alla normale circolazione stradale, eppure l'asfalto dell'Albert Park è tra i meno sconnessi dell'intero Circus.");
        return m;
    }

    @RequestMapping("classificagara")
    public HashMap[] classificaGara(@RequestParam int anno, @RequestParam String tracciato) {
        ArrayList<Paio<Integer, Integer>> cl = impl.risultatoGara(tracciato, anno);
        HashMap<String, Object>[] m = new HashMap[cl.size()];
        for (int i = 0; i < m.length; i++) {
            Driver d = impl.infoPilota(cl.get(i).first);
            Constructor c = impl.costruttorePilota(d, anno);
            m[i] = new HashMap<>();
            m[i].put("id", d.getDriverId());
            m[i].put("nome", d.getForename());
            m[i].put("cognome", d.getSurname());
            m[i].put("scuderia", c.getName());
            m[i].put("punteggio", cl.get(i).second);
            m[i].put("nazionalita", d.getNationality());
        }
        return m;
    }
}
