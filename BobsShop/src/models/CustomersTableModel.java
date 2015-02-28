package models;

import data.DataConnection;
import elements.*;
import java.util.ArrayList;
import javax.swing.table.*;

/**
 * <pre>
 * The CustomersTableModel class.
 * encapsulates all functionality
 * related to the CustomersTableModel attributes and handling.
 *
 *
 * For example:
 * <code>
 * Customer customer = Customer.create(int userID, String userPin, String userName, String userAddress);
 * </code>
 * </pre>
 *
 * @version 1.0
 * @author Miccio,reviewed Rudolf Documentation
 * @see AbstractTableModel
 * @see data
 * @see elements.Customer
 * @todo add to report
 */
public class CustomersTableModel extends AbstractTableModel {

    // column headers
    private final String[] columnNames = {"Full name", "Address", "Active", "Current balance"};
    // data connection
    private final DataConnection conn;
    // customers
    private ArrayList<Customer> customers = null;

    /**
     *
     * @param conn DataConnection
     */
    public CustomersTableModel(DataConnection conn) {
        this.conn = conn;
        customers = conn.getCustomers();
    }

    /**
     * Get Customer instance at index.
     * 
     * @param index integer
     * @return Customer
     */
    public Customer getCustomer(int index) {
        return customers.get(index);
    }

    @Override
    public int getRowCount() {
        return customers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
                return Double.class;
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     *
     * @param rowIndex integer
     * @param columnIndex integer
     * @return Object
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return customer.getUserName();
            case 1:
                return customer.getUserAddress();
            case 2:
                return customer.isActive();
            case 3:
                return conn.getBalance(customer);
            default:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     *
     * @param columnIndex integer
     * @return String
     */
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /**
     *
     */
    public void update() {
        customers = conn.getCustomers();
    }
}
