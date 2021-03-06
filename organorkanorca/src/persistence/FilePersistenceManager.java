package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import data_objects.*;

/**
 * @author teschke
 *
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 * @see bib.local.persistence.PersistenceManager
 */
public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#openForReading(java.lang.String)
	 */
	public void openForReading(String datei) throws FileNotFoundException {
		try {
			reader = new BufferedReader(new FileReader(datei));
		} catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(datei);
		}
	}

	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#openForWriting(java.lang.String)
	 */
	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	/* (non-Javadoc)
	 * @see persistence.PersistenceManager#close()
	 */
	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}

	/**
	 * Methode zum Einlesen der Artikeldaten aus einer externen Datenquelle.
	 * Das Verfügbarkeitsattribut ist in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @return Artikel-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException {
		/*
		//Artikel-Header suchen
		while(!liesZeile().equals("<---ARTIKEL--->"));{}
		*/
		
		int artikelnummer = 0;
		String bezeichnung = "";
		double preis = 0;
		int bestand = 0;
		int packungsgroesse = 0;
		
		//Lies Artikelnummer
		try{
			artikelnummer = Integer.parseInt(liesZeile());
		} catch (NumberFormatException nfe) {
			//Abbruch wenn Leerzeile -> keine Artikel mehr vorhanden
			return null;
		}
				
		//Lies Artikelbezeichnung
		bezeichnung = liesZeile();
		
		//Lies Artikel-Preis
		preis = Double.parseDouble(liesZeile());
		
		//Lies Artikel-Bestand
		bestand = Integer.parseInt(liesZeile());
		
		//Lies Packungsgröße
		packungsgroesse = Integer.parseInt(liesZeile());

		if(packungsgroesse == 1){
			return new Artikel(bezeichnung, artikelnummer, bestand, preis);
		} else {
			return new Massengutartikel(bezeichnung, artikelnummer, bestand, preis, packungsgroesse);
		}
	}

	/**
	 * Methode zum Schreiben der Artikeldaten in eine externe Datenquelle.
	 * Das Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @param art Artikel-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel art) throws IOException {
		/*
		//Schreibe Artikel-Header
		schreibeZeile("<---ARTIKEL--->");
		*/
		
		//Schreibe Artikelnummer
		schreibeZeile(String.valueOf(art.getArtikelnummer()));
		//Schreibe Artikelbezeichnung
		schreibeZeile(art.getBezeichnung());
		//Schreibe Preis
		schreibeZeile(String.valueOf(art.getPreis()));
		//Schreibe Bestand
		schreibeZeile(String.valueOf(art.getBestand()));
		
		//wenn Artikel ein Massengutartikel ist, wird die Packungsgr��e geschrieben, ansonsten "0"
		if(art instanceof Massengutartikel) {
			Massengutartikel tmp = (Massengutartikel) art;
			schreibeZeile(String.valueOf(tmp.getPackungsgroesse()));
		}
		else {
			schreibeZeile(String.valueOf(0));
		}
				
		/*
		//Schreibe Artikel-Limiter
		schreibeZeile("<---END ARTIKEL--->");
		*/
		
		return true;
	}
	
	/**
	 * @author Mathis M�hlenkamp
	 */
	public Vector<Object> ladeKunde() throws IOException {
		Vector<Object> ret = new Vector<Object>(7);
		
		int id = 0;
		String firstname = "";
		String lastname = "";
		String passwort = "";
		String address_Street = "";
		String address_Zip = "";
		String address_Town = "";
		
		//Lies ID
		try{
			id = Integer.parseInt(liesZeile());
		} catch (NumberFormatException nfe) {
			//Abbruch wenn Leerzeile -> keine Kunden mehr vorhanden
			return null;
		}
				
		//Lies firstname & lastname
		firstname = liesZeile();
		lastname = liesZeile();
		
		//Lies passwort
		passwort = liesZeile();
		
		//Lies Adresse
		address_Street = liesZeile();
		address_Zip = liesZeile();
		address_Town = liesZeile();		
		
		ret.add(id);
		ret.add(firstname);
		ret.add(lastname);
		ret.add(passwort);
		ret.add(address_Street);
		ret.add(address_Zip);
		ret.add(address_Town);		
		
		return ret;
	}
	
	public boolean speichereKunde(Kunde ku) throws IOException {
		
		//Schreibe ID
		schreibeZeile(String.valueOf(ku.getId()));
		//Schreibe firstname
		schreibeZeile(ku.getFirstname());
		//Schreibe lastname
		schreibeZeile(ku.getLastname());
		//Schreibe passwort
		schreibeZeile(ku.getPasswort());
		
		//Schreibe Adresse
		schreibeZeile(ku.getAddress_Street());
		schreibeZeile(ku.getAddress_Zip());
		schreibeZeile(ku.getAddress_Town());
		
		return true;
	}
	
	/**
	 * @author Mathis M�hlenkamp
	 */
	public Mitarbeiter ladeMitarbeiter() throws IOException {

		int id = 0;
		String firstname = "";
		String lastname = "";
		String passwort = "";
		
		//Lies ID
		try{
			id = Integer.parseInt(liesZeile());
		} catch (NumberFormatException nfe) {
			//Abbruch wenn Leerzeile -> keine Kunden mehr vorhanden
			return null;
		}
				
		//Lies firstname & lastname
		firstname = liesZeile();
		lastname = liesZeile();
		
		//Lies passwort
		passwort = liesZeile();

		return new Mitarbeiter(firstname, lastname, id, passwort);
	}
	
	public boolean speichereMitarbeiter(Mitarbeiter mi) throws IOException {
		
		//Schreibe ID
		schreibeZeile(String.valueOf(mi.getId()));
		//Schreibe firstname
		schreibeZeile(mi.getFirstname());
		//Schreibe lastname
		schreibeZeile(mi.getLastname());
		//Schreibe passwort
		schreibeZeile(mi.getPasswort());

		return true;
	}

	public Vector<Object> ladeEreignis() throws IOException {
		
		Vector<Object> ret = new Vector<Object>(6);
		
		int id = 0;
		int werId = 0;
		Typ was = null;
		int womitId = 0;
		int wieviel = 0;
		String wann;
		
		
		try{
			id = Integer.parseInt(liesZeile());
		} catch (NumberFormatException nfe) {
			//Abbruch wenn Leerzeile -> keine Ereignisse mehr vorhanden
			return null;
		}
		
		werId = Integer.parseInt(liesZeile());
		was = Typ.valueOf(liesZeile());
		womitId = Integer.parseInt(liesZeile());
		wieviel = Integer.parseInt(liesZeile());
		wann = liesZeile();
		
		ret.add(id);
		ret.add(werId);
		ret.add(was);
		ret.add(womitId);
		ret.add(wieviel);
		ret.add(wann);
		
		
		return ret;
	}
	
	public boolean speichereEreignis(Ereignis er) throws IOException {
		
		//Schreibe
		schreibeZeile(String.valueOf(er.getId()));
		schreibeZeile(String.valueOf(er.getWer().getId()));
		schreibeZeile(String.valueOf(er.getTyp()));
		schreibeZeile(String.valueOf(er.getWomit().getArtikelnummer()));
		schreibeZeile(String.valueOf(er.getWieviel()));

		//Datum wird richtig formatiert
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		schreibeZeile(String.valueOf(dateFormat.format(er.getWann())));
		
		
		return true;
	}
	
	
	/**
	 * Liest eine Zeile aus
	 * @return Inhalt der Zeile als String
	 * @throws IOException
	 */
	private String liesZeile() throws IOException {
		if (reader != null)
			try{
				return reader.readLine();
			} catch (IOException ie){
				return "";
			}
		else
			return "";
	}

	/**
	 * Schreibt eine Zeile
	 * @param daten Zu schreibende Zeile als String
	 */
	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
}
