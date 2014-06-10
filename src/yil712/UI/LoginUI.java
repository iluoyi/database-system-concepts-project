package yil712.UI;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import yil712.control.LoginControl;

/**
 * This is the interface of login
 * @author Yi Luo
 */
public class LoginUI extends JFrame{

	private static final long serialVersionUID = -808651543841604244L;
	private char identity = ' ';
	private static JTextField userID = new JTextField("");
	private JPasswordField password = new JPasswordField(10);
	private JButton loginButton = new JButton("Login");
	private JButton registerButton = new JButton("Register");
	private JRadioButton[] identities = new JRadioButton[5];
	
	private static LoginUI singleton = null;	// singleton
	
	public static LoginUI getInstance() {
		if (singleton == null) {
			singleton = new LoginUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo() {
		userID.setText("");
		password.setText("");
	}
	
	/**
	 * The default constructor
	 */
	public LoginUI(){
		setTitle("Welcome");
		setSize(900,560);
		setLocationRelativeTo(null);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		Container container=getContentPane();
		container.setBackground(Color.WHITE);
		container.setLayout(null);

		//Put in the welcome title
		JLabel title = new JLabel("Merry Lunch Professional Finace & Service");
		Font titleFont = new Font("Arial", Font.PLAIN, 30 );
		title.setBounds(120, 50, 700, 100);
		title.setFont(titleFont);
		container.add(title);

		//Put in the version information
		JLabel version = new JLabel("Brokerage Firm");
		Font versionFont = new Font("Arial", Font.PLAIN, 20 );
		version.setBounds(650, 80, 200, 100);
		version.setFont(versionFont);
		container.add(version);

		//put in the RadioButtons of identities
		identities[0] = new JRadioButton("Customer Login");
		identities[1] = new JRadioButton("Broker Login");
		ButtonGroup group = new ButtonGroup();
		Font radioButtonFont = new Font("Arial",Font.PLAIN,13);

		for (int i = 0;i<2;i++){
			identities[i].setBounds(300+i*200,200,150,30);
			identities[i].setBackground(Color.WHITE);
			identities[i].setFont(radioButtonFont);
			container.add(identities[i]);
			group.add(identities[i]);
			identities[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					identity = e.getActionCommand().charAt(0);
				}
			});
		}

		//Put in the input text field of userID and password
		Font inputLabel = new Font("Arial", Font.BOLD, 20 );
		JLabel userLabel = new JLabel("User ID");
		JLabel passwordLabel = new JLabel("Password");
		userLabel.setFont(inputLabel);
		passwordLabel.setFont(inputLabel);
		userLabel.setBounds(300, 250, 150, 30);
		passwordLabel.setBounds(300, 300, 150, 30);
		userID.setBounds(450, 250,150,30);
		password.setBounds(450, 300,150,30);
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userID);
		container.add(password);

		//put in the "login" button and "exit" button
		loginButton.setBounds(250, 420,150,30);
		registerButton.setBounds(500, 420,150,30);
		container.add(loginButton);
		container.add(registerButton);
		
		loginButtonEvent();
		registerButtonEvent();
	}
	
	public void loginButtonEvent() {
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (identity == ' ') {
					JOptionPane.showMessageDialog(null, "Please select an identity.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					LoginControl loginControl = new LoginControl();
					String user = userID.getText();
					String pwd = new String(password.getPassword());
										
					if (loginControl.checkPassword(identity, user, pwd)) {
						if (identity == 'C') {
							ManagementUI manage = ManagementUI.getInstance();
							manage.setCustomerID(user);
							manage.setUserID(user); // the current user is the target customer
							manage.setVisible(true);
							singleton.dispose();
						} else if (identity == 'B') {
							SelectCustomerUI selection = SelectCustomerUI.getInstance(user);
							selection.setVisible(true);
							singleton.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect userID or pwd!", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}
	
	public void registerButtonEvent() {
		registerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (identity == ' ') {
					JOptionPane.showMessageDialog(null, "Please select an identity.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (identity == 'C') {
						NewCustomerUI frame = NewCustomerUI.getInstance();
						frame.setVisible(true);
					} else if (identity == 'B') {
						NewBrokerUI frame = NewBrokerUI.getInstance();
						frame.setVisible(true);
					}
				}
			}
		});
	}
}

