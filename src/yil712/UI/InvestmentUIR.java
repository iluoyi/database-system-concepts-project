package yil712.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import yil712.control.RetireAcctControl;
import yil712.control.SymbolControl;
import yil712.element.AllTableModel;
import yil712.element.Investment;

public class InvestmentUIR extends JFrame {

	private static final long serialVersionUID = -5227624965196620470L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnBuy;
	private JButton btnSell;
	private JButton btnCancel;
	private JButton update;
	private JButton browse;
	private String accountNum = null;
	
	private JRadioButton[] range = new JRadioButton[2];
	
	private Vector<String> investColumn = new Vector<String>();
	private Vector<Vector<String>> invesetRow = new Vector<Vector<String>>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvestmentUIR frame = InvestmentUIR.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private static InvestmentUIR singleton = null;	// singleton
	public static InvestmentUIR getInstance() {
		if (singleton == null) {
			singleton = new InvestmentUIR();
		}
		singleton.refreshInfo();
		return singleton;
	}
	
	public void refreshInfo() {
		range[0].setSelected(true);
		accountNum = null;
		updateTable(accountNum);
	}
	
	public void updateTable(String accountID) {
		
		RetireAcctControl control = new RetireAcctControl();
		ArrayList<Investment> stockList, fundList;
		
		if (accountID == null) {
			stockList = control.getAllStocks();
			fundList = control.getAllMutualFunds();
		} else {
			stockList = control.getAllStocksOfOneAccount(accountID);
			fundList = control.getAllMutualFundsOfOneAccount(accountID);
		}
		
		invesetRow.clear();
		for (Investment invest : stockList) {
			Vector<String> row = new Vector<String>();
			row.add(invest.getSymbol());
			row.add(invest.getTimestamp().toString());
			row.add(""+invest.getPrice());
			invesetRow.add(row);
		}
		
		for (Investment invest : fundList) {
			Vector<String> row = new Vector<String>();
			row.add(invest.getSymbol());
			row.add(invest.getTimestamp().toString());
			row.add(""+invest.getPrice());
			invesetRow.add(row);
		}
		if (!invesetRow.isEmpty()) {
			table.setRowSelectionInterval(0, 0);
		}
		SwingUtilities.updateComponentTreeUI(table);
	}

	/**
	 * Create the frame.
	 */
	public InvestmentUIR() {
		setResizable(false);
		setTitle("Investment Page");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 40));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblTickerSymbolFile = new JLabel("Ticker Symbol File:");
		panel.add(lblTickerSymbolFile);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(30);
		
		browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(null);
				File selected = fc.getSelectedFile();
				if (selected != null) 
					textField.setText(selected.getAbsolutePath());
			}
		});
		panel.add(browse);
		
		update = new JButton("Update");
		panel.add(update);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = getJTransTable();
		scrollPane.setViewportView(table);
		
		range[0] = new JRadioButton("All stocks and funds");
		range[1] = new JRadioButton("My stocks and funds");
		ButtonGroup group = new ButtonGroup();
		
		for (int i = 0;i<2;i++){
			group.add(range[i]);
		}
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(98)
							.addComponent(range[0], GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(range[1], GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(range[1])
						.addComponent(range[0]))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(110, 350));
		contentPane.add(panel_2, BorderLayout.EAST);
		
		btnBuy = new JButton("Buy");
		
		btnSell = new JButton("Sell");
		
		btnCancel = new JButton("Cancel");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(btnSell, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
						.addComponent(btnBuy, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(67)
					.addComponent(btnBuy, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(btnSell, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(110)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(52, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		refreshInfo();
		cancelEvent();
		updateEvent();
		radioBtnEvent();
		buyEvent();
		sellEvent();
	}
	
	
	public JTable getJTransTable() {
		investColumn.add("Symbol");
		investColumn.add("Timestamp");
		investColumn.add("Latest Price");
		
		for (int i=0; i<1; i++){
			Vector<String> row = new Vector<String>();
			row.add("");
			row.add("");
			row.add("");
			invesetRow.add(row);
		}
		
		JTable table = new JTable(new AllTableModel(invesetRow, investColumn));
		table.setShowGrid(true);
		table.setRowHeight(30);

		return table;
	}
	
	public void radioBtnEvent() {
		range[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				accountNum = null;
				updateTable(accountNum);
			}
		});
		
		range[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				accountNum = ManagementUI.getInstance().getAccountNum();
				updateTable(accountNum);
			}
		});
	}
	
	public void cancelEvent() {
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				singleton.dispose();
			}
		});
	}
	
	public void updateEvent() {
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = textField.getText();

				try {
					SymbolControl control = new SymbolControl();
					ArrayList<Investment> tickerList = control.readSymbolFromFile(fileName);
					if (control.updateSymbol(tickerList)) {
						JOptionPane.showMessageDialog(null, "Prices are updated.", "Warning", JOptionPane.INFORMATION_MESSAGE);
						updateTable(accountNum);
					}
					
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "File is not found.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
				

			}
		});
	}
	
	public void buyEvent() {
		btnBuy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (invesetRow.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No stocks/mutual funds to buy.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (table.getSelectedRow() < 0) {
						JOptionPane.showMessageDialog(null, "Please select the stock/mutual_fund you want to buy.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					} else {
						int index = table.getSelectedRow();
						String symbol = invesetRow.get(index).get(0).trim();
						String accountID = ManagementUI.getInstance().getAccountNum();
						BuyInvestmentUIR ui = BuyInvestmentUIR.getInstance(symbol, accountID);
						ui.setVisible(true);
					}
				}
			}
		});
	}
	
	public void sellEvent() {
		btnSell.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (invesetRow.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No stocks/mutual funds to buy.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (table.getSelectedRow() < 0) {
						JOptionPane.showMessageDialog(null, "Please select the stock/mutual_fund you want to sell.", "Warning", JOptionPane.INFORMATION_MESSAGE);
					} else {
						int index = table.getSelectedRow();
						String symbol = invesetRow.get(index).get(0).trim();
						String accountID = ManagementUI.getInstance().getAccountNum();
						SellInvestmentUIR ui = SellInvestmentUIR.getInstance(symbol, accountID);
						ui.setVisible(true);
					}
				}
			}
		});
	}
}
