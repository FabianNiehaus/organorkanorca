/**
 * 
 */
package data_objects;

import java.util.Date;

/**
 * @author Fabian
 * Dient zur Speicherung einer Rechnung
 */
/**
 * @author Manic
 *
 */
public class Rechnung {

	/**
	 * @param ku Zugehöriger Kunde
	 * @param datum Rechnungsdatum
	 * @param wk Zugehöriger Warenkorb
	 * @param gesamt Gesamtsumme der Rechnung
	 */
	public Rechnung(Kunde ku, Date datum, Warenkorb wk, double gesamt) {
		super();
		this.ku = ku;
		this.datum = datum;
		this.wk = wk;
		this.gesamt = gesamt;
	}
	
	private Kunde ku;
	private Date datum;
	private Warenkorb wk;
	private double gesamt;
	
	/**
	 * @return
	 */
	public Kunde getKu() {
		return ku;
	}
	
	/**
	 * @return
	 */
	public Date getDatum() {
		return datum;
	}
	
	/**
	 * @return
	 */
	public Warenkorb getWk() {
		return wk;
	}
	
	/**
	 * @return
	 */
	public double getGesamt() {
		return gesamt;
	}
	
}
