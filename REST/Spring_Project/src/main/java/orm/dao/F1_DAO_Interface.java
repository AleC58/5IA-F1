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

	abstract ArrayList<Paio<Integer, Integer>> classificaPilotiS(int anno); //--> Classifica Piloti (per Stagione o complessivo)
        
        abstract int puntiPilotaS(int pilotaId, int anno); //--> Punteggio di un singolo pilota di un dato anno
        
        abstract ArrayList<Integer> pilotiAnno(int anno); //--> Restituisce id piloti in un anno
        
        abstract ArrayList<Paio<Integer, Integer>> gareAnno(int anno); //--> Restituisce id gare in un anno
        
        abstract ArrayList<Paio<Integer, Integer>> classificaCostruttoriS(int anno); //--> Classifica dei costruttori
                
        abstract ArrayList<Paio<Integer, Integer>> risultatoGara(String nome, int anno); //--> Risultato di una certa gara     
        
        abstract ArrayList<Race> gareDaDisputare(); //--> Restituisce la lista delle gare da disputare
        
        abstract ArrayList<Driver> pilotiCostruttore(Constructor costruttore, int anno); //--> Restituisce i piloti di un costruttore di un anno
        
        abstract Constructor costruttorePilota(Driver pilota, int anno); //--> Restituisce il costruttore di un pilota
        
        abstract Driver infoPilota(int indice); //--> Restituisce le info del pilota
        
        abstract Constructor infoCostruttore(int indice); //--> Dato l'id restituisce il costruttore completo
	
	abstract Race infoCorsa(int indice); //--> Dato l'id restituisce la corsa completa
	
	abstract Result infoRisultato(int indice); //--> Dato l'id restituisce il risultato completo
	
	abstract Circuit infoCircuito(int indice); //--> Dato l'id restituisce il circuito completo

	abstract ArrayList<Integer> indiciTabella(String tab); //--> dato il nome di una tabella restituisce tutti gli indici
	
	abstract Result infoRisultato(int indiceR, int indiceD); //--> Dato l'idDriver e idRace restituisce il risultato completo

	abstract ArrayList<Driver> classificaGara(Race corsa); //--> Data una gara, sapere l'ordine di arrivo dei piloti

	abstract HashMap<Race, Integer> punteggiPerGara(Driver pilota, int anno); //--> Dato un pilota, sapere i suoi punteggi dal più recente  al meno recente
	
	abstract HashMap<Race, Integer> garePerPilota(Driver pilota); //--> Dato un pilota, sapere a quante gare ha partecipato 
	
	
	/*
	abstract ArrayList<User> utente(); //--> restituisce tutti gli utenti presenti nel database per controllo su username e password
	
	abstract boolean aggiungiUtente(String nome, String cognome, String username, String password); //--> registra un nuovo utente
	*/

}
