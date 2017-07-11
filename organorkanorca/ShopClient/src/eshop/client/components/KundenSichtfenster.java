package eshop.client.components;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import eshop.client.components.tablemodels.PersonenTableModel;
import eshop.client.util.Sichtfenster;
import eshop.common.data_objects.Person;
import eshop.common.exceptions.AccessRestrictedException;
import eshop.common.exceptions.PersonNonexistantException;
import eshop.common.net.ShopRemote;

// TODO: Auto-generated Javadoc
/**
 * The Class KundenSichtfenster.
 */
public class KundenSichtfenster extends Sichtfenster {

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 4821072292018595904L;
	
	/** The model. */
	private PersonenTableModel	model;

	/**
	 * Instantiates a new kunden sichtfenster.
	 *
	 * @param server
	 *           the server
	 * @param user
	 *           the user
	 * @param sichtfensterCallbacks
	 *           the sichtfenster callbacks
	 */
	public KundenSichtfenster(ShopRemote server, Person user, SichtfensterCallbacks sichtfensterCallbacks) {
		super(server, user, sichtfensterCallbacks, new String[] {"ID", "Vorname", "Nachname", "Straße", "Wohnort"});
		auflistung.getSelectionModel().addListSelectionListener(new KundeAnzeigenListener());
	}

	/* (non-Javadoc)
	 * @see eshop.client.util.Sichtfenster#callTableUpdate()
	 */
	@Override
	public void callTableUpdate() {

		try {
			model = new PersonenTableModel(server.alleKundenAusgeben(user));
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					auflistung.setModel(model);
					fitTableLayout();
				}
			});
		} catch (RemoteException | AccessRestrictedException e) {
			JOptionPane.showMessageDialog(KundenSichtfenster.this, e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see eshop.client.util.Sichtfenster#initializeHighlighting()
	 */
	@Override
	public void initializeHighlighting() {

		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see eshop.client.util.Sichtfenster#TabelleFiltern()
	 */
	@Override
	public void TabelleFiltern() {

		if (sucheField1.getText().equals(sucheFieldNames[0])) {
			sucheField1.setText("");
		}
		if (sucheField2.getText().equals(sucheFieldNames[1])) {
			sucheField2.setText("");
		}
		if (sucheField3.getText().equals(sucheFieldNames[2])) {
			sucheField3.setText("");
		}
		if (sucheField4.getText().equals(sucheFieldNames[3])) {
			sucheField4.setText("");
		}
		if (sucheField5.getText().equals(sucheFieldNames[4])) {
			sucheField5.setText("");
		}
		Filter[] filterArray = {new PatternFilter(".*" + sucheField1.getText() + ".*", Pattern.CASE_INSENSITIVE, 0),
				new PatternFilter(".*" + sucheField2.getText() + ".*", Pattern.CASE_INSENSITIVE, 1),
				new PatternFilter(".*" + sucheField3.getText() + ".*", Pattern.CASE_INSENSITIVE, 2),
				new PatternFilter(".*" + sucheField4.getText() + ".*", Pattern.CASE_INSENSITIVE, 3),
				new PatternFilter(".*" + sucheField5.getText() + ".*", Pattern.CASE_INSENSITIVE, 5)};
		FilterPipeline filters = new FilterPipeline(filterArray);
		auflistung.setFilters(filters);
	}

	/**
	 * The listener interface for receiving kundeAnzeigen events. The class that
	 * is interested in processing a kundeAnzeigen event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's <code>addKundeAnzeigenListener<code>
	 * method. When the kundeAnzeigen event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see KundeAnzeigenEvent
	 */
	class KundeAnzeigenListener implements ListSelectionListener {

		/* (non-Javadoc)
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {

			try {
				if (auflistung.getSelectedRow() != -1) sichtfensterCallbacks
						.kundeAnzeigen(server.kundeSuchen((int) auflistung.getValueAt(auflistung.getSelectedRow(), 0), user));
				return;
			} catch (RemoteException e1) {
				JOptionPane.showMessageDialog(KundenSichtfenster.this, e1.getMessage());
			} catch (PersonNonexistantException e1) {
				JOptionPane.showMessageDialog(KundenSichtfenster.this, e1.getMessage());
			}
		}
	}
}
