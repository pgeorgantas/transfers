package additional;

import views.AdministratorView;
import views.CustomerView;
import views.EmployeeView;
import views.LoginView;
import views.CourierView;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.AdministratorModel;
import models.CustomerModel;
import models.EmployeeModel;
import models.CourierModel;
import controllers.AdministratorController;
import controllers.CustomerController;
import controllers.EmployeeController;
import controllers.LoginController;
import controllers.CourierController;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView {

	private static CardLayout card;
	private static JMenuBar menuBar;
	private static JButton btnNewButton;
	private static LoginView login;
	private static EmployeeView Employee;
	private static CourierView Courier;
	private static CustomerView customer;
	private static AdministratorView admin;

	private static EmployeeModel ipalmod;
	private static CourierModel metmod;
	private static CustomerModel logmod;
	private static AdministratorModel adminmod;

	private static LoginController logcont;
	private static EmployeeController ipalcont;
	private static CourierController metcont;
	private static CustomerController custcont;
	private static AdministratorController admincont;

	public static void main(String[] args) {

		final JFrame frame = new JFrame("Transfers");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // diavazei
																			// tin
																			// analisi
																			// tis
																			// othonis

		frame.setBounds(0, 0, (int) (screenSize.width / 2.5),
				(int) (screenSize.height / 1.5)); // orizei to megethos tou
													// parathirou sto /2.5 tis
													// analisis y kai /3 sto x
		frame.setLocation((screenSize.width - frame.getSize().width) / 2,
				(screenSize.height - frame.getSize().height) / 2); // to vazei
																	// sto
																	// kendro
																	// tis
																	// othonis
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		btnNewButton = new JButton("Logout");
		menuBar.add(btnNewButton);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		login = new LoginView();
		Employee = new EmployeeView();
		Courier = new CourierView();
		customer = new CustomerView();
		admin = new AdministratorView();

		ipalmod = new EmployeeModel();
		metmod = new CourierModel();
		logmod = new CustomerModel();
		adminmod = new AdministratorModel();

		logcont = new LoginController(login);
		ipalcont = new EmployeeController(Employee, ipalmod);
		metcont = new CourierController(Courier, metmod);
		custcont = new CustomerController(customer, logmod);
		admincont = new AdministratorController(admin, adminmod);

		frame.getContentPane().add(login, "Log");
		frame.getContentPane().add(Employee, "Employee");
		frame.getContentPane().add(Courier, "Courier");
		frame.getContentPane().add(customer, "Customer");
		frame.getContentPane().add(admin, "Administrator");

		card = (CardLayout) frame.getContentPane().getLayout();
		card.show(frame.getContentPane(), "Log");

		menuBar.setVisible(false);

		frame.setVisible(true);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frame.getContentPane(), "Log");
				login.clear();
				ipalcont.clear();
				admincont.clear();
				custcont.clear();
				metcont.clear();
				menuBar.setVisible(false);
			}
		});
		
		login.btnSingIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (login.getRole().equals("Customer")) {
					card.show(frame.getContentPane(), logcont.changePanel());
					menuBar.setVisible(true);
				} else {
					DatabaseUtility dblogin = new DatabaseUtility();
					if (dblogin.login(login.getUserName(), login.getPass(),
							login.getRole())) {
						card.show(frame.getContentPane(), logcont.changePanel());
						menuBar.setVisible(true);
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Could not sign in. Please check your credentials and connectivity.");
					}
				}
			}
		});

	}
}
