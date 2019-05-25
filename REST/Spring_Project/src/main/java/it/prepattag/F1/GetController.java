package it.prepattag.F1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("get")
/**
 * Classe che gestisce le richieste get (/get/<attributo>) della API
 */
public class GetController {

    /**
     * Mapping per la richiesta delle informazioni di un pilota
     *
     * @param id l'id del pilota
     * @return Le informazioni del pilota richiesto in formato JSON
     */
    @RequestMapping(value = "driver", params = "id")
    public Map driver(@RequestParam("id") int id) {
        HashMap<String, Object> m = new HashMap(10);
        if (id == 10) {
            long dob = Date.valueOf("2000-07-15").getTime();
            long diff = Date.valueOf(LocalDate.now()).getTime() - dob;
            int age = (int) Math.floor(diff / 3.15576e+10);
            m.put("age", age);
            m.put("id", 10);
            m.put("forename", "Andrea");
            m.put("surname", "Crocco");
            m.put("nationality", "IT");
            m.put("scuderia", "Ferrari");
            m.put("numeropodi", "15");
            m.put("punti", "32");
            m.put("luogonascita", "Bologna");
            m.put("datanascita", "15/05/1988");
            m.put("numerogare", "65");
            m.put("granpremivinti", "13");
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
    @RequestMapping("drivers")
    public HashMap[] drivers() {
        
        HashMap<String, Object>[] arr = new HashMap[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new HashMap<>();
            arr[i].put("age", 18);
            arr[i].put("id", 10);
            arr[i].put("forename", "Andrea");
            arr[i].put("surname", "Crocco");
            arr[i].put("nationality", "IT");
            arr[i].put("scuderia", "Ferrari");
            arr[i].put("granpremivinti", "13");
            arr[i].put("numerogare", "65");
            arr[i].put("numeropodi", "15");
        }
        return arr;
    }

    /**
     * Mapping per la richiesta della classifica dei piloti in base all'anno (posizione nome
     * cognome punteggio, id, id scuderia)
     *
     * @return Un array con n piloti in ordine di punteggio
     */
    @RequestMapping("classificapiloti/{anno}")
    public HashMap[] classificapiloti() {
        HashMap<String, Object>[] m = new HashMap[10];
        for (int i = 0; i < m.length; i++) {
            m[i] = new HashMap<>();
            m[i].put("id", 10);
            m[i].put("idscuderia", 1);
            m[i].put("nome", "Andrea");
            m[i].put("cognome", "Crocco");
            m[i].put("posizione", "3");
            m[i].put("scuderia", "Ferrari");
            m[i].put("punteggio", "121");
            m[i].put("nazionalita", "Romania");
        }
        return m;
    }

    /**
     * Mapping per la richiesta della classifica delle scuderie in base all'anno
     *
     * @return Un array con le n scuderie in ordine di punteggio
     */
    @RequestMapping("classificascuderie/{anno}")
    public HashMap[] classificascuderie() {
        HashMap<String, Object>[] m = new HashMap[10];
        for (int i = 0; i < m.length; i++) {
            m[i] = new HashMap<>();
            m[i].put("nome", "Ferrari");
            m[i].put("punteggio", 155);
            m[i].put("id", 1);
        }
        return m;
    }

    /**
     * Mapping per la richiesta di tutti i costruttori
     *
     * @return Un array con id costruttore, nome, compionati vinti dal
     * costruttore, sede, manager, numero di poleposition, [nome cognome pilota 1, nome cognome pilota 2]
     */
    @RequestMapping("costruttori")
    public HashMap[] costruttori() {
        HashMap<String, Object>[] m = new HashMap[21];
        for (int i = 0; i < m.length; i++) {
            m[i] = new HashMap<>();
            m[i].put("id", 1);
            m[i].put("campionativinti", "15");
            m[i].put("nome", "Lamborghini");
            m[i].put("sede", "Campagna Lupia");
            m[i].put("manager", "Tony Buerin");
            m[i].put("poleposition", "15");
            m[i].put("piloti", new String[]{"Andrea Crocco", "Cuin Luca"});

        }
        return m;
    }

    /**
     * Mapping per la richiesta delle informazioni di un costruttore
     *
     * @param id l'id del costruttore
     * @return Le informazioni del costruttore richiesto in formato JSON
     */
    @RequestMapping(value = "costruttore", params = "id")
    public Map costruttore(@RequestParam("id") int id) {
        HashMap<String, Object> m = new HashMap(10);
        if (id == 1) {
            long dob = Date.valueOf("2000-07-15").getTime();
            long diff = Date.valueOf(LocalDate.now()).getTime() - dob;
            int age = (int) Math.floor(diff / 3.15576e+10);
            m.put("id", 1);
            m.put("campionativinti", "15");
            m.put("nome", "Lamborghini");
            m.put("sede", "Campagna Lupia");
            m.put("manager", "Tony Buerin");
            m.put("poleposition", "15");
        } else {
            m.put("error", "Id inesistente (id test: 10 | Development purpose only)");
        }
        return m;
    }

    /**
     * Mapping per la richiesta dei tracciati, dei luoghi in cui si trovano,
     * nome, giorni mancanti al prossimo gran premio e descrizione del circuito
     *
     * @return Un array con luogo tracciato, nome circuito, data prossimo gran
     * premio e descrizione del percorso
     */
    @RequestMapping("tracciato")
    public HashMap[] tracciato() {
        HashMap<String, Object>[] m = new HashMap[21];
        for (int i = 0; i < m.length; i++) {
            m[i] = new HashMap<>();
            m[i].put("luogo", "Australia");
            m[i].put("circuito", "Melbourne Gran Prix Circuit");
            m[i].put("data", "21");
            m[i].put("descr", "Il percorso si snoda nel cuore della città di Melbourne ed è ricavato raccordando le strade perimetrali del lago artificiale ricavato nell'Albert Park. Negli altri giorni è adibito alla normale circolazione stradale, eppure l'asfalto dell'Albert Park è tra i meno sconnessi dell'intero Circus.");
        }
        return m;
    }

}
