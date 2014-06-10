package yil712.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import yil712.control.RetireAcctControl;
import yil712.control.SymbolControl;

public class SellInvestmentUIR extends JFrame {

	private static final long serialVersionUID = -6225573111793696789L;
	private JPanel contentPane;
	private JButton confirm;
	private JButton cancel;
	private JLabel lblCurrentBalance;
	private JLabel lblTotalAmount_1;
	private JTextField symbolFiled;
	private JTextField price;
	private JTextField numHeld;
	private JTextField numToSell;
	private JTextField toSell;
	private JTextField crtBalance;
	private JButton btnCalculate;
	private static String symbolStr, accountID;
	private int type, number;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellInvestmentUIR frame = SellInvestmentUIR.getInstance("AAN", "A000006");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static SellInvestmentUIR singleton = null;	// singleton
	public static SellInvestmentUIR getInstance(String symbol, String accountNum) {
		if (singleton == null) {
			singleton = new SellInvestmentUIR(symbol, accountNum);
		} else {
			symbolStr = symbol;
			accountID = accountNum;
		}
		singleton.upateInfo();
		return singleton;
	}
	
	public void upateInfo() {
		setTitle("Sell - Account Number: " + accountID);
		
		this.symbolFiled.setText(symbolStr);
		this.symbolFiled.setEditable(false);
		
		SymbolControl control1 = new SymbolControl();
		type = control1.getInvestType(symbolStr);
		double priceVal = control1.getCrtPrice(symbolStr, type);
		this.price.setText(""+priceVal);
		this.price.setEditable(false);
		
		RetireAcctControl control2 = new RetireAcctControl();
		int heldNum = control2.getCurrentNumOfShares(accountID, symbolStr);
		this.numHeld.setText("" + heldNum);
		this.numHeld.setEditable(false);
		
		this.numToSell.setText("");
		
		this.toSell.setText("");
		this.toSell.setEditable(false);
		
		RetireAcctControl control3 = new RetireAcctControl(); 
		double bal = control3.getRetireAccountBalance(accountID);
		crtBalance.setText("" + bal);
		crtBalance.setEditable(false);
	}

	/**
	 * Create the frame.
	 */
	private SellInvestmentUIR(String symbol, String accountNum) {
		symbolStr = symbol;
		accountID = accountNum;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(540,400);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblOpenALoan = new JLabel("Sell stocks/mutual_funds");
		lblOpenALoan.setBounds(101, 27, 333, 39);
		lblOpenALoan.setFont(new Font("Dialog", Font.PLAIN, 30));
		
		JLabel lblLoanId = new JLabel("- Symbol:");
		lblLoanId.setBounds(82, 102, 60, 17);
		lblLoanId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel priceLabel = new JLabel("- Latest price:");
		priceLabel.setBounds(82, 128, 96, 17);
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblAnnualRate = new JLabel("- Number of Shares (NoS) held:");
		lblAnnualRate.setBounds(82, 151, 202, 17);
		lblAnnualRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblThreshold = new JLabel("- NoS to sell:");
		lblThreshold.setBounds(82, 174, 96, 17);
		lblThreshold.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		confirm = new JButton("Confirm");
		confirm.setBounds(152, 292, 82, 23);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(329, 292, 75, 23);
		
		lblCurrentBalance = new JLabel("- Current balance:");
		lblCurrentBalance.setBounds(82, 220, 118, 17);
		lblCurrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblTotalAmount_1 = new JLabel("- Total amount:");
		lblTotalAmount_1.setBounds(82, 197, 96, 17);
		lblTotalAmount_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		symbolFiled = new JTextField();
		symbolFiled.setBounds(152, 102, 206, 20);
		symbolFiled.setColumns(10);
		contentPane.setLayout(null);
		contentPane.add(lblCurrentBalance);
		contentPane.add(lblTotalAmount_1);
		contentPane.add(symbolFiled);
		contentPane.add(lblAnnualRate);
		contentPane.add(priceLabel);
		contentPane.add(lblThreshold);
		contentPane.add(confirm);
		contentPane.add(cancel);
		contentPane.add(lblLoanId);
		contentPane.add(lblOpenALoan);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(178, 128, 206, 20);
		contentPane.add(price);
		
		numHeld = new JTextField();
		numHeld.setColumns(10);
		numHeld.setBounds(283, 151, 206, 20);
		contentPane.add(numHeld);
		
		numToSell = new JTextField();
		numToSell.setColumns(10);
		numToSell.setBounds(178, 174, 206, 20);
		contentPane.add(numToSell);
		
		toSell = new JTextField();
		toSell.setColumns(10);
		toSell.setBounds(188, 197, 206, 20);
		contentPane.add(toSell);
		
		crtBalance = new JTextField();
		crtBalance.setColumns(10);
		crtBalance.setBounds(198, 220, 206, 20);
		contentPane.add(crtBalance);
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.setBounds(400, 173, 89, 23);
		contentPane.add(btnCalculate);
	
		calculateEvent();
		confirmEvent();
		cancelEvent();
	}
	
	
	public void calculateEvent() {
		btnCalculate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					number = Integer.parseInt(numToSell.getText());					
					if (number > 0) {
						double crtPrice = Double.parseDouble(price.getText());
						double amount = crtPrice * number;
						toSell.setText("" + amount);
					} else {
						JOptionPane.showMessageDialog(null, "Please input a positive number.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please input only numbers in this feild.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void confirmEvent() {
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					int heldNum = Integer.parseInt(numHeld.getText());
					if (number <= heldNum) {
						RetireAcctControl control = new RetireAcctControl();
						if (control.sellStocksOrFunds(accountID, symbolStr, type, number)) {
							JOptionPane.showMessageDialog(null, "Successfully sold " + number + " of " + symbolStr + ".", "Warning", JOptionPane.INFORMATION_MESSAGE);
							singleton.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please ensure the number of shares to sell is less than the held number.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please calculate the amount to pay.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	
	public void cancelEvent() {
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
			}
		});
	}

}
