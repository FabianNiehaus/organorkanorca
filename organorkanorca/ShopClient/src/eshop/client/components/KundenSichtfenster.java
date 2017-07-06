package eshop.client.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import eshop.client.util.Sichtfenster;
import eshop.common.data_objects.Person;
import eshop.common.exceptions.AccessRestrictedException;
import eshop.common.net.ShopRemote;

public class KundenSichtfenster extends Sichtfenster {

    /**
     * 
     */
    private static final long serialVersionUID = 4821072292018595904L;

    public KundenSichtfenster(ShopRemote server, Person user, SichtfensterCallbacks listener) {
	super(server, user, listener);
	aktion.setText("Bearbeiten");
	aktion.addActionListener(new KundeBearbeitenListener());
	anzahl.setVisible(false);
	try {
	    updateTable(server.alleKundenAusgeben(user),
		    new String[] { "Kundennummer", "Vorname", "Nachname", "Straße", "PLZ", "Ort" });
	} catch(RemoteException | AccessRestrictedException e) {
	    JOptionPane.showMessageDialog(KundenSichtfenster.this, e.getMessage());
	}
    }

    class KundeBearbeitenListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    listener.kundeBearbeiten();
	}
    }

    @Override
    public void callTableUpdate() {

	try {
	    updateTable(server.alleKundenAusgeben(user),
		    new String[] { "ArtNr.", "Bezeichnung", "Preis", "Einheit", "Bestand" });
	} catch(RemoteException | AccessRestrictedException e) {
	    JOptionPane.showMessageDialog(KundenSichtfenster.this, e.getMessage());
	}
    }
}
