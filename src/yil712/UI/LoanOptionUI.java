package yil712.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LoanOptionUI extends JFrame {

	private static final long serialVersionUID = -1620872857559682713L;
	private JPanel contentPane;
	private JButton openLoan;
	private JButton payLoan;

	private static LoanOptionUI singleton = null;	// singleton
	public static LoanOptionUI getInstance() {
		if (singleton == null) {
			singleton = new LoanOptionUI();
		}
		return singleton;
	}

	/**
	 * Create the frame.
	 */
	private LoanOptionUI() {
		setResizable(false);
		this.setLocationRelativeTo(null);
		setTitle("Loan Option");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400,200);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		openLoan = new JButton("Open Loan");
		openLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		payLoan = new JButton("Pay Loan");
		payLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(72)
					.addComponent(openLoan, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addComponent(payLoan, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(81))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(69)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(openLoan, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(payLoan, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
					.addGap(88))
		);
		contentPane.setLayout(gl_contentPane);
		
		OpenLoanEvent();
		PayLoanEvent();
	}
	
	public void OpenLoanEvent() {
		openLoan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoanOpenUI ui = LoanOpenUI.getInstance();
				ui.setVisible(true);
			}
		});
	}
	
	public void PayLoanEvent() {
		payLoan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LoanPaymentUI ui = LoanPaymentUI.getInstance();
				ui.setVisible(true);
			}
		});
	}
}
