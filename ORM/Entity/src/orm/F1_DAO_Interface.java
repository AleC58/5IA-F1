package f1_dao;

import java.util.ArrayList;
import entity.*;
import java.util.HashMap;

/*************************************************************************
	6 QUERY :
	--> Classifica Piloti (per Stagione o complessivo)
	--> Classifica Costruttori per stagione
	--> Dato un costruttore, sapere chi gareggia in una determinata gara
	--> Data una gara, sapere l'ordine di arrivo dei piloti
	--> Dato un pilota, sapere i suoi punteggi dal più recente al meno recente
	--> Dato un pilota, sapere a quante gare ha partecipato 
 *************************************************************************/
public interface F1_DAO_Interface {

	abstract HashMap<Driver, Integer> classificaPilotiS(int anno); //--> Classifica Piloti (per Stagione o complessivo)

	abstract HashMap<Constructor, Integer> classificaCostruttoriS(int anno); //--> Classifica Costruttori per stagione

	abstract ArrayList<Driver> pilotiCostruttore(Constructor costruttore, int anno); //--> Dato un costruttore, sapere chi gareggia in una determinata gara

	abstract ArrayList<Driver> classificaGara(Race corsa); //--> Data una gara, sapere l'ordine di arrivo dei piloti

	abstract HashMap<Race, Integer> punteggiPerGara(Driver pilota, int anno); //--> Dato un pilota, sapere i suoi punteggi dal più recente  al meno recente
	
	abstract HashMap<Race, Integer> garePerPilota(Driver pilota); //--> Dato un pilota, sapere a quante gare ha partecipato 

}
