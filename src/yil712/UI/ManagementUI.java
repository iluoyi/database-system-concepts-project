package yil712.UI;
import java.awt.*;

import javax.swing.*;

/**
 * This is the account interface of customers
 * @author Yi Luo
 */
public class ManagementUI extends UserInterface {

	private static final long serialVersionUID = -8985282438296476933L;

	private static AccountPanel acocuntPanel = null;

	private static ManagementUI singleton = null;	// singleton
	public static ManagementUI getInstance() {
		if (singleton == null) {
			singleton = new ManagementUI();
		}
		return singleton;
	}
	
	public String getAccountNum() {
		return acocuntPanel.getAccountID();
	}
	
	/**
	 * This is the default constructor
	 */
	private ManagementUI() {
		super();
	}
	
	/**
	 * This method initializes jTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	protected JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setPreferredSize(new Dimension(780, 530));
			jTabbedPane.addTab("Accounts", null, getAccountPanel(), null);

		}
		return jTabbedPane;
	}	

	/**
	 * This method initializes cash_Panel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getAccountPanel() {
		if (acocuntPanel == null) {
			acocuntPanel = new AccountPanel();
		}
		return acocuntPanel;
	}
	
	public static void main(String args[]) {
		ManagementUI manage = ManagementUI.getInstance();
		manage.setVisible(true);
	}
}
