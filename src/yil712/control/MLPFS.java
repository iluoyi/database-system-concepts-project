package yil712.control;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import yil712.UI.LoginUI;
import yil712.element.InvestAccount;
import yil712.element.Investment;
import yil712.element.RetireAccount;

/**
 * The main-method class of the final project of cse341.
 * 
 * @author Yi Luo
 *
 */
public class MLPFS {
	
	public static void main(String args[]) {	
		if (args.length == 0) {
			System.out.println("Welcome! You are entering the GUI mode...");
			LoginUI login = LoginUI.getInstance();
			login.setVisible(true);
		} else if (args.length == 2) { // program trading
			if (args[0].equals("-ticker")) {
				System.out.println("Welcome! You are entering the ticker symbol reader mode...");
				String fileName = args[1];
				SymbolControl control = new SymbolControl();
				ArrayList<Investment> tickerList;
				try {
					tickerList = control.readSymbolFromFile(fileName);
					if (control.updateSymbol(tickerList)) {
						System.out.println("Ticker symbols updated successfully.");
					} else {
						System.out.println("Failed to update ticker symbols due to database exceptions.");
					}
				} catch (FileNotFoundException e) {
					System.out.println("For ticker reader: the ticker symbol file is not found.");
				}
			} else if (args[0].equals("-tin")) {
				String accountID = args[1];
				AccountControl control = new AccountControl();
				if (control.getAccountType(accountID) == 2) {
					ProgramTrading.executeTransactions(accountID);
				} else {
					System.out.println("For program trading: you can only query an investment account.");
				}
			} else if (args[0].equals("-tout")) {
				String accountID = args[1];
				AccountControl control = new AccountControl();
				if (control.getAccountType(accountID) == 2) {
					ProgramTrading.outputTransactions(accountID);
				} else {
					System.out.println("For program trading: you can only query an investment account.");
				}
			} else {
				System.out.println("Usage:");
				System.out.println("For ticker reader: >java -jar MLPFS.jar -ticker [tickerFile.txt]");
				System.out.println("For program trading in: >java -jar MLPFS.jar -tin [accountID]");
				System.out.println("For program trading out: >java -jar MLPFS.jar -tout [accountID]");
			}
		} else if (args.length == 3) { // tax statement
			if (args[0].equals("-tax")) {
				try {
					int taxYear = Integer.parseInt(args[1]);
					String customerID = args[2];
					
					CustomerControl control = new CustomerControl();
					if (control.isExistedCustomer(customerID)) {
						System.out.println("Tax statement of customer: " + customerID + " in " + taxYear);
						AccountControl control2 = new AccountControl();
						TaxControl control3 = new TaxControl();
						// get all investment accounts
						ArrayList<InvestAccount> investAccounts = control2.getValidInvestAccounts(customerID);
						for (InvestAccount invest : investAccounts) {
							System.out.println(control3.getTaxStatementInvestment(invest.getAccountID(), taxYear));
						}
						
						// get all retirement accounts
						ArrayList<RetireAccount> retireAccounts = control2.getValidRetireAccounts(customerID);
						for (RetireAccount retire : retireAccounts) {
							System.out.println(control3.getTaxStatementTransfer(retire.getAccountID(), taxYear));
						}
					} else {
						System.out.println("For tax statement: the customerID must exist in current system.");
					}
					
				} catch (NumberFormatException e) {
					System.out.println("For tax statement: the second argument must be an integer (tax year).");
				}
			} else {
				System.out.println("Usage:");
				System.out.println("For tax statement: >java -jar MLPFS.jar -tax [taxYear] [customerID]");
			}
		} else {
			System.out.println("Usage:");
			System.out.println(String.format("%-30s\t%s", "For GUI mode:", ">java -jar MLPFS.jar"));
			System.out.println(String.format("%-30s\t%s", "For ticker reader:", ">java -jar MLPFS.jar -ticker [tickerFile.txt]"));
			System.out.println(String.format("%-30s\t%s", "For program trading INPUT:", ">java -jar MLPFS.jar -tin [accountID]"));
			System.out.println(String.format("%-30s\t%s", "For program trading OUTPUT:", ">java -jar MLPFS.jar -tout [accountID]"));
			System.out.println(String.format("%-30s\t%s", "For tax statement:", ">java -jar MLPFS.jar -tax [taxYear] [customerID]"));
		}
		DBConnection.closeConnection();
	}
}
