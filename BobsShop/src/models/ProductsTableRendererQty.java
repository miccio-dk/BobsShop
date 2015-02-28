/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.*;

/**
 *
 * @author Miccio
 */
public class ProductsTableRendererQty extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        
		int qtyLeft = (Integer)table.getValueAt(row, 4);
        int qtyMin = (Integer)table.getValueAt(row, 5);
		
		
        if (qtyLeft<qtyMin)
		{
			setOpaque(true);
			if(isSelected)
				setBackground(Color.RED.brighter());
			else
				setBackground(Color.RED);
        }
		else
		{
            setOpaque(false);
        }
        return this;
    }
}
