package yil712.UI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is the super interface
 * @author Yi Luo
 */
public abstract class UserInterface extends JFrame{

	private static final long serialVersionUID = 1303808465319603203L;
	private static StatusBar statusBar = null;
	protected JTabbedPane jTabbedPane = null;
	
	/**
	 * This is the default constructor
	 */
	public UserInterface() {
		super();
		initialize();
		
		statusBar.getJbt_Exit().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					System.exit(0);
			}
		});
		
		statusBar.getJbt_Logout().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ManagementUI.getInstance().dispose();
				LoginUI.getInstance().setVisible(true);
		}
	});
	}
	
	/**
	 * This method initializes this
	 */
	protected void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);//in the center place
		this.setContentPane(getJContentPane());
		this.setTitle("Merry Lunch Professional Finance & Service Brokerage Firm - by Yi Luo (yil712)");
		this.setVisible(false);
	}
	
	protected JPanel getJContentPane() {
		JPanel jContentPane = null;
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jContentPane.setFont(new Font("Dialog", Font.PLAIN, 12));
			jContentPane.setPreferredSize(new Dimension(800, 600));
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getStatusBar(), BorderLayout.SOUTH);
		return jContentPane;
	}
	
	private StatusBar getStatusBar() {
		if (statusBar == null) {
			statusBar = StatusBar.getInstance();
		}
		return statusBar;
	}

	protected JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setPreferredSize(new Dimension(780, 530));
		}	
		return jTabbedPane;
	}

	public String getCustomerID(){
		if (statusBar != null) {
			return statusBar.getCustomerID();
		}
		return null;
	}
	
	public String getUserID() {
		if (statusBar != null) {
			return statusBar.getUserID();
		}
		return null;
	}
	
	public void setCustomerID(String customerID) {
		if (statusBar != null) {
			statusBar.setCustomerID(customerID);
		}
	}

	public void setUserID(String userID) {
		if (statusBar != null) {
			statusBar.setUserID(userID);
		}
	}
}