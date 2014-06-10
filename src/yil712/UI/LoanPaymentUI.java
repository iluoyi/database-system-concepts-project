package yil712.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import yil712.control.CashAcctControl;
import yil712.control.LoanControl;
import yil712.element.AccountLoan;
import yil712.element.AllTableModel;

public class LoanPaymentUI extends JFrame {


	private static final long serialVersionUID = 2442385248914730132L;
	private JPanel contentPane;
	private JTextField balance;
	private JTextField payment;
	private JButton confirm;
	private JButton cancel;

	private Vector<String> loansColumn = new Vector<String>();
	private Vector<Vector<String>> loansRow = new Vector<Vector<String>>();
	private JScrollPane jScrollPane_loans;
	private JTable table;
	private JTextField accountID;
	
	private static LoanPaymentUI singleton = null;	// singleton
	public static LoanPaymentUI getInstance() {
		if (singleton == null) {
			singleton = new LoanPaymentUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){
		LoanControl control = new LoanControl(); 
		String accountNum = ManagementUI.getInstance().getAccountNum();
		ArrayList<AccountLoan> list = control.getValidLoansOfAccount(accountNum);
		control.updateLoan(accountNum);
		
		loansRow.clear();
		for (AccountLoan record : list) {
			Vector<String> row = new Vector<String>();
			row.add(record.getLoanID());
			row.add("" + record.getDueAmount());
			row.add(record.getStartDate());
			row.add(record.getUpdateDate());
			loansRow.add(row);
		}
		SwingUtilities.updateComponentTreeUI(table);
		
		accountID.setText(accountNum);
		accountID.setEditable(false);
		
		CashAcctControl cashControl = new CashAcctControl(); 
		double bal = cashControl.getCashAccountBalance(accountNum);
		balance.setText("" + bal);
		balance.setEditable(false);
		
		payment.setText("");
	}

	/**
	 * Create the frame.
	 */
	public LoanPaymentUI() {
		this.setResizable(false);
		setTitle("Pay a loan");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		jScrollPane_loans = getTransPanel();
		
		JLabel lblYourAccountHas = new JLabel("Please select one loan you want to pay:");
		lblYourAccountHas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("- Current balance:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		balance = new JTextField();
		balance.setColumns(10);
		
		JLabel lblPaymentAmount = new JLabel("- Payment amount:");
		lblPaymentAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		payment = new JTextField();
		payment.setColumns(10);
		
		confirm = new JButton("Confirm");
		
		cancel = new JButton("Cancel");
		
		JLabel lblAccountNo = new JLabel("- Account No.:");
		lblAccountNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		accountID = new JTextField();
		accountID.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(21, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblYourAccountHas)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jScrollPane_loans, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(33)
											.addComponent(confirm, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
											.addComponent(cancel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
											.addGap(54))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
													.addComponent(lblNewLabel)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(balance, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
												.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
													.addComponent(lblPaymentAmount)
													.addGap(18)
													.addComponent(payment, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblAccountNo, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
									.addGap(28)
									.addComponent(accountID, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblYourAccountHas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAccountNo, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(accountID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(balance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(payment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPaymentAmount, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addGap(77)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(confirm, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(jScrollPane_loans, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)))
					.addGap(39))
		);
		contentPane.setLayout(gl_contentPane);
		
		cancelEvent();
		confirmEvent();
	}
	
	public JTable getJTransTable() {
		loansColumn.add("Loan ID");
		loansColumn.add("Due Amount");
		loansColumn.add("Start Date");
		loansColumn.add("Update Date");
		
		for (int i=0; i<1; i++){
			Vector<String> row = new Vector<String>();
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			loansRow.add(row);
		}
		
		table = new JTable(new AllTableModel(loansRow, loansColumn));
		table.setShowGrid(true);
		table.setRowHeight(30);

		return table;
	}
	
	public JScrollPane getTransPanel() {
		JScrollPane jScrollPane_loans = null;
		if (jScrollPane_loans == null) {
			jScrollPane_loans = new JScrollPane(getJTransTable());
			jScrollPane_loans.setFont(new Font("Dialog", Font.PLAIN, 14));
		}
		return jScrollPane_loans;
	}
	
	public void cancelEvent() {
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
			}
		});
	}
	
	public void confirmEvent() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int rowNum = table.getSelectedRow();
				if (rowNum > -1) {
					try {
						double payMoney = Double.parseDouble(payment.getText());
						double bal = Double.parseDouble(balance.getText());
						if (bal >= payMoney) {
							String loanID = loansRow.get(rowNum).get(0);
							double due = Double.parseDouble(loansRow.get(rowNum).get(1));
							if (payMoney <= due) {
								LoanControl control = new LoanControl(); 
								String accountNum = ManagementUI.getInstance().getAccountNum();
								if (control.makePayment(accountNum, loanID, payMoney)) {
									JOptionPane.showMessageDialog(null, "Loan payment succeeds.", "Warning", JOptionPane.INFORMATION_MESSAGE);
									singleton.dispose();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Payment cannot be greater than the due amount.", "Warning", JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Payment should be less than the current balance.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "Please input numbers for your payment.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select one loan to pay.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
}
