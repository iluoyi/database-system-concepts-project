package yil712.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import yil712.control.AccountControl;
import yil712.control.TransControl;

public class TransferUI extends JFrame{

	private static final long serialVersionUID = 267025935430657969L;
	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");
	private JTextField fromAcct = new JTextField("");
	private JTextField crtBal = new JTextField("");
	private JTextField transfer = new JTextField("");
	
	private JComboBox acctList = new JComboBox();
	
	private JRadioButton[] range = new JRadioButton[2];

	private static TransferUI singleton = null;	// singleton
	public static TransferUI getInstance() {
		if (singleton == null) {
			singleton = new TransferUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){
		AccountControl control1 = new AccountControl();
		String customerID = StatusBar.getInstance().getCustomerID();
		String accountNum = ManagementUI.getInstance().getAccountNum();
		
		double bal = control1.getBalance(accountNum);
		
		fromAcct.setText(accountNum);
		crtBal.setText(""+bal);
		fromAcct.setEditable(false);
		crtBal.setEditable(false);
		transfer.setText("");
		
		acctList.removeAllItems();
		ArrayList<String> toAccounts = null; 
		AccountControl control2 = new AccountControl();
		
		if (range[0].isSelected()) {
			toAccounts = control2.getValidAccountIDList(null);
		} else {
			toAccounts = control2.getValidAccountIDList(customerID);
		}
		for (String account : toAccounts) {
			acctList.addItem(account);
		}
	}
	/**
	 * The constructor
	 */
	private TransferUI(){		
		this.setTitle("Transfer");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 15 );
		Font titleFont = new Font("Times New Roman", Font.PLAIN, 35 );
		
		JLabel title = new JLabel("Transfer Money");
		title.setBounds(200, 20, 500, 100);
		title.setFont(titleFont);
		this.getContentPane().add(title);
		
		JLabel label = new JLabel("- Please input deposit amount:");
		label.setFont(labelFont);
		label.setBounds(190, 120, 300, 30);
		this.getContentPane().add(label);
		
		
		JPanel upperPanel = new JPanel();
		range[0] = new JRadioButton("All");
		range[1] = new JRadioButton("Mine");
		ButtonGroup group = new ButtonGroup();
		range[0].setSelected(true);
		
		for (int i = 0;i<2;i++){
			upperPanel.add(range[i]);
			group.add(range[i]);
			range[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					updateInfo();
				}
			});
		}
		
		//put in the information list
		JLabel fromLabel = new JLabel("- From Account: ");
		JLabel balLabel = new JLabel("- Current Balance: ");
		JLabel toLabel = new JLabel("- To Account: ");
		JLabel depLabel = new JLabel("- Transfer Amount: ");
		
		fromLabel.setFont(labelFont);
		balLabel.setFont(labelFont);
		toLabel.setFont(labelFont);
		depLabel.setFont(labelFont);

		fromLabel.setBounds(190, 160, 150, 30);
		balLabel.setBounds(190, 200, 150, 30);
		toLabel.setBounds(190, 240, 150, 30);
		depLabel.setBounds(190, 320, 150, 30);

		fromAcct.setBounds(350, 160,150,30);
		crtBal.setBounds(350, 200,150,30);
		acctList.setBounds(350, 240,150,30);
		upperPanel.setBounds(350, 280,150,30);
		transfer.setBounds(350, 320,150,30);
	
		this.getContentPane().add(fromLabel);
		this.getContentPane().add(balLabel);
		this.getContentPane().add(toLabel);
		this.getContentPane().add(depLabel);
		
		this.getContentPane().add(fromAcct);
		this.getContentPane().add(crtBal);
		this.getContentPane().add(acctList);
		this.getContentPane().add(upperPanel);
		this.getContentPane().add(transfer);
		
		confirm.setBounds(220, 400, 90, 30);
		this.getContentPane().add(confirm);
		
		cancel.setBounds(360, 400, 90, 30);
		this.getContentPane().add(cancel);
		
		updateInfo();
		
		clickConfirm();
		clickCancel();
		}
	
	public void clickConfirm() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					double money = Double.parseDouble(transfer.getText());
					double bal = Double.parseDouble(crtBal.getText());
					String toAccount = (String) acctList.getSelectedItem();
					
					AccountControl con = new AccountControl();
						
					if (money <= bal) {
						TransControl control = new TransControl();
						String accountNum = ManagementUI.getInstance().getAccountNum();
						if (control.transferFromTo(accountNum, con.getAccountType(accountNum), toAccount, con.getAccountType(toAccount), money)) {
							JOptionPane.showMessageDialog(null, "Money transfer succeeds.", "Warning", JOptionPane.INFORMATION_MESSAGE);
							singleton.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Transfer money should be less than the balance.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						transfer.setText("");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please input numbers. (e.g. 100.00)", "Warning", JOptionPane.INFORMATION_MESSAGE);
					transfer.setText("");
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
