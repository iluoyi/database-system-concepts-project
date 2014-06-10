package yil712.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import yil712.control.BrokerControl;
import yil712.control.CustomerControl;
import yil712.control.Utility;
import yil712.element.Customer;

public class NewCustomerUI extends JFrame {

	private static final long serialVersionUID = 3273608852567943985L;
	private JPanel contentPane;
	private JTextField cIDField;
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField addField;
	private JButton confirm;
	private JButton cancel;
	private JPasswordField pwd1Field;
	private JPasswordField pwd2Field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewCustomerUI frame = NewCustomerUI.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static NewCustomerUI singleton = null;	// singleton
	private JComboBox brokerBox;
	
	public static NewCustomerUI getInstance() {
		if (singleton == null) {
			singleton = new NewCustomerUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	
	public void updateInfo(){
		CustomerControl control1 = new CustomerControl(); 
		String newCustomerID = Utility.getNewID(control1.getLatestCustomerID());	
		cIDField.setText(newCustomerID);
		cIDField.setEditable(false);
		
		pwd1Field.setText("");
		pwd2Field.setText("");
		nameField.setText("");
		phoneField.setText("");
		addField.setText("");
		
		BrokerControl control2 = new BrokerControl();
		ArrayList<String> brokers = control2.getAllBrokerIDs();
		
		brokerBox.removeAllItems();
		for (String broker : brokers) {
			brokerBox.addItem(broker);
		}
	}

	/**
	 * Create the frame.
	 */
	public NewCustomerUI() {
		setResizable(false);
		setTitle("New Customer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCreateNewBroker = new JLabel("Create New Customer");
		lblCreateNewBroker.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblId = new JLabel("ID:");
		
		JLabel lblPassword = new JLabel("Password:");
		
		JLabel lblPasswordAgain = new JLabel("Password again:");
		
		JLabel lblName = new JLabel("Name:");
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		
		JLabel lblBranchLocation = new JLabel("Address:");
		
		cIDField = new JTextField();
		cIDField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		
		addField = new JTextField();
		addField.setColumns(10);
		
		confirm = new JButton("Confirm");
		
		cancel = new JButton("Cancel");
		
		pwd1Field = new JPasswordField();
		
		pwd2Field = new JPasswordField();
		
		JLabel label = new JLabel("*");
		
		JLabel label_1 = new JLabel("*");
		
		JLabel label_2 = new JLabel("*");
		
		JLabel label_3 = new JLabel("*");
		
		JLabel lblegXxxxxxxxxx = new JLabel("* (e.g. XXX-XXX-XXXX)");
		
		JLabel label_5 = new JLabel("*");
		
		JLabel lblBroker = new JLabel("Broker:");
		
		brokerBox = new JComboBox();
		
		JLabel label_4 = new JLabel("*");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(152)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(confirm)
								.addComponent(lblBranchLocation)
								.addComponent(lblPhoneNumber)
								.addComponent(lblName)
								.addComponent(lblPasswordAgain)
								.addComponent(lblPassword)
								.addComponent(lblId)
								.addComponent(lblBroker, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(pwd2Field, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(brokerBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(nameField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(cIDField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(phoneField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(addField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(cancel)
										.addComponent(pwd1Field))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(label)
										.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblegXxxxxxxxxx, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(185)
							.addComponent(lblCreateNewBroker)))
					.addContainerGap(64, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addComponent(lblCreateNewBroker)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(pwd1Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPasswordAgain)
						.addComponent(pwd2Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName)
						.addComponent(label_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPhoneNumber)
						.addComponent(lblegXxxxxxxxxx))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(addField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBranchLocation)
						.addComponent(label_5))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBroker)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(brokerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_4)))
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(confirm)
						.addComponent(cancel))
					.addGap(36))
		);
		contentPane.setLayout(gl_contentPane);
		
		clickCancel();
		confirmEvent();
	}
	
	public void clickCancel() {
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
			}
		});
	}

	public void confirmEvent() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String phonePattern = "[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}";
				
				String pwd1 = new String(pwd1Field.getPassword());
				String pwd2 = new String(pwd2Field.getPassword());
				String id = cIDField.getText();
				String name = nameField.getText();
				String phone = phoneField.getText();
				String add = addField.getText();
				
				if (brokerBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "No broker is available, so you cannot create new customers.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else if (pwd1.equals("") || pwd2.equals("") || id.equals("") || name.equals("") || phone.equals("") || add.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all starred fields.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else if (!pwd1.equals(pwd2)) {
					JOptionPane.showMessageDialog(null, "The second password doesn't match the first one.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else if (!phone.matches(phonePattern)) {
					JOptionPane.showMessageDialog(null, "Phone number should be in the form of XXX-XXX-XXXX.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String b_id = (String) brokerBox.getSelectedItem();
					//String name, String customer_id, String broker_id, String phone_num, String address
					Customer customer = new Customer(name, id, b_id, phone, add);
					CustomerControl control = new CustomerControl();
					if (control.createNewCustomer(customer, pwd1)) {
						JOptionPane.showMessageDialog(null, "New customer has been successfully created.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						singleton.dispose();
					}
				}
			}
		});
	}
}
