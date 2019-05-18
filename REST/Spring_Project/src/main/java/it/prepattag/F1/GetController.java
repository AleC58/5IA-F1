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
		} else {
			m.put("error", "Id inesistente");
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
		}
		return arr;
	}

	/**
	 * Mapping per la richiesta della classifica dei piloti (posizione nome
	 * cognome scuderia urlprofilo punteggio)
	 *
	 * @return Un array con n piloti in ordine di punteggio
	 */
	@RequestMapping("classificapiloti")
	public HashMap[] classificapiloti() {
		HashMap<String, Object>[] m = new HashMap[10];
		for (int i = 0; i < m.length; i++) {
			m[i] = new HashMap<>();
			m[i].put("nome", "Andrea");
			m[i].put("cognome", "Crocco");
			m[i].put("posizione", "3");
			m[i].put("scuderia", "Ferrari");
			m[i].put("punteggio", "121");
			m[i].put("urlprofilo", "http:\\localhost\\url");
			m[i].put("urlscuderia", "http:\\localhost\\urlscud");
			m[i].put("nazionalita", "Romania");
		}
		return m;
	}

	/**
	 * Mapping per la richiesta della classifica generale (non annuale) delle
	 * scuderie dato il numero di posizioni da visualizzare
	 *
	 * @return Un array con le n scuderie in ordine di punteggio
	 */
	@RequestMapping("classificascuderie")
	public HashMap[] classificascuderie() {
		HashMap<String, Object>[] m = new HashMap[10];
		for (int i = 0; i < m.length; i++) {
			m[i] = new HashMap<>();
			m[i].put("nome", "Ferrari");
			m[i].put("posizione", "1");
			m[i].put("punteggio", "155");
		}
		return m;
	}

	/**
	 * Mapping per la richiesta di piloti e relativi premi vinti, podi, gare
	 * disputate
	 *
	 * @return Un array con idPilota [da cui ricavare info pilota e scuderia
	 * tramite mapping Drivers(id)], granpremi vinti, podi e numero gare
	 * effettuate
	 */
	@RequestMapping("pilotipremipodi")
	public HashMap[] pilotipremipodi() {
		HashMap<String, Object>[] m = new HashMap[21];
		for (int i = 0; i < m.length; i++) {
			m[i] = new HashMap<>();
			m[i].put("idpilota", "1");
			m[i].put("granpremivinti", "13");
			m[i].put("numeropodi", "15");
			m[i].put("numerogare", "65");
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
