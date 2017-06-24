package eshop.common.data_objects;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class Artikel {
	
	/**
	 * @param bezeichnung	Bezeichnung / Name des Artikels
	 * @param artikelnummer	Eindeutige Artikelnummer
	 * @param bestand	Aktueller Bestand des Artikels
	 * @param preis	Preis des Artikels
	 */
	public Artikel(String bezeichnung, int artikelnummer, int bestand, double preis, Map<Integer,Integer> bestandsverlauf) {
		super();
		this.bezeichnung = bezeichnung;
		this.artikelnummer = artikelnummer;
		this.bestand = bestand;
		this.preis = preis;

		if(bestandsverlauf != null){
			this.bestandsverlauf = bestandsverlauf;
		} 
		this.aktualisiereBestandsverlauf();

	}
	
	/**
	 * @param bezeichnung	Bezeichnung / Name des Artikels
	 * @param artikelnummer	Eindeutige Artikelnummer
	 * @param bestand	Aktueller Bestand des Artikels
	 * @param preis	Preis des Artikels
	 * @param kategorie	Kategorie des Artikels
	 * @param angebot	Zeigt an, ob Artikel aktuell im Angebot ist
	 * @param bewertung	Nuzterbewertung des Artikels
	 */
	public Artikel(String bezeichnung, int artikelnummer, int bestand, double preis, String kategorie, boolean angebot,
			int bewertung) {
		super();
		this.bezeichnung = bezeichnung;
		this.artikelnummer = artikelnummer;
		this.bestand = bestand;
		this.preis = preis;
		
		/* Noch nicht verwendet
		this.kategorie = kategorie;
		this.angebot = angebot;
		this.bewertung = bewertung;
		*/
	}
	
	private String bezeichnung;
	private int artikelnummer;
	private int bestand;
	private double preis;
	
	private Map<Integer,Integer> bestandsverlauf = new LinkedHashMap<>();
	
	/* Nocht nicht verwendet
	private String kategorie;
	private boolean angebot;
	private int bewertung;
	*/
	
	
	/**
	 * @return Gibt die Bezeichnung des Artikels aus
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Setzt die Bezeichnung des Artikel
	 * @param bezeichnung Gewuenschte Artikelbezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * @return
	 */
	public int getArtikelnummer() {
		return artikelnummer;
	}

	/**
	 * @param artikelnummer
	 */
	public void setArtikelnummer(int artikelnummer) {
		this.artikelnummer = artikelnummer;
	}

	/**
	 * @return
	 */
	public int getBestand() {
		return bestand;
	}

	/**
	 * @param bestand
	 */
	public void setBestand(int bestand) {
		this.bestand = bestand;
	}

	/**
	 * @return
	 */
	public double getPreis() {
		return preis;
	}

	/**
	 * @param preis
	 */
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	public void aktualisiereBestandsverlauf(){
		
		if (bestandsverlauf.size() >= 30){
			
			bestandsverlauf.remove(0);
			
		} 
					
		int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		bestandsverlauf.put(dayOfYear, bestand);
		
	}
	
	public Map<Integer,Integer> getBestandsverlauf(){
		return bestandsverlauf;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return artikelnummer  + " | " + bezeichnung + " | " + preis + " | " + bestand; 
	}
}