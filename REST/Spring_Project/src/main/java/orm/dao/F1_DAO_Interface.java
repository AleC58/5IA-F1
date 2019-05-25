package orm.dao;

import java.util.ArrayList;
import orm.entity.*;
import java.util.HashMap;

/*************************************************************************
	6 QUERY :
	--> Classifica Piloti (per Stagione o complessivo)
	--> Classifica Costruttori per stagione
	--> Dato un costruttore, sapere chi gareggia in una determinata gara
	--> Data una gara, sapere l'ordine di arrivo dei piloti
	--> Dato un pilota, sapere i suoi punteggi dal più recente al meno recente
	--> Dato un pilota, sapere a quante gare ha partecipato 
	--> Dato un pilota e l'anno, trovare il costruttore
 *************************************************************************/
public interface F1_DAO_Interface {

	abstract HashMap<Driver, Integer> classificaPilotiS(int anno); //--> Classifica Piloti (per Stagione o complessivo)

	abstract HashMap<Constructor, Integer> classificaCostruttoriS(int anno); //--> Classifica Costruttori per stagione

	abstract ArrayList<Driver> pilotiCostruttore(Constructor costruttore, int anno); //--> Dato un costruttore, sapere chi gareggia in una determinata gara

	abstract ArrayList<Driver> classificaGara(Race corsa); //--> Data una gara, sapere l'ordine di arrivo dei piloti

	abstract HashMap<Race, Integer> punteggiPerGara(Driver pilota, int anno); //--> Dato un pilota, sapere i suoi punteggi dal più recente  al meno recente
	
	abstract HashMap<Race, Integer> garePerPilota(Driver pilota); //--> Dato un pilota, sapere a quante gare ha partecipato 
	
	abstract Constructor costruttorePilota(Driver pilota, int anno); //--> Dato un Pilota restituisce il costruttore dell'anno
	
	abstract Driver infoPilota(int indice); //--> Dato l'id restituisce il pilota completo
	
	abstract Constructor infoCostruttore(int indice); //--> Dato l'id restituisce il costruttore completo
	
	abstract Race infoCorsa(int indice); //--> Dato l'id restituisce la corsa completa
	
	abstract Result infoRisultato(int indice); //--> Dato l'id restituisce il risultato completo
	
	abstract Circuit infoCircuito(int indice); //--> Dato l'id restituisce il circuito completo

	abstract ArrayList<Integer> indiciTabella(String tab); //--> dato il nome di una tabella restituisce tutti gli indici
	
	abstract Result infoRisultato(int indiceR, int indiceD); //--> Dato l'idDriver e idRace restituisce il risultato completo
}
