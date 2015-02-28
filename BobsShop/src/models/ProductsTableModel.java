package models;

import data.DataConnection;
import elements.*;
import java.util.ArrayList;
import javax.swing.table.*;

/**
 * <pre>
 * The ProductsTableModel class.
 * encapsulates all functionality
 * related to the ProductsTableModel attributes and handling.
 *
 *
 * For example:
 * <code>
 * Product product = Product.create(int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold);
 * </code>
 * </pre>
 *
 * @version 1.1
 * @since change the field to final
 * @author Miccio,reviewed and Documentation by Yin
 * @see AbstractTableModel
 * @see data
 * @see elements.Product
 * @todo add to report
 */

public class ProductsTableModel extends AbstractTableModel
{
	// column headers
	private final String[] columnNames={"Code", "Name", "U. cost", "U. price", "Qty. left", "Threshold"};
	
	// data connection
	private final DataConnection conn;

	// products
	private ArrayList<Product> products = null;

    /**
     * <pre>
     * Constructor.
     * 
     * Factory internal Constructor.
     * </pre>
     * @param conn DataConnection
     */
        
    public ProductsTableModel(DataConnection conn)
	{
		this.conn=conn;
		products=conn.getProducts();
	}

    /**
     * <pre>
     * get product.
     * Get Product instance at index.
     * </pre>
     * 
     * @param index
     * @return Product
     */
    
    public Product getProduct(int index)
	{
		return products.get(index);
	}

    /**
     * @override
     * @return integer
     */
    @Override
	public int getRowCount()
	{
		return products.size();
	}

    /**
     * @override
     * @return integer
     */
    @Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

    /**
     * @override
     * @param columnIndex
     * @return Class
     */
    @Override
	public Class getColumnClass(int columnIndex)
	{
		switch(columnIndex)
		{
			case 0:
				return Integer.class;
			case 1:
				return String.class;
			case 2:
				return Double.class;
			case 3:
				return Double.class;
			case 4:
				return Integer.class;
			case 5:
				return Integer.class;
			default:
				throw new UnsupportedOperationException("Not supported yet.");
		}
	}

    /**
     * @override
     * @param rowIndex
     * @param columnIndex
     * @return boolean
     */
    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

    /**
     * @override
     * @param rowIndex
     * @param columnIndex
     * @return Object
     */
    @Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Product a=products.get(rowIndex);
		switch(columnIndex)
		{
			case 0:
				return a.getProdID();
			case 1:
				return a.getProdName();
			case 2:
				return a.getProdCost();
			case 3:
				return a.getProdPrice();
			case 4:
				return conn.getProductQuantity(a);
			case 5:
				return a.getProdThreshold();
			default:
				throw new UnsupportedOperationException("Not supported yet.");
		}
	}

    /**
     * @override
     * @param columnIndex
     * @return String
     */
    @Override
	public String getColumnName(int columnIndex)
	{
		return columnNames[columnIndex];
	}

    /**
     * <pre>
     *update.
     * 
     * update the product
     * </pre>
     */
        
    public void update()
	{
		products = conn.getProducts();
	}
}
