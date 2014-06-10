package yil712.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import yil712.control.BrokerControl;

public class SelectCustomerUI extends JFrame {

	private static final long serialVersionUID = -2552224356588877393L;
	private JPanel contentPane;
	private JComboBox customerIDs;
	private JButton confirm;
	private JButton cancel;
	public static String brokerID; 
	
	private static SelectCustomerUI singleton = null;	// singleton
	
	public static SelectCustomerUI getInstance(String broker) {
		if (singleton == null) {
			singleton = new SelectCustomerUI();
		}
		brokerID = broker;
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){		
		BrokerControl control = new BrokerControl();
		ArrayList<String> customers = control.getCustomerIDsOf(brokerID);
		
		customerIDs.removeAllItems();
		for (String customer : customers) {
			customerIDs.addItem(customer);
		}
	}

	public SelectCustomerUI() {
		setResizable(false);
		setTitle("Select a customer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseSelectA = new JLabel("Please select a customer");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPleaseSelectA.setBounds(105, 40, 209, 46);
		contentPane.add(lblPleaseSelectA);
		
		customerIDs = new JComboBox();
		customerIDs.setBounds(168, 117, 150, 20);
		contentPane.add(customerIDs);
		
		JLabel lblCustomerId = new JLabel("Customer ID:");
		lblCustomerId.setBounds(94, 120, 81, 14);
		contentPane.add(lblCustomerId);
		
		confirm = new JButton("Confirm");
		confirm.setBounds(94, 186, 89, 23);
		contentPane.add(confirm);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(229, 186, 89, 23);
		contentPane.add(cancel);
		
		clickCancel();
		clickConfirm();
	}

	public void clickCancel() {
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
				LoginUI.getInstance().setVisible(true);
			}
		});
	}
	
	public void clickConfirm() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (customerIDs.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "You don't have any customers, work harder man!", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String c_id = (String) customerIDs.getSelectedItem();
					ManagementUI manage = ManagementUI.getInstance();
					manage.setCustomerID(c_id);
					manage.setUserID(brokerID); // the current user is the target customer
					manage.setVisible(true);
					singleton.dispose();
				}
			}
		});
	}
}
