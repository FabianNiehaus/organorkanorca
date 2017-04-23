package domain;

import java.util.LinkedHashMap;
import java.util.Vector;
import data_objects.*;

/**
 * @author Fabian Niehaus
 * Klasse zur Verwaltung von Warenkörben
 */
public class Warenkorbverwaltung {
	
	private Vector<Warenkorb> warenkoerbe = new Vector<>();

	/**
	 * Gibt einen Warenkorb aus, sofern dieser in der Verwaltung exisitert
	 * @param wk Gesuchter Warenkorb
	 * @return Gesuchter Warenkorb
	 */
	public Warenkorb getWarenkorb(Warenkorb wk){
		for(Warenkorb ret : warenkoerbe){
			if(ret.equals(wk)){
				return ret;
			}
		}
		return null;
	}
	
	/**
	 * Erzeugt einen neuen (leeren) Warenkorb
	 * @return Erzeugter Warenkorb
	 */
	public Warenkorb erstelleWarenkorb(){
		Warenkorb wk = new Warenkorb();
		warenkoerbe.addElement(wk);
		return wk;
	}
	
	/**
	 * Löscht einen Warenkorb
	 * @param wk Zu löschender Warenkorb
	 */
	public void loescheWarenkorb(Warenkorb wk){
		for(Warenkorb del : warenkoerbe){
			if(del.equals(wk)){
				warenkoerbe.remove(del);
			}
		}
	}
	
	/**
	 * Ändert die Anzahl eines Artikels in einem Warenkorb. Die Artikelauswahl erfolgt nach Position.
	 * @param aend Zu bearbeitender Warenkorb
	 * @param position Position des zu verändernden Artikels
	 * @param anz Neue Anzahl
	 */
	public void aendereWarenkorb(Warenkorb aend, int position, int anz){
		Warenkorb wk = getWarenkorb(aend);
		wk.aendereAnzahl(position, anz);
	}
	
	/**
	 * Fügt einem Warenkorb einen Artikel hinzu
	 * @param wk Zu bearbeitender Warenkorb
	 * @param art Hinzuzufügender Artikel
	 * @param anz Anzahl des Artikels
	 */
	public void legeInWarenkorb(Warenkorb wk, Artikel art, int anz){
		wk.speichereArtikel(art, anz);
	}
	
	/**
	 * @return Alle Warenkörbe
	 */
	public Vector<Warenkorb> getWarenkoerbe() {
		return warenkoerbe;
	}
	
	/**
	 * Leert einen Warenkorb
	 * @param wk Zu leerender Warenkorb
	 */
	public void leereWarenkorb(Warenkorb wk){
		wk.leereWarkenkorb();
	}
	
	/**
	 * Gibt den Inhalt eines Warenkorbs aus
	 * @param wk Gewünschter Warenkorb
	 * @return Alle Artikel mit Anzahl
	 */
	public LinkedHashMap<Artikel, Integer> getArtikel(Warenkorb wk){
		return wk.getArtikel();
	}

}
