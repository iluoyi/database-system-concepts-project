package yil712.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import yil712.control.CashAcctControl;

public class DepositUI extends JFrame{

	private static final long serialVersionUID = 267025935430657969L;
	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");
	private JTextField account = new JTextField("");
	private JTextField crtBal = new JTextField("");
	private JTextField deposit = new JTextField("");
	
	private static DepositUI singleton = null;	// singleton
	public static DepositUI getInstance() {
		if (singleton == null) {
			singleton = new DepositUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){
		String accountNum = ManagementUI.getInstance().getAccountNum();
		
		CashAcctControl control = new CashAcctControl();
		double bal = control.getCashAccountBalance(accountNum);
		
		account.setText(accountNum);
		crtBal.setText(""+bal);
		account.setEditable(false);
		crtBal.setEditable(false);
		deposit.setText("");
	}
	/**
	 * The constructor
	 */
	private DepositUI(){
		this.setTitle("Deposit");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 15 );
		Font titleFont = new Font("Times New Roman", Font.PLAIN, 35 );
		
		JLabel title = new JLabel("Deposit");
		title.setBounds(200, 20, 500, 100);
		title.setFont(titleFont);
		this.getContentPane().add(title);
		
		JLabel label = new JLabel("- Please input deposit amount:");
		label.setFont(labelFont);
		label.setBounds(190, 120, 300, 30);
		this.getContentPane().add(label);
		
		//put in the information list
		JLabel acctLabel = new JLabel("- Account No.: ");
		JLabel typeLabel = new JLabel("- Current Balance: ");
		JLabel depLabel = new JLabel("- Deposit Amount: ");
		
		acctLabel.setFont(labelFont);
		typeLabel.setFont(labelFont);
		depLabel.setFont(labelFont);

		acctLabel.setBounds(190, 160, 150, 30);
		typeLabel.setBounds(190, 200, 150, 30);
		depLabel.setBounds(190, 240, 150, 30);

		account.setBounds(350, 160,150,30);
		crtBal.setBounds(350, 200,150,30);
		deposit.setBounds(350, 240,150,30);
	
		this.getContentPane().add(acctLabel);
		this.getContentPane().add(typeLabel);
		this.getContentPane().add(depLabel);

		this.getContentPane().add(account);
		this.getContentPane().add(crtBal);
		this.getContentPane().add(deposit);
		
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
					double dep = Double.parseDouble(deposit.getText());
					String accountNum = ManagementUI.getInstance().getAccountNum();
					CashAcctControl control = new CashAcctControl();
					if (control.depositTo(accountNum, dep)) {
						JOptionPane.showMessageDialog(null, "Deposit succeeds.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						singleton.dispose();
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please input numbers. (e.g. 100.00)", "Warning", JOptionPane.INFORMATION_MESSAGE);
					deposit.setText("");
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
