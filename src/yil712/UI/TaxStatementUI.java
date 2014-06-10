package yil712.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import yil712.control.TaxControl;

public class TaxStatementUI extends JFrame {

	private static final long serialVersionUID = -3838926055295462684L;
	private JPanel contentPane;
	private JTextArea statement;
	private static String customerID, accountNum;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaxStatementUI frame = TaxStatementUI.getInstance("C000001", "A000002", 1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static TaxStatementUI singleton = null;	// singleton
	public static TaxStatementUI getInstance(String customer, String account, int type) {
		if (singleton == null) {
			singleton = new TaxStatementUI(customer, account);
		} else {
			customerID = customer;
			accountNum = account;
		}
		singleton.updateInfo(type);
		return singleton;
	}

	public void updateInfo(int type) {
		TaxControl control = new TaxControl();

		int taxYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if (type == 1) {
			String statementStr = "Tax Statement of Investment in " + taxYear + "\n";
			statementStr += control.getTaxStatementInvestment(accountNum, taxYear);
			statement.setText(statementStr);
		} else if (type == 2) {
			String statementStr = "Tax Statement of Money Transfer from Retirement Account in " + taxYear + "\n";
			statementStr += control.getTaxStatementTransfer(accountNum, taxYear);
			statement.setText(statementStr);
		}
		setTitle("Customer: " + customerID);
		statement.setEditable(false);
	}
	
	public TaxStatementUI(String customer, String account) {
		customerID = customer;
		accountNum = account;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		this.setLocationRelativeTo(null);//in the center place
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		statement = new JTextArea();
		contentPane.add(statement, BorderLayout.CENTER);
	}

}
