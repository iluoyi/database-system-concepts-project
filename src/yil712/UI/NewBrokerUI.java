package yil712.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import yil712.control.BrokerControl;
import yil712.control.Utility;
import yil712.element.Broker;

public class NewBrokerUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3273608852567943985L;
	private JPanel contentPane;
	private JTextField bIDField;
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField locField;
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
					NewBrokerUI frame = NewBrokerUI.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static NewBrokerUI singleton = null;	// singleton
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel lblegXxxxxxxxxx;
	private JLabel label_5;
	public static NewBrokerUI getInstance() {
		if (singleton == null) {
			singleton = new NewBrokerUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	
	public void updateInfo(){
		BrokerControl control = new BrokerControl(); 
		String newBrokerID = Utility.getNewID(control.getLatestBrokerID());	
		bIDField.setText(newBrokerID);
		bIDField.setEditable(false);
		
		pwd1Field.setText("");
		pwd2Field.setText("");
		nameField.setText("");
		phoneField.setText("");
		locField.setText("");
	}

	/**
	 * Create the frame.
	 */
	public NewBrokerUI() {
		setResizable(false);
		setTitle("New Broker");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCreateNewBroker = new JLabel("Create New Broker");
		lblCreateNewBroker.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblId = new JLabel("ID:");
		
		JLabel lblPassword = new JLabel("Password:");
		
		JLabel lblPasswordAgain = new JLabel("Password again:");
		
		JLabel lblName = new JLabel("Name:");
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		
		JLabel lblBranchLocation = new JLabel("Branch Location:");
		
		bIDField = new JTextField();
		bIDField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		phoneField = new JTextField();
		phoneField.setColumns(10);
		
		locField = new JTextField();
		locField.setColumns(10);
		
		confirm = new JButton("Confirm");
		
		cancel = new JButton("Cancel");
		
		pwd1Field = new JPasswordField();
		
		pwd2Field = new JPasswordField();
		
		label = new JLabel("*");
		
		label_1 = new JLabel("*");
		
		label_2 = new JLabel("*");
		
		label_3 = new JLabel("*");
		
		lblegXxxxxxxxxx = new JLabel("* (e.g. XXX-XXX-XXXX)");
		
		label_5 = new JLabel("*");
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
								.addComponent(lblId))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(nameField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(bIDField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(phoneField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(locField, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
										.addComponent(cancel)
										.addComponent(pwd1Field))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(label)
										.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblegXxxxxxxxxx, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(pwd2Field, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(185)
							.addComponent(lblCreateNewBroker)))
					.addContainerGap(63, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addComponent(lblCreateNewBroker)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(bIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(locField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBranchLocation)
						.addComponent(label_5))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
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
				String id = bIDField.getText();
				String name = nameField.getText();
				String phone = phoneField.getText();
				String loc = locField.getText();
				
				//System.out.println(pwd1 + "\n" + pwd2);
				if (pwd1.equals("") || pwd2.equals("") || id.equals("") || name.equals("") || phone.equals("") || loc.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all starred fields.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else if (!pwd1.equals(pwd2)) {
					JOptionPane.showMessageDialog(null, "The second password doesn't match the first one.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else if (!phone.matches(phonePattern)) {
					JOptionPane.showMessageDialog(null, "Phone number should be in the form of XXX-XXX-XXXX.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					Broker broker = new Broker(name, id, phone, loc);
					BrokerControl control = new BrokerControl();
					if (control.createNewBroker(broker, pwd1)) {
						JOptionPane.showMessageDialog(null, "New broker has been successfully created.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						singleton.dispose();
					}
				}
			}
		});
	}
}
