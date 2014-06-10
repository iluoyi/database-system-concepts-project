package yil712.UI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import yil712.control.AccountControl;
import yil712.control.LoanControl;
import yil712.control.TransControl;
import yil712.element.Account;
import yil712.element.AllTableModel;
import yil712.element.CashAccount;
import yil712.element.InvestAccount;
import yil712.element.RetireAccount;
import yil712.element.Transaction;

/**
 * This is the panel for account management
 * @author Yi Luo
 */
public class AccountPanel extends JPanel {

	private static final long serialVersionUID = 5273627951986163139L;
	private Vector<String> transColumn = new Vector<String>();
	private Vector<Vector<String>> transRow = new Vector<Vector<String>>();
	
	public JTable jTable_trans = null;
	private JButton jBTransferC = null;
	private JButton jBTransferI = null;
	private JButton jBTransferR = null;
	private JButton jBWithdraw = null;
	private JButton jBDeposit = null;
	private JButton jBInvestI = null;
	private JButton jBTaxI = null;
	private JButton jBTaxR = null;
	private JButton jBInvestR = null;
	private JButton jBLoan = null;
	private JButton jBCreate = null;
	private JButton jBDelete = null;
	
	private JRadioButton[] accountTypes = new JRadioButton[3];
	private String accountType = null;
	private JComboBox jComboBox_account = null;
	
	private JTextField type = new JTextField("");
	private JTextField accountNum = new JTextField("");
	private JTextField balance = new JTextField("");

	private JScrollPane jScrollPane_trans = null;
	private CardLayout c = new CardLayout();
	private JPanel actionPanel = null;
	
	public String getAccountID() {
		if (accountNum != null) {
			return accountNum.getText();
		}
		return null;
	}

	public void cleanJTextFileds() {
		type.setText("");
		accountNum.setText("");
		balance.setText("");
		type.setEditable(false);
		accountNum.setEditable(false);
		balance.setEditable(false);
	}
	/**
	 * This is the default constructor
	 */
	public AccountPanel() {
		super();
		initialize();
	
		jBDeposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
	}
		
	/**
	 * This method initializes this
	 */
	public void initialize() {
		this.setLayout(new BorderLayout());
		this.setBounds(new Rectangle(0, 0, 780, 530));
		this.add(getOperationPanel(), BorderLayout.WEST);
		this.add(getTransPanel(), BorderLayout.CENTER);
		cleanJTextFileds();
		
		checkBoxEvent();
		jComboBoxEvent();
		newAccountEvent();
		depositEvent();
		withdrawEvent();
		transferEvent();
		LoanEvent();
		investIEvent();
		taxEvent();
		deleteAccountEvent();
	}
	
	public JPanel getOperationPanel() {
		JPanel jPanel_operations = new JPanel();
		jPanel_operations.setLayout(new BorderLayout());
		jPanel_operations.add(getAccOptionsPanel(), BorderLayout.NORTH);
		jPanel_operations.add(getAccInfoPanel(), BorderLayout.CENTER);
		jPanel_operations.add(getButtonPanel(), BorderLayout.SOUTH);
		return jPanel_operations;
	}

	public JPanel getAccOptionsPanel() {
		JPanel jPanel_AccOption = new JPanel();
		
		JPanel infoLabelPanel = new JPanel();
		JLabel info = new JLabel("---Select one account---");
		infoLabelPanel.add(info);
		infoLabelPanel.setPreferredSize(new Dimension(350, 25));
		
		JPanel upperPanel = new JPanel();
		accountTypes[0] = new JRadioButton("Cash");
		accountTypes[1] = new JRadioButton("Investment");
		accountTypes[2] = new JRadioButton("Retirement");
		ButtonGroup group = new ButtonGroup();
		
		for (int i = 0;i<3;i++){
			upperPanel.add(accountTypes[i]);
			group.add(accountTypes[i]);
			accountTypes[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					accountType = e.getActionCommand();
				}
			});
		}
		
		JPanel lowerPanel = new JPanel();
		jComboBox_account = new JComboBox();
		jComboBox_account.addItem("Select the account type...");
		jComboBox_account.setPreferredSize(new Dimension(250, 25));
		lowerPanel.add(jComboBox_account);
		
		jPanel_AccOption.setPreferredSize(new Dimension(350, 120));
		jPanel_AccOption.add(infoLabelPanel);
		jPanel_AccOption.add(upperPanel);
		jPanel_AccOption.add(lowerPanel);
		return jPanel_AccOption;
	}
	
	public JPanel getAccInfoPanel() {
		JPanel jPanel_AccInfo = new JPanel();
		
		JPanel infoLabelPanel = new JPanel();
		JLabel info = new JLabel("---Account information---");
		infoLabelPanel.add(info);
		infoLabelPanel.setPreferredSize(new Dimension(350, 25));
		
		JPanel typePanel = new JPanel();
		typePanel.add(new JLabel("Type:            "));
		typePanel.add(type);
		type.setPreferredSize(new Dimension(200, 25));
		typePanel.setPreferredSize(new Dimension(350, 30));
		
		JPanel numPanel = new JPanel();
		numPanel.add(new JLabel("AccountNo: "));
		numPanel.add(accountNum);
		accountNum.setPreferredSize(new Dimension(200, 25));
		numPanel.setPreferredSize(new Dimension(350, 30));
		
		JPanel balPanel = new JPanel();
		balPanel.add(new JLabel("Balance:      "));
		balPanel.add(balance);
		balance.setPreferredSize(new Dimension(200, 25));
		balPanel.setPreferredSize(new Dimension(350, 30));
	
		jPanel_AccInfo.add(infoLabelPanel);
		jPanel_AccInfo.add(typePanel);
		jPanel_AccInfo.add(numPanel);
		jPanel_AccInfo.add(balPanel);
		jPanel_AccInfo.setPreferredSize(new Dimension(350, 80));
		return jPanel_AccInfo;
	}
	
	public JPanel getButtonPanel() {
			JPanel jButtonPanel = new JPanel();
			
			JPanel infoLabelPanel = new JPanel();
			JLabel info = new JLabel("---Select one action---");
			infoLabelPanel.add(info);
			infoLabelPanel.setPreferredSize(new Dimension(350, 25));
		
			actionPanel = new JPanel(c); // CardLayout
			
			actionPanel.add(getCashButtonPanel(), "cash");
			actionPanel.add(getInvestButtonPanel(), "invest");
			actionPanel.add(getRetireButtonPanel(), "retire");
			
			jButtonPanel.add(infoLabelPanel);
			jButtonPanel.add(actionPanel);
			jButtonPanel.add(getJBCreate());
			jButtonPanel.add(getJBDelete());
			jButtonPanel.setPreferredSize(new Dimension(350, 250));
		return jButtonPanel;
	}
	
	
	public JPanel getCashButtonPanel() {
		JPanel jPanel_button = null;
				
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(20);
				
		jPanel_button = new JPanel();
		jPanel_button.setLayout(flowLayout);
		jPanel_button.setPreferredSize(new Dimension(350, 120));
		jPanel_button.add(getJBDeposit(), null);
		jPanel_button.add(getJBWithdraw(), null);
		jPanel_button.add(getJBTransferC(), null);
		jPanel_button.add(getJBLoan(), null);
	return jPanel_button;
	}
	
	public JPanel getInvestButtonPanel() {
		JPanel jPanel_button = null;
				
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(20);
				
		jPanel_button = new JPanel();
		jPanel_button.setLayout(flowLayout);
		jPanel_button.setPreferredSize(new Dimension(350, 120));
		jPanel_button.add(getJBTransferI(), null);
		jPanel_button.add(getJBInvestI(), null);
		jPanel_button.add(getJBTaxI(), null);
	return jPanel_button;
	}
	
	public JPanel getRetireButtonPanel() {
		JPanel jPanel_button = null;
				
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(20);
				
		jPanel_button = new JPanel();
		jPanel_button.setLayout(flowLayout);
		jPanel_button.setPreferredSize(new Dimension(350, 120));
		jPanel_button.add(getJBTransferR(), null);
		jPanel_button.add(getJBInvestR(), null);
		jPanel_button.add(getJBTaxR(), null);
	return jPanel_button;
	}
	
	public JScrollPane getTransTablePanel() {
		if (jScrollPane_trans == null) {
			jScrollPane_trans = new JScrollPane(getJTransTable());
			jScrollPane_trans.setFont(new Font("Dialog", Font.PLAIN, 14));
		}
		return jScrollPane_trans;
	}
	
	public JPanel getTransPanel() {
		JPanel jPanel_trans = new JPanel();
		jPanel_trans.setLayout(new BorderLayout());
		
		JPanel infoLabelPanel = new JPanel();
		JLabel info = new JLabel("Financial transaction history of this account:");
		infoLabelPanel.add(info);
		
		jPanel_trans.add(infoLabelPanel, BorderLayout.NORTH);
		jPanel_trans.add(getTransTablePanel(), BorderLayout.CENTER);

		return jPanel_trans;
	}

	public JTable getJTransTable() {
		transColumn.add("Date");
		transColumn.add("Type");
		transColumn.add("Receiver");
		transColumn.add("Amount");
		
		for (int i=0; i<50; i++){
			Vector<String> row = new Vector<String>();
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			transRow.add(row);
		}

		if (jTable_trans == null) {
			jTable_trans = new JTable(new AllTableModel(transRow, transColumn));
			jTable_trans.setShowGrid(true);
			jTable_trans.setRowHeight(30);
		}
		return jTable_trans;
	}

	public CardLayout getC() {
		return c;
	}

	public JButton getJBDeposit() {
		if (jBDeposit == null) {
			jBDeposit = new JButton();
			jBDeposit.setText("Deposit");
			jBDeposit.setPreferredSize(new Dimension(150, 30));
			jBDeposit.setEnabled(false);
		}
		return jBDeposit;
	}

	public JButton getJBTransferC() {
		if (jBTransferC == null) {
			jBTransferC = new JButton();
			jBTransferC.setText("Transfer");
			jBTransferC.setPreferredSize(new Dimension(150, 30));
			jBTransferC.setEnabled(false);
		}
		return jBTransferC;
	}
	
	public JButton getJBTransferI() {
		if (jBTransferI == null) {
			jBTransferI = new JButton();
			jBTransferI.setText("Transfer");
			jBTransferI.setPreferredSize(new Dimension(150, 30));
			jBTransferI.setEnabled(false);
		}
		return jBTransferI;
	}
	
	public JButton getJBTransferR() {
		if (jBTransferR == null) {
			jBTransferR = new JButton();
			jBTransferR.setText("Transfer");
			jBTransferR.setPreferredSize(new Dimension(150, 30));
			jBTransferR.setEnabled(false);
		}
		return jBTransferR;
	}
	
	public JButton getJBTaxI() {
		if (jBTaxI == null) {
			jBTaxI = new JButton();
			jBTaxI.setText("Tax Info");
			jBTaxI.setPreferredSize(new Dimension(150, 30));
			jBTaxI.setEnabled(false);
		}
		return jBTaxI;
	}
	
	public JButton getJBTaxR() {
		if (jBTaxR == null) {
			jBTaxR = new JButton();
			jBTaxR.setText("Tax Info");
			jBTaxR.setPreferredSize(new Dimension(150, 30));
			jBTaxR.setEnabled(false);
		}
		return jBTaxR;
	}
	
	public JButton getJBLoan() {
		if (jBLoan == null) {
			jBLoan = new JButton();
			jBLoan.setText("Loan");
			jBLoan.setPreferredSize(new Dimension(150, 30));
			jBLoan.setEnabled(false);
		}
		return jBLoan;
	}
	
	public JButton getJBInvestI() {
		if (jBInvestI == null) {
			jBInvestI = new JButton();
			jBInvestI.setText("Invest");
			jBInvestI.setPreferredSize(new Dimension(150, 30));
			jBInvestI.setEnabled(false);
		}
		return jBInvestI;
	}
	
	public JButton getJBInvestR() {
		if (jBInvestR == null) {
			jBInvestR = new JButton();
			jBInvestR.setText("Invest");
			jBInvestR.setPreferredSize(new Dimension(150, 30));
			jBInvestR.setEnabled(false);
		}
		return jBInvestR;
	}

	public JButton getJBWithdraw() {
		if (jBWithdraw == null) {
			jBWithdraw = new JButton();
			jBWithdraw.setText("Withdrawal");
			jBWithdraw.setPreferredSize(new Dimension(150, 30));
			jBWithdraw.setEnabled(false);
		}
		return jBWithdraw;
	}
	
	public JButton getJBCreate() {
		if (jBCreate == null) {
			jBCreate = new JButton();
			jBCreate.setText("New Account");
			jBCreate.setPreferredSize(new Dimension(150, 30));
		}
		return jBCreate;
	}
	
	public JButton getJBDelete() {
		if (jBDelete == null) {
			jBDelete = new JButton();
			jBDelete.setText("Delete Account");
			jBDelete.setPreferredSize(new Dimension(150, 30));
		}
		return jBDelete;
	}
	
	public void checkBoxEvent() {
		accountTypes[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cleanJTextFileds();
				jBTransferC.setEnabled(true);
				jBDeposit.setEnabled(true);
				jBWithdraw.setEnabled(true);
				jBLoan.setEnabled(true);
				
				c.show(actionPanel, "cash");
				String customerID = StatusBar.getInstance().getCustomerID();
				
				AccountControl control = new AccountControl();
				ArrayList<CashAccount> accountList = control.getValidCashAccounts(customerID);
				jComboBox_account.removeAllItems();
				if (accountList != null && accountList.size() > 0) {
					jComboBox_account.addItem("Select one cash account...");
					for (CashAccount account : accountList) {
						jComboBox_account.addItem(account.getAccountID());
					}
				} else {
					jComboBox_account.addItem("No cash account...");
				}
			}
		});
		
		accountTypes[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cleanJTextFileds();
				jBTransferI.setEnabled(true);
				jBInvestI.setEnabled(true);
				jBTaxI.setEnabled(true);
				
				c.show(actionPanel, "invest");
				String customerID = StatusBar.getInstance().getCustomerID();
				
				AccountControl control = new AccountControl();
				ArrayList<InvestAccount> accountList = control.getValidInvestAccounts(customerID);
				jComboBox_account.removeAllItems();
				if (accountList != null && accountList.size() > 0) {
					jComboBox_account.addItem("Select one investment account...");
					for (InvestAccount account : accountList) {
						jComboBox_account.addItem(account.getAccountID());
					}
				} else {
					jComboBox_account.addItem("No cash account...");
				}
			}
		});
		
		accountTypes[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cleanJTextFileds();
				jBTransferR.setEnabled(true);
				jBInvestR.setEnabled(true);
				jBTaxR.setEnabled(true);
				
				c.show(actionPanel, "retire");
				String customerID = StatusBar.getInstance().getCustomerID();
				
				AccountControl control = new AccountControl();
				ArrayList<RetireAccount> accountList = control.getValidRetireAccounts(customerID);
				jComboBox_account.removeAllItems();
				if (accountList != null && accountList.size() > 0) {
					jComboBox_account.addItem("Select one retirement account...");
					for (RetireAccount account : accountList) {
						jComboBox_account.addItem(account.getAccountID());
					}
				} else {
					jComboBox_account.addItem("No cash account...");
				}
			}
		});
	}
	
	public void jComboBoxEvent() {
		jComboBox_account.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = (String) jComboBox_account.getSelectedItem();
				String customerID = StatusBar.getInstance().getCustomerID();
				AccountControl control = new AccountControl();
				Account account = control.getOneAccount(customerID, accountID);
				
				if (account != null) {
					accountNum.setText(accountID);
					int acctType = 0;
					double bal = 0;
					if (accountType.equals("Cash")) {
						acctType = 1;
						type.setText("Cash Account");
						bal = ((CashAccount) account).getBalance();
						
						// check the alarm of margin loans
						LoanControl loanControl = new LoanControl();
						double threshold = loanControl.getHighestThreshold(accountID);
						if (bal < threshold) {
							JOptionPane.showMessageDialog(null, "Alarm! You balance is lower than the threshold of a loan in your account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						}
					} else if (accountType.equals("Investment")) {
						acctType = 2;
						type.setText("Investment Account");
						bal = ((InvestAccount) account).getBalance();
					} else if (accountType.equals("Retirement")) {
						acctType = 3;
						type.setText("Retirement Account");
						bal = ((RetireAccount) account).getBalance();
					}
					balance.setText(""+bal);
					updateTransaction(accountID, acctType);
				}
			}
		});
	}
	
	public void newAccountEvent() {
		jBCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewAccountUI ui = NewAccountUI.getInstance();
				ui.setVisible(true);
			}
		});
	}
	
	public void deleteAccountEvent() {
		jBDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					int option = JOptionPane.showConfirmDialog(null, 
							"Please withdraw cashes, pay loans and sell your stocks/funds before you invalidate this account." +
							"You are going to invalidate this account, are you sure?", "Warning", JOptionPane.INFORMATION_MESSAGE);
					if (option == 0) {
						AccountControl control = new AccountControl();
						control.deleteAccount(accountID);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select an account to invalidate.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void depositEvent() {
		jBDeposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					DepositUI ui = DepositUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a cash account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void withdrawEvent() {
		jBWithdraw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					WithdrawUI ui = WithdrawUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a cash account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void transferEvent() {
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					TransferUI ui = TransferUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select an account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		jBTransferC.addActionListener(listener);
		jBTransferI.addActionListener(listener);
		jBTransferR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					JOptionPane.showMessageDialog(null, "Note, if you transfer money from the Retirement Account, you will be charged with 25% processing fees " +
							"and the remaining amount will be recorded for tax statement.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					TransferUI ui = TransferUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select an account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void LoanEvent() {
		jBLoan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					LoanOptionUI ui = LoanOptionUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a cash account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void investIEvent() {
		jBInvestI.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					InvestmentUI ui = InvestmentUI.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select an investment account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		jBInvestR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					InvestmentUIR ui = InvestmentUIR.getInstance();
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a retirement account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void taxEvent() {
		jBTaxI.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					String customerID = StatusBar.getInstance().getCustomerID();
					TaxStatementUI ui = TaxStatementUI.getInstance(customerID, accountID, 1);
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select an investment account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		jBTaxR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String accountID = accountNum.getText();
				if (!accountID.equals("")) {
					String customerID = StatusBar.getInstance().getCustomerID();
					TaxStatementUI ui = TaxStatementUI.getInstance(customerID, accountID, 2);
					ui.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a retirement account.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void updateTransaction(String accountID, int acctType) {
		TransControl control = new TransControl();
		ArrayList<Transaction> transactions = null;
		if (acctType == 1) {
			transactions = control.getCashAcctTransactions(accountID);
		} else if (acctType == 2) {
			transactions = control.getInvestAcctTransactions(accountID);
		} else if (acctType == 3) {
			transactions = control.getRetireAcctTransactions(accountID);
		}
		
		transRow.clear();
		if (transactions != null) {
			for (Transaction trans : transactions) {
				Vector<String> row = new Vector<String>();
				row.add(trans.getDate().toString());
				row.add(trans.getType());
				row.add(trans.getReceiver());
				row.add("" + trans.getAmount());
				transRow.add(row);
			}
			SwingUtilities.updateComponentTreeUI(jTable_trans);
		}
	}
} 