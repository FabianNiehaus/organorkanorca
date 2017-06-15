package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import data_objects.Kunde;
import data_objects.Person;
import domain.eShopCore;
import domain.exceptions.AccessRestrictedException;
import domain.exceptions.InvalidPersonDataException;
import domain.exceptions.LoginFailedException;
import domain.exceptions.MaxIDsException;
import net.miginfocom.swing.MigLayout;


public class LoginWindow extends JFrame {
	
	public interface LoginListener {
		public void userLoggedIn(Person user);
		public void loginCancelled();
	}
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	JLabel benutzerLabel = new JLabel("Benutzer");
	JLabel passwortLabel = new JLabel("Passwort");
	
	JTextField benutzerField = new JTextField(10);
	JPasswordField passwortField = new JPasswordField(10);
	
	JLabel headerLabel = new JLabel("Willkommen bei OrganOrkanOrca");
	
	JButton anmeldenButton = new JButton("Login");
	JButton registrierenButton = new JButton("Registrieren");
	
	private eShopCore eShop = null;
	private LoginListener loginListener = null;
	
	public LoginWindow(String titel, eShopCore eShop, LoginListener listener){
		super(titel);
	
		this.eShop = eShop;
		this.loginListener = listener;
		
		JPanel form = new JPanel();
		
		form.setLayout(new MigLayout());
		
		form.add(headerLabel, "span 2, wrap");
		form.add(benutzerLabel);
		form.add(benutzerField, "wrap");
		form.add(passwortLabel);
		form.add(passwortField, "wrap");
		form.add(anmeldenButton);
		form.add(registrierenButton);
		
		JPanel pU = new JPanel();
		JButton bU = new JButton("Als User anmelden");
		bU.addActionListener(new LoginUserUmgehenListener());
		pU.add(bU);
		
		JPanel pM = new JPanel();
		JButton bM = new JButton("Als Mitarbeiter anmelden");
		bM.addActionListener(new LoginMitarbeiterUmgehenListener());
		pM.add(bM);
		
		tabbedPane.addTab("Anmelden", null, form, "Anmeldung");
		tabbedPane.addTab("User", null, pU, "Shortcut User");
		tabbedPane.addTab("Mitarbeiter", null, pM, "Shortcut Mitarbeiter");
		
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.getContentPane().add(tabbedPane);
		
		anmeldenButton.addActionListener(new LoginButtonListener());
		registrierenButton.addActionListener(new LoginNeuerNutzerAnlegenListener());
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		
		this.pack();
		
		this.setVisible(true);
	}
	
	public int benutzerIdAuslesen() throws NumberFormatException{
		return Integer.parseInt(benutzerField.getText());
	}
	
	public String passwortAuslesen(){
		char[] pw = passwortField.getPassword();
		passwortField.setText("");
		return new String(pw);
	}
	
	private class LoginButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Person user = eShop.anmelden(LoginWindow.this.benutzerIdAuslesen(), LoginWindow.this.passwortAuslesen());
//				mainwindow = new MainWindow("OrganOrkanOrca eShop");
//				loginwindow.dispose();
				loginListener.userLoggedIn(user);
			} catch (NumberFormatException | LoginFailedException e1) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Anmeldung fehlgeschlagen");
			}
		}
	}
	
	private class LoginUserUmgehenListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			try {
//				user = eShop.anmelden(1001, "test");
//			} catch (LoginFailedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			mainwindow = new MainWindow("OrganOrkanOrca eShop");
//			loginwindow.dispose();
			
		}
		
	}
	
	private class LoginMitarbeiterUmgehenListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			try {
//				user = eShop.anmelden(9000, "test2");
//			} catch (LoginFailedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			mainwindow = new MainWindow("OrganOrkanOrca eShop");
//			loginwindow.dispose();
		}
	}
	
	private class LoginNeuerNutzerAnlegenListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextField firstnameField = new JTextField();
			JTextField lastnameField = new JTextField();
			JTextField addressStreetField = new JTextField();
			JTextField addressTownField = new JTextField();
			JTextField addressZipField = new JTextField();			
			JTextField passwordField = new JPasswordField();
			
			Object[] message = {
			    "Vorname:", firstnameField,
			    "Nachname:", lastnameField,
			    "Straße:", addressStreetField,
			    "Stadt:", addressTownField,
			    "PLZ:", addressZipField,
			    "Passwort:", passwordField
			};

			int option = JOptionPane.showConfirmDialog(null, message, "Nutzer anlegen", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
			   if (!firstnameField.getText().equals("")){
				   if (!lastnameField.getText().equals("")){
					   if (!addressStreetField.getText().equals("")){
						   if (!addressTownField.getText().equals("")){
							   if (!addressZipField.getText().equals("") && addressZipField.getText().length() == 5){
								   if (!passwordField.getText().equals("")){
									   try {
										   
											Kunde ku = eShop.erstelleKunde(firstnameField.getText(), 
														   lastnameField.getText(), 
														   passwordField.getText(), 
														   addressStreetField.getText(), 
														   addressZipField.getText(), 
														   addressTownField.getText(), 
														   null);
											
											JOptionPane.showMessageDialog(LoginWindow.this, "Benutzer " + ku.getId() + " erfolgreich erstellt");
											
										} catch (MaxIDsException e1) {
											JOptionPane.showMessageDialog(LoginWindow.this, e1.getMessage());
										} catch (AccessRestrictedException e1) {
											JOptionPane.showMessageDialog(LoginWindow.this, e1.getMessage());
										} catch (InvalidPersonDataException e1) {
											JOptionPane.showMessageDialog(LoginWindow.this, e1.getMessage());
										}
								   }
							   }
						   }
					   }
				   }
			   } else {
				   JOptionPane.showMessageDialog(LoginWindow.this, "Benutzer erstellen fehlgeschlagen");
			   }
			} else {
				JOptionPane.showMessageDialog(LoginWindow.this, "Benutzer erstellen abgebrochen");
			}
			
		}
	}

}
