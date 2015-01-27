package views;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class LoginView extends JPanel {

	private static final long serialVersionUID = 3;

	private JPasswordField txtPassword;
	private String txtpwd = null;
	private String usertypes[] = { "Administrator", "Customer", "Employee",	"Courier" };
	public JButton btnSingIn;
	private JComboBox<Object> comboBox;
	private JFormattedTextField txtUsername;
	private JLabel lblUsername;
	private JLabel lblPassword;

	public LoginView() {
		setOpaque(false);
		setLayout(new MigLayout("", "[132.00,grow][200][grow]", "[grow][40][40][40][40][40][grow]"));

		ImageIcon image = new ImageIcon("image/a.png");

		JLabel lblTransfersLogin = new JLabel(image);
		lblTransfersLogin.setBackground(Color.WHITE);
		lblTransfersLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		add(lblTransfersLogin, "cell 1 1,alignx center,aligny center");
		
		lblUsername = new JLabel("Username:");
		add(lblUsername, "cell 0 3,alignx trailing");

		
		txtUsername = new JFormattedTextField();
		add(txtUsername, "cell 1 3,grow");
		
		lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 0 4,alignx trailing");

		txtPassword = new JPasswordField();
		add(txtPassword, "cell 1 4,grow");
		
		comboBox = new JComboBox<Object>(usertypes);
		comboBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				String s = (String) comboBox.getSelectedItem();
				if (s.equals("Customer"))
				{
					txtUsername.setText(null);
					txtPassword.setText(null);
					txtUsername.setEnabled(false);
					txtPassword.setEnabled(false);
					
				}
				else
				{
					txtUsername.setEnabled(true);
					txtPassword.setEnabled(true);	
				}
			}
		});
		comboBox.setSelectedIndex(1);
		add(comboBox, "cell 1 2,grow");

		btnSingIn = new JButton("Log In");
		btnSingIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnSingIn, "cell 1 5,grow");

	}

	public String getUserType() {
		return comboBox.getSelectedItem().toString();
	}

	public String getUserName() {
		return txtUsername.getText();
	}
	
	public String getRole() {
		return comboBox.getSelectedItem().toString();
	}

	public void clear() {
		txtUsername.setText("");
		txtPassword.setText("");
	}

	public String getPass() {
		txtpwd = new String(txtPassword.getPassword());
		return txtpwd;
	}

}
