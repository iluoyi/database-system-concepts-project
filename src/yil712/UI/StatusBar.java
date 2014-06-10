package yil712.UI;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 *This class generates the widely used status bar at the bottom 
 * @author Yi Luo
 */
public class StatusBar extends JSplitPane {

	private static final long serialVersionUID = -7145495578384512885L;
	public JLabel jLabel_customerID = null;
	public JLabel jLabel_userID = null;
	private JTextField jTextField_time = null;
	
	public static JButton jbt_logout = new JButton("Logout");
	public static JButton jbt_exit = new JButton("Exit");
	
	private static StatusBar singleton = null;	// singleton
	public static StatusBar getInstance() {
		if (singleton == null) {
			singleton = new StatusBar();
		}
		return singleton;
	}

	/**
	 * This method initializes StatusBar
	 */
	private StatusBar(){
		this.setResizeWeight(0.5D);
		this.setDividerSize(6);
		this.setPreferredSize(new Dimension(780, 30));
		this.setLeftComponent(getJSplitPane_status1());
		this.setRightComponent(getJSplitPane_status2());
		this.setContinuousLayout(true);
	}
	
	public String getCustomerID() {
		if (jLabel_customerID != null) {
			return jLabel_customerID.getText();
		}
		return null;
	}
	
	public String getUserID() {
		if (jLabel_userID != null) {
			return jLabel_userID.getText();
		}
		return null;
	}
	
	public void setCustomerID(String text) {
		jLabel_customerID.setText(text);
	}
	
	public void setUserID(String text) {
		jLabel_userID.setText(text);
	}
	
	/**
	 *  This method initializes jbt_logout
	 * @return jbt_logout
	 */
	public JButton getJbt_Logout(){
		return jbt_logout;
	}
	/**
	 *   This method initializes jbt_exit
	 * @return jbt_exit
	 */
	public JButton getJbt_Exit(){
		return jbt_exit;
	}
	
	/**
	 * This method initializes jSplitPane_status1
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane_status1() {
			JSplitPane jSplitPane_status1 = new JSplitPane();
			jSplitPane_status1.setResizeWeight(0.4D);
			jSplitPane_status1.setDividerSize(4);
			jSplitPane_status1.setRightComponent(getJSplitPane_position());
			jSplitPane_status1.setLeftComponent(getJSplitPane_userid());
		return jSplitPane_status1;
	}

	/**
	 * This method initializes jSplitPane_status2
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane_status2() {
			JSplitPane jSplitPane_status2 = new JSplitPane();
			jSplitPane_status2.setResizeWeight(0.6D);
			jSplitPane_status2.setDividerSize(6);
			JPanel temp = new JPanel(new GridLayout(1,2));
			temp.add(jbt_logout);
			temp.add(jbt_exit);
			jSplitPane_status2.setRightComponent(temp);
			jSplitPane_status2.setLeftComponent(getJTextField_time());
		return jSplitPane_status2;
	}

	/**
	 * This method initializes jSplitPane_userid
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane_userid() {
			JSplitPane jSplitPane_userid = null;
			JLabel jLabel_id = null;
			jLabel_customerID = new JLabel();
			jLabel_customerID.setText("C000001");
			jLabel_id = new JLabel();
			jLabel_id.setText(" Target CustomerID:");
			jSplitPane_userid = new JSplitPane();
			jSplitPane_userid.setResizeWeight(0.1D);
			jSplitPane_userid.setDividerSize(3);
			jSplitPane_userid.setLeftComponent(jLabel_id);
			jSplitPane_userid.setRightComponent(jLabel_customerID);
		return jSplitPane_userid;
	}

	/**
	 * This method initializes jSplitPane_postion
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane_position() {
			JSplitPane jSplitPane_position = null;
			JLabel jLabel_position = null;
			jLabel_userID = new JLabel();
			jLabel_userID.setText("");
			jLabel_position = new JLabel();
			jLabel_position.setText(" Current UserID:");
			jSplitPane_position = new JSplitPane();
			jSplitPane_position.setResizeWeight(0.1D);
			jSplitPane_position.setDividerSize(3);
			jSplitPane_position.setRightComponent(jLabel_userID);
			jSplitPane_position.setLeftComponent(jLabel_position);
		return jSplitPane_position;
	}

	/**
	 * This method initializes jTextField_time
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_time() {
		if (jTextField_time == null) {
			jTextField_time = new JTextField();
			Timer timer = new Timer(1000,  new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					jTextField_time.setText( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			});
			timer.start();
			jTextField_time.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return jTextField_time;
	}

}
