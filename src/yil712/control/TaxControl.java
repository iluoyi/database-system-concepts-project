package yil712.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import yil712.element.TaxRecordOfInvest;
import yil712.element.TaxRecordOfTransfer;

public class TaxControl {
	private Connection con = null;
	
	public TaxControl() {
		con = DBConnection.getConnection();
	}
	
	public ArrayList<TaxRecordOfTransfer> getTaxRecordOfTransfer(String accountID, int taxYear) {
//		select ACCOUNT_NUM, TO_ACCOUNT_NUM, TRANSACTION_DATE, AMOUNT from
//		(select * from transfer_transaction natural join transaction where taxable = 1 and TRANSACTION_DATE >= TO_DATE('2014-01-01', 'YYYY-MM-DD'))
//		where ACCOUNT_NUM = 'A000003'	
		
		String query = "select ACCOUNT_NUM, TO_ACCOUNT_NUM, TRANSACTION_DATE, AMOUNT from " +
						"(select * from transfer_transaction natural join transaction where taxable = 1 and TRANSACTION_DATE >= TO_DATE('" +
						taxYear + "-01-01', 'YYYY-MM-DD') and TRANSACTION_DATE < TO_DATE('" + (taxYear + 1) + 
						"-01-01', 'YYYY-MM-DD')) where ACCOUNT_NUM = '" + accountID + "'";

		//System.out.println(query);

		ArrayList<TaxRecordOfTransfer> recordList = new ArrayList<TaxRecordOfTransfer>();
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
			SimpleDateFormat stdDate = new SimpleDateFormat("yyyy-MM-dd");
			
			while (result.next()) {
				String toAccount = result.getString("TO_ACCOUNT_NUM");
				String date = stdDate.format(result.getDate("TRANSACTION_DATE"));
				double amount = result.getDouble("AMOUNT");
				
				TaxRecordOfTransfer record = new TaxRecordOfTransfer(toAccount, date, amount);
				recordList.add(record);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recordList;
	}
	
	public ArrayList<TaxRecordOfInvest> getTaxRecordOfInvestment(String accountID, int shortOrLong, int taxYear) {
//	select ACCOUNT_NUM, INVEST_SYMBOL, LONG_OR_SHORT, ROUND(sum(GAIN_LOSS), 2) as total_gain_loss from
//	(select * from invest_transaction natural join transaction where long_or_short = 0 and TRANSACTION_DATE >= TO_DATE('2014-01-01', 'YYYY-MM-DD'))
//	where ACCOUNT_NUM = 'A000002'
//	group by ACCOUNT_NUM, INVEST_SYMBOL, LONG_OR_SHORT
		String query = "select ACCOUNT_NUM, INVEST_SYMBOL, LONG_OR_SHORT, ROUND(sum(GAIN_LOSS), 2) as total_gain_loss from " +
						"(select * from invest_transaction natural join transaction where long_or_short = '" + shortOrLong + "' " +
						"and TRANSACTION_DATE >= TO_DATE('" + taxYear + "-01-01', 'YYYY-MM-DD') and " +
						"TRANSACTION_DATE < TO_DATE('" + (taxYear + 1) + "-01-01', 'YYYY-MM-DD')) where ACCOUNT_NUM = '" + accountID +
						"' group by ACCOUNT_NUM, INVEST_SYMBOL, LONG_OR_SHORT";
		
		//System.out.println(query);
		
		ArrayList<TaxRecordOfInvest> recordList = new ArrayList<TaxRecordOfInvest>();
		Statement s;
		try {
			s = con.createStatement();
			
			ResultSet result;
			result = s.executeQuery(query);
	
			while (result.next()) {
				String symbol = result.getString("INVEST_SYMBOL");
				int longOrShort = result.getInt("LONG_OR_SHORT");
				double total = result.getDouble("total_gain_loss");
				
				TaxRecordOfInvest record = new TaxRecordOfInvest(symbol, longOrShort, total);
				recordList.add(record);
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recordList;
	}
	
	public String getTaxStatementInvestment(String accountID, int taxYear) {
		StringBuffer output = new StringBuffer();
		output.append(">==========================================================\n");
		output.append("Investment Account: " + accountID + "\n");
		output.append("-----------------------Short term--------------------------\n");
		output.append(String.format("%-10s\t\t%-10s\n\n", "SYMBOL", "GAIN_OR_LOSS"));
		ArrayList<TaxRecordOfInvest> recordList = getTaxRecordOfInvestment(accountID, 0, taxYear);
		double total = 0;
		for (TaxRecordOfInvest record : recordList) {
			output.append(String.format("%-10s\t\t%-10.2f\n", record.getSymbol(), record.getTotal()));
			total += record.getTotal();
		}
		output.append(String.format("\n%-10s\t\t%-10.2f\n", "Total:", total));
		output.append("\n-----------------------Long term---------------------------\n");
		output.append(String.format("%-10s\t\t%-10s\n\n", "SYMBOL", "GAIN_OR_LOSS"));
		recordList = getTaxRecordOfInvestment(accountID, 1, taxYear);
		total = 0;
		for (TaxRecordOfInvest record : recordList) {
			output.append(String.format("%-10s\t\t%-10.2f\n", record.getSymbol(), record.getTotal()));
			total += record.getTotal();
		}
		output.append(String.format("\n%-10s\t\t%-10.2f\n", "Total:", total));
		output.append("==========================================================<\n");
		return output.toString();
	}
	
	public String getTaxStatementTransfer(String accountID, int taxYear) {
		StringBuffer output = new StringBuffer();
		output.append(">==========================================================\n");
		output.append("Retirement Account: " + accountID + "\n");
		output.append("-----------------------------------------------------------\n");
		output.append(String.format("%-8s\t%-8s\t%-8s\n\n", "TO_ACCOUNT", "DATE", "AMOUNT"));
		ArrayList<TaxRecordOfTransfer> recordList = getTaxRecordOfTransfer(accountID, taxYear);
		double total = 0;
		
		for (TaxRecordOfTransfer record : recordList) {
			output.append(String.format("%-8s\t%-8s\t%-8.2f\n", record.getToAccount(), record.getDate(), record.getAmount()));
			total += record.getAmount();
		}
		output.append(String.format("\n%-8s\t%-8s\t%-8.2f\n", "Total:", "n/a", total));
		output.append("==========================================================<\n");
		return output.toString();
	}
	
	public static void main(String args[]) {
		TaxControl control = new TaxControl();
		System.out.println(control.getTaxStatementInvestment("A000002", 2014));
		System.out.println(control.getTaxStatementTransfer("A000003", 2014));
	}
}
