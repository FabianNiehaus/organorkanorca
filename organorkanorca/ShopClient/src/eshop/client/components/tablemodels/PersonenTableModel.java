package eshop.client.components.tablemodels;

import java.util.Vector;

import eshop.common.data_objects.Person;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonenTableModel.
 */
public class PersonenTableModel extends ShopTableModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6108669637836557607L;

	/**
	 * Instantiates a new personen table model.
	 *
	 * @param dataVector
	 *           the data vector
	 */
	public PersonenTableModel(Vector<? extends Person> dataVector) {
		columnNames = new String[] {"ID", "Vorname", "Nachname", "Straße", "PLZ", "Ort"};
		data = new Object[dataVector.size()][6];
		int i = 0;
		for (Person p : dataVector) {
			data[i][0] = p.getId();
			data[i][1] = p.getFirstname();
			data[i][2] = p.getLastname();
			data[i][3] = p.getAddress_Street();
			data[i][4] = p.getAddress_Zip();
			data[i][5] = p.getAddress_Town();
			i++;
		}
	}
}
