package domain.exceptions;

public class MaxIDsException extends Exception {

	private static final long serialVersionUID = 5393028241416887104L;

	public MaxIDsException (String typ){
		super("Maximale Anzahl an Nutzer für " + typ + " erreicht");
	}
	
}
