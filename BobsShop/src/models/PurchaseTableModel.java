//package models;
//
//import data.DataConnection;
//import elements.*;
//import elements.Purchase.*;
//import java.awt.Component;
//import java.util.ArrayList;
//import javax.swing.table.*;
//
///**
// *
// * @author Yin
// */
//public class PurchaseTableModel extends AbstractTableModel
//{
//	// column headers
//	private final String[] columnNames={ "PRODID", "RECEID", "U.Cost", "Purc.Qty"};
//	
//	// data connection
//	private final DataConnection conn;
//
//	// products
//	private ArrayList<Purchase> purchase = null;
//
//
//    /**
//     *
//     * @param conn
//     */
//    public PurchaseTableModel(DataConnection conn)
//	{
//		this.conn=conn;
//		purchase=conn.getPurchase();
//	}
//
//    /**
//     *
//     * @param index
//     * @return
//     */
//    public Purchase getPurchase(int index)
//	{
//		return purchase.get(index);
//	}
//	
//	@Override
//	public int getRowCount()
//	{
//		return purchase.size();
//	}
//
//	@Override
//	public int getColumnCount()
//	{
//		return columnNames.length;
//	}
//	
//	@Override
//	public Class getColumnClass(int columnIndex)
//	{
//		switch(columnIndex)
//		{
//			
//                    	case 0:
//				return Integer.class;
//			case 1:
//				return Integer.class;
//                        case 2:
//				return Double.class;
//			case 3:
//				return Integer.class;
//			
//			default:
//				throw new UnsupportedOperationException("Not supported yet.");
//		}
//	}
//	
//	@Override
//	public boolean isCellEditable(int rowIndex, int columnIndex)
//	{
//		return false;
//		//return (columnIndex==0 || columnIndex==3);
//	}
//			
//	
//	
//	@Override
//	public Object getValueAt(int rowIndex, int columnIndex)
//	{
//		Purchase p=purchase.get(rowIndex);
//		switch(columnIndex)
//		{
//			
//			case 0:
//				return p.getPurcCost();
//			case 1:
//				return p.getPurcQty();
//                        case 2:
//				return p.getPurcCost();
//			case 3:
//				return p.getPurcQty();
//			
//			default:
//				throw new UnsupportedOperationException("Not supported yet.");
//		}
//	}
//	
//	@Override
//	public String getColumnName(int columnIndex)
//	{
//		return columnNames[columnIndex];
//	}
//
//    /**
//     *
//     */
//    public void update()
//	{
//		purchase = conn.getPurchase();
//	}
//
//    
//}
