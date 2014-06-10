package yil712.element;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 * This class generates a newly defined table model for our project
 * so that all the tables cannot be modified actively via directly 
 * click on each cell
 * @author royal (luoyi.zxx@gmail.com)
 *
 */
public class AllTableModel extends DefaultTableModel{
        
	private static final long serialVersionUID = 1L;

	/**
	 * Initialization
	 * @param data The data of the table
	 * @param columns Table's titles
	 */
	public AllTableModel(Vector<Vector<String>> data,Vector<String> columns){
            super(data,columns);
        }
   /**
    * Set cells cannot be edited
    */
    public   boolean   isCellEditable(int   row,int   column){
                return false;//can not be edited any more
        }
        /**
         * Override getColumnClass() method
         */
   public Class<?> getColumnClass(int columnIndex) {
            return Object.class;
        }
    }