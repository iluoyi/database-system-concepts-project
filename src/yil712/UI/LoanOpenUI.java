package yil712.UI;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import yil712.control.LoanControl;
import yil712.control.Utility;
import yil712.element.Loan;

public class LoanOpenUI extends JFrame {


	private static final long serialVersionUID = 28616069083808668L;
	private JPanel contentPane;
	private JTextField loanID;
	private JTextField amount;
	private JTextField rate;
	private JTextField threshold;
	private JButton confirm;
	private JButton cancel;
	
	private static LoanOpenUI singleton = null;	// singleton
	private JLabel label;
	public static LoanOpenUI getInstance() {
		if (singleton == null) {
			singleton = new LoanOpenUI();
		}
		singleton.updateInfo();
		return singleton;
	}
	
	public void updateInfo(){
		LoanControl control = new LoanControl(); 
		String newLoanID = Utility.getNewID(control.getLatestLoanID()); 
		
		loanID.setText(newLoanID);
		amount.setText("");
		rate.setText("");
		threshold.setText("");
		loanID.setEditable(false);
	}

	/**
	 * Create the frame.
	 */
	private LoanOpenUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblOpenALoan = new JLabel("Open a Loan");
		lblOpenALoan.setFont(new Font("Dialog", Font.PLAIN, 30));
		
		JLabel lblLoanId = new JLabel("- Loan ID:");
		lblLoanId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		loanID = new JTextField();
		loanID.setColumns(10);
		
		amount = new JTextField();
		amount.setColumns(10);
		
		JLabel lblTotalAmount = new JLabel("- Total amount:");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		rate = new JTextField();
		rate.setColumns(10);
		
		JLabel lblAnnualRate = new JLabel("- Annual rate:");
		lblAnnualRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		threshold = new JTextField();
		threshold.setColumns(10);
		
		JLabel lblThreshold = new JLabel("- Threshold:");
		lblThreshold.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		confirm = new JButton("Confirm");
		
		cancel = new JButton("Cancel");
		
		label = new JLabel("%");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(146)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblAnnualRate, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rate, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblTotalAmount, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(amount, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblThreshold, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
									.addComponent(threshold, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(confirm)
									.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
									.addComponent(cancel))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblLoanId)
									.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
									.addComponent(loanID, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(189)
							.addComponent(lblOpenALoan)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addContainerGap(109, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addComponent(lblOpenALoan)
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLoanId)
						.addComponent(loanID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalAmount, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(amount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAnnualRate, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(rate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblThreshold, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(threshold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(confirm)
						.addComponent(cancel))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		cancelEvent();
		confirmEvent();
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
				String loanIDStr = loanID.getText();
				try {
					double totalAmount = Double.parseDouble(amount.getText());
					double interestRate = Double.parseDouble(rate.getText());
					double thresholdVal = Double.parseDouble(threshold.getText());
	
					if (totalAmount >= 0 && interestRate >= 0 && thresholdVal >= 0) {
						LoanControl control = new LoanControl(); 
						Loan loan = new Loan(loanIDStr, totalAmount, interestRate, thresholdVal);
						String accountNum = ManagementUI.getInstance().getAccountNum();
						if (control.createNewLoan(accountNum, loan)) {
							JOptionPane.showMessageDialog(null, "Open loan succeeds.", "Warning", JOptionPane.INFORMATION_MESSAGE);
							singleton.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Total amount, annual rate and threshold should not be negative.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Only numbers for Total amount, annual rate and threshold.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					updateInfo();
				}
			}
		});
	}
}
