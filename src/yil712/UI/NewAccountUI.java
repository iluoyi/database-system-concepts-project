package yil712.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import yil712.control.AccountControl;
import yil712.control.Utility;
import yil712.element.CashAccount;
import yil712.element.InvestAccount;
import yil712.element.RetireAccount;

public class NewAccountUI extends JFrame{

	private static final long serialVersionUID = -3071624074801088374L;
	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");
	private JTextField accountNum = new JTextField("");
	private JTextField date = new JTextField("");
	private JTextField location = new JTextField("");
	
	private JComboBox acctType = new JComboBox(new String[]{"Cash Account", "Investment Account", "Retirement Account"});
	
	private static NewAccountUI singleton = null;	// singleton
	public static NewAccountUI getInstance() {
		if (singleton == null) {
			singleton = new NewAccountUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){
		AccountControl accountControl = new AccountControl(); 
		String newAccountNum = Utility.getNewID(accountControl.getLatestAccountNum());
		
		SimpleDateFormat stdDate = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		String newDate = stdDate.format(today);
		
		accountNum.setText(newAccountNum);
		date.setText(newDate);
		accountNum.setEditable(false);
		date.setEditable(false);
		
		acctType.setSelectedIndex(0);
		location.setText("");
	}
	/**
	 * The constructor
	 */
	private NewAccountUI(){
		this.setTitle("Create New Account");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 15 );
		Font titleFont = new Font("Times New Roman", Font.PLAIN, 35 );
		
		JLabel title = new JLabel("Create New Account");
		title.setBounds(200, 20, 500, 100);
		title.setFont(titleFont);
		this.getContentPane().add(title);
		
		JLabel label = new JLabel("- Please complete the information:");
		label.setFont(labelFont);
		label.setBounds(190, 120, 200, 30);
		this.getContentPane().add(label);
		
		//put in the information list
		//CashAccount account = new CashAccount("A000003", "C000001", "03/18/1989", "431 Montclair Ave", 1000.85);
		JLabel dateLabel = new JLabel("- Open Date: ");
		JLabel acctLabel = new JLabel("- Account No.: ");
		JLabel typeLabel = new JLabel("- Account Type: ");
		JLabel locLabel = new JLabel("- Branch Location: ");
		
		acctLabel.setFont(labelFont);
		locLabel.setFont(labelFont);
		dateLabel.setFont(labelFont);
		typeLabel.setFont(labelFont);

		dateLabel.setBounds(190, 160, 150, 30);
		acctLabel.setBounds(190, 200, 150, 30);
		typeLabel.setBounds(190, 240, 150, 30);
		locLabel.setBounds(190, 280, 150, 30);

		date.setBounds(320, 160,150,30);
		accountNum.setBounds(320, 200,150,30);
		acctType.setBounds(320, 240,150,30);
		location.setBounds(320, 280,150,30);
	
		this.getContentPane().add(dateLabel);
		this.getContentPane().add(acctLabel);
		this.getContentPane().add(typeLabel);
		this.getContentPane().add(locLabel);

		this.getContentPane().add(date);
		this.getContentPane().add(accountNum);
		this.getContentPane().add(acctType);
		this.getContentPane().add(location);
		
		confirm.setBounds(220, 400, 80, 30);
		this.getContentPane().add(confirm);
		
		cancel.setBounds(360, 400, 80, 30);
		this.getContentPane().add(cancel);
		
		clickConfirm();
		clickCancel();
		}
	
	public void clickConfirm() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (!location.getText().equals("")) {
					AccountControl accountControl = new AccountControl(); 
					String customerID = StatusBar.getInstance().getCustomerID();
					
					if (acctType.getSelectedItem().equals("Cash Account")) {
						String accountID = accountNum.getText();
						String dateStr = date.getText();
						String locStr = location.getText();
						double balance = 0;
						CashAccount cashAccount = new CashAccount(accountID, customerID, dateStr, locStr, balance);
						if (accountControl.createCashAccount(cashAccount)) {
							JOptionPane.showMessageDialog(null, "Congratulations! You have successfully created a cash account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					} 
					else if (acctType.getSelectedItem().equals("Investment Account")) {
						String accountID = accountNum.getText();
						String dateStr = date.getText();
						String locStr = location.getText();
						double balance = 0;
						InvestAccount investAccount = new InvestAccount(accountID, customerID, dateStr, locStr, balance);
						if (accountControl.createInvestAccount(investAccount)) {
							JOptionPane.showMessageDialog(null, "Congratulations! You have successfully created an investment account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else if (acctType.getSelectedItem().equals("Retirement Account")) {
						String accountID = accountNum.getText();
						String dateStr = date.getText();
						String locStr = location.getText();
						double balance = 0;
						RetireAccount retireAccount = new RetireAccount(accountID, customerID, dateStr, locStr, balance);
						if (accountControl.createRetireAccount(retireAccount)) {
							JOptionPane.showMessageDialog(null, "Congratulations! You have successfully created a retirement account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					singleton.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Please fill in the brach location.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
		
	public void clickCancel() {
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
			}
		});
	}

}
