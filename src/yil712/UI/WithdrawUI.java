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

public class WithdrawUI extends JFrame{

	private static final long serialVersionUID = 267025935430657969L;
	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");
	private JTextField account = new JTextField("");
	private JTextField crtBal = new JTextField("");
	private JTextField withdraw = new JTextField("");
	
	private static WithdrawUI singleton = null;	// singleton
	public static WithdrawUI getInstance() {
		if (singleton == null) {
			singleton = new WithdrawUI();
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
		withdraw.setText("");
	}
	/**
	 * The constructor
	 */
	private WithdrawUI(){
		this.setTitle("Withdrawal");
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 15 );
		Font titleFont = new Font("Times New Roman", Font.PLAIN, 35 );
		
		JLabel title = new JLabel("Withdrawal");
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
		JLabel depLabel = new JLabel("- Withdrawal Amount: ");
		
		acctLabel.setFont(labelFont);
		typeLabel.setFont(labelFont);
		depLabel.setFont(labelFont);

		acctLabel.setBounds(190, 160, 150, 30);
		typeLabel.setBounds(190, 200, 150, 30);
		depLabel.setBounds(190, 240, 150, 30);

		account.setBounds(350, 160,150,30);
		crtBal.setBounds(350, 200,150,30);
		withdraw.setBounds(350, 240,150,30);
	
		this.getContentPane().add(acctLabel);
		this.getContentPane().add(typeLabel);
		this.getContentPane().add(depLabel);

		this.getContentPane().add(account);
		this.getContentPane().add(crtBal);
		this.getContentPane().add(withdraw);
		
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
					double with = Double.parseDouble(withdraw.getText());
					double bal = Double.parseDouble(crtBal.getText());
					
					if (with <= bal) {
						CashAcctControl control = new CashAcctControl();
						String accountNum = ManagementUI.getInstance().getAccountNum();
						if (control.withdrawalFrom(accountNum, with)) {
							JOptionPane.showMessageDialog(null, "Withdrawal succeeds.", "Warning", JOptionPane.INFORMATION_MESSAGE);
							singleton.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Withdrawal amount should be less than the balance.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						withdraw.setText("");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please input numbers. (e.g. 100.00)", "Warning", JOptionPane.INFORMATION_MESSAGE);
					withdraw.setText("");
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
