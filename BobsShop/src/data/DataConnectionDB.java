/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import elements.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Miccio,reviewed and documented by Rudolf.
 * @see elements
 */
public class DataConnectionDB extends DataConnection {

    /**
     *
     * @param url String
     * @param username String
     * @param password String
     * @return DataConnectionDB
     */
    public static DataConnectionDB create(String url, String username, String password) {
        DataConnectionDB conn = new DataConnectionDB(url, username, password);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Impossible to connect to " + url + ".\nCheck configuration.", "Bob's Shop: Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private final String url;
    private final String username;
    private final String password;
    private static Connection connection;

    private DataConnectionDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return ArrayList<Customer>
     */
    @Override
    public ArrayList<Customer> getCustomers() {
        try {
            ArrayList<Customer> customerArrayList = new ArrayList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customerArrayList.add(Customer.create(resultSet.getInt("USERID"), resultSet.getString("USERPIN"), resultSet.getString("USERNAME"), resultSet.getString("USERADDRESS"), resultSet.getBoolean("USERACTIVE")));
            }
            return customerArrayList;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @return ArrayList<Product>
     */
    @Override
    public ArrayList<Product> getProducts() {
        try {
            ArrayList<Product> productArrayList = new ArrayList();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCT;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productArrayList.add(Product.create(resultSet.getInt("PRODID"), resultSet.getString("PRODNAME"), resultSet.getDouble("PRODCOST"), resultSet.getDouble("PRODPRICE"), resultSet.getInt("PRODTHRESHOLD")));
            }
            return productArrayList;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
     @Override
    public ArrayList<Purchase> getPurchase() {
//        try {
//            ArrayList<Purchase> purchaseArrayList = new ArrayList();
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PURCHASE;");
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                purchaseArrayList.add(Purchase.create(resultSet.getDouble("PURCCOST"), resultSet.getInt("PURCQTY")));
//            }
//
//            return purchaseArrayList;
//        } catch (SQLException ex) {
//            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
//        }
    }
    /**
     *
     * @param customer Customer
     * @return double
     */
    @Override
    public double getBalance(Customer customer) {
        try {
            String queryStr = "SELECT sum(PURCCOST*PURCQTY) AS balance FROM PURCHASE, RECEIPT WHERE PURCHASE.RECEID=RECEIPT.RECEID AND RECEIPT.USERID=?;";
            PreparedStatement query = connection.prepareStatement(queryStr);
            query.setInt(1, customer.getUserID());
            ResultSet resultSet = query.executeQuery();
            resultSet.last();
            return resultSet.getInt("balance");
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     *
     * @param product Product
     * @return integer
     */
    @Override
    public int getProductQuantity(Product product) {
        try {
            String queryStr = "SELECT sum(PURCQTY) AS quantity FROM PURCHASE WHERE PRODID=?;";
            PreparedStatement query = connection.prepareStatement(queryStr);
            query.setDouble(1, product.getProdID());
            ResultSet resultSet = query.executeQuery();
            resultSet.last();
            return resultSet.getInt("quantity");
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     *
     * @param userPin String
     * @param userName String
     * @param userAddress String
     * @param userActive
     * @return Customer
     */
    @Override
    public Customer addCustomer(String userPin, String userName, String userAddress, boolean userActive) {
        if (Customer.create(1, userPin, userName, userAddress, userActive) != null) {
            try {
                PreparedStatement queryInsert = connection.prepareStatement("INSERT INTO CUSTOMER (USERNAME, USERRFID, USERADDRESS,USERACTIVE, USERPIN) VALUES (?,?,?,?,?);");
                queryInsert.setString(1, userName);
                queryInsert.setNull(2, Types.VARCHAR);
                queryInsert.setString(3, userAddress);
                queryInsert.setBoolean(4, userActive);
                queryInsert.setString(5, userPin);
                queryInsert.executeUpdate();

                PreparedStatement querySelect = connection.prepareStatement("SELECT * FROM CUSTOMER;");
                ResultSet resultSet = querySelect.executeQuery();
                resultSet.last();
                return Customer.create(resultSet.getInt("USERID"), resultSet.getString("USERPIN"), resultSet.getString("USERNAME"), resultSet.getString("USERADDRESS"), resultSet.getBoolean("USERACTIVE"));
            } catch (SQLException ex) {
                Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     *
     * @param customer Customer
     * @return boolean
     */
    @Override
    public boolean delCustomer(Customer customer) {
        int id = customer.getUserID();

        try {
            PreparedStatement queryDelete = connection.prepareStatement("DELETE FROM CUSTOMER WHERE USERID = ?;");
            queryDelete.setInt(1, id);
            queryDelete.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Impossible to delete selected customer.\nCheck configuration.", "Bob's Shop: Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param prodName String
     * @param prodCost double
     * @param prodPrice double
     * @param prodThreshold integer
     * @return Product
     */
    @Override
    public Product addProduct(String prodName, double prodCost, double prodPrice, int prodThreshold) {
        try {
            PreparedStatement queryInsert = connection.prepareStatement("INSERT INTO PRODUCT (PRODNAME, PRODRFID, PRODCOST, PRODPRICE, PRODTHRESHOLD) VALUES (?,?,?,?,?);");
            queryInsert.setString(1, prodName);
            queryInsert.setNull(2, Types.VARCHAR);
            queryInsert.setDouble(3, prodCost);
            queryInsert.setDouble(4, prodPrice);
            queryInsert.setInt(5, prodThreshold);
            queryInsert.executeUpdate();

            PreparedStatement querySelect = connection.prepareStatement("SELECT * FROM PRODUCT;");
            ResultSet resultSet = querySelect.executeQuery();
            resultSet.last();
            return Product.create(resultSet.getInt("PRODID"), resultSet.getString("PRODNAME"), resultSet.getDouble("PRODCOST"), resultSet.getDouble("PRODPRICE"), resultSet.getInt("PRODTHRESHOLD"));
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param product Product
     * @return boolean
     */
    @Override
    public boolean delProduct(Product product) {
        double id = product.getProdID();

        try {
            PreparedStatement queryDelete = connection.prepareStatement("DELETE FROM PRODUCT WHERE PRODID = ?;");
            queryDelete.setDouble(1, id);
            queryDelete.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param customer Customer
     * @param purchaseArrayList ArrayList<Purchase>
     * @param receDate Date
     * @return boolean
     */
    @Override
    public boolean addReceipt(Customer customer, ArrayList<Purchase> purchaseArrayList, java.util.Date receDate) {
        try {
            PreparedStatement queryInsertReceipt = connection.prepareStatement("INSERT INTO RECEIPT (USERID, RECEDATE) VALUES (?,?);");
            queryInsertReceipt.setInt(1, customer.getUserID());
            queryInsertReceipt.setTimestamp(2, new Timestamp(receDate.getTime()));
            queryInsertReceipt.executeUpdate();

            PreparedStatement querySelectReceipt = connection.prepareStatement("SELECT max(RECEID) AS lastID FROM RECEIPT;");
            ResultSet resultSet = querySelectReceipt.executeQuery();
            resultSet.last();
            int receID = resultSet.getInt("lastID");

            PreparedStatement queryInsertPurchase = connection.prepareStatement("INSERT INTO PURCHASE (PRODID, RECEID, PURCCOST, PURCQTY) VALUES (?,?,?,?);");
            for (Purchase item : purchaseArrayList) {
                queryInsertPurchase.setDouble(1, item.getProd().getProdID());
                queryInsertPurchase.setInt(2, receID);
				queryInsertPurchase.setDouble(3, item.getPurcCost());
				if(item.getFlowDirection() == Purchase.FlowDirection.SELL) {
					queryInsertPurchase.setInt(4, -item.getPurcQty());
				}
				else {
					queryInsertPurchase.setInt(4, item.getPurcQty());
				}
                queryInsertPurchase.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param product Product
     * @return ArrayList<Integer>
     */
    @Override
    public ArrayList<Integer> getProductTrend(Product product) {
        Calendar cal = Calendar.getInstance();
        Timestamp min = new Timestamp(new java.util.Date().getTime());

        //get the first date
        try {
            String queryStr = "SELECT min(RECEDATE) as mindate FROM RECEIPT";
            PreparedStatement query = connection.prepareStatement(queryStr);
            ResultSet resultSet = query.executeQuery();
            resultSet.last();
            min = resultSet.getTimestamp("mindate");
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        cal.setTime(min);
        ArrayList<Integer> quantities = new ArrayList();

        while (cal.getTime().before(new java.util.Date())) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            min.setTime(cal.getTime().getTime());
			//System.out.println(min);

            try {
                String queryStr = "SELECT sum(PURCQTY) AS quantity FROM PURCHASE, RECEIPT WHERE RECEIPT.RECEID=PURCHASE.RECEID AND PRODID=? AND RECEDATE<?";
                PreparedStatement query = connection.prepareStatement(queryStr);
                query.setDouble(1, product.getProdID());
                query.setTimestamp(2, min);
                ResultSet r = query.executeQuery();
                r.last();
                int currQty = r.getInt("quantity");
                quantities.add(currQty);
            } catch (SQLException ex) {
                Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return quantities;
    }

    /**
     *
     * @param product Product
     * @return boolean
     */
    @Override
    public boolean editProduct(Product product) {
        try {
            PreparedStatement queryInsert = connection.prepareStatement("UPDATE PRODUCT SET PRODNAME=?, PRODCOST=?, PRODPRICE=?, PRODTHRESHOLD=? WHERE PRODID=?;");
            queryInsert.setString(1, product.getProdName());
            queryInsert.setDouble(2, product.getProdCost());
            queryInsert.setDouble(3, product.getProdPrice());
            queryInsert.setDouble(4, product.getProdThreshold());
            queryInsert.setDouble(5, product.getProdID());
            queryInsert.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param customer Customer
     * @return boolean
     */
    @Override
    public boolean editCustomer(Customer customer) {
        try {
            PreparedStatement queryInsert = connection.prepareStatement("UPDATE CUSTOMER SET USERNAME=?, USERADDRESS=?, USERACTIVE=?, USERPIN=? WHERE USERID=?;");
            queryInsert.setString(1, customer.getUserName());
            queryInsert.setString(2, customer.getUserAddress());
            queryInsert.setBoolean(3, customer.isActive());
            queryInsert.setString(4, customer.getUserPin());
            queryInsert.setInt(5, customer.getUserID());
            queryInsert.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

	@Override
	public Customer getCustomer(String userRFID)
	{
		try {
			Customer cust;
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE USERRFID=?;");
			statement.setString(1, userRFID);
			ResultSet resultSet = statement.executeQuery();
			
			if(!resultSet.first()) {
				return null;
			}
			else {
				resultSet.last();
				cust = Customer.create(resultSet.getInt("USERID"), resultSet.getString("USERPIN"), resultSet.getString("USERNAME"), resultSet.getString("USERADDRESS"), resultSet.getBoolean("USERACTIVE"));
				return cust;
			}
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
	}

	@Override
	public Product getProduct(String prodRFID)
	{
		try {
			Product prod;
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRODRFID=?;");
			statement.setString(1, prodRFID);
			ResultSet resultSet = statement.executeQuery();
			
			if(!resultSet.first()) {
				return null;
			}
			else {
				resultSet.last();
				prod = Product.create(resultSet.getInt("PRODID"), resultSet.getString("PRODNAME"), resultSet.getDouble("PRODCOST"), resultSet.getDouble("PRODPRICE"), resultSet.getInt("PRODTHRESHOLD"));
				return prod;
			}
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
	}

	@Override
	public Customer getCustomer(int userID)
	{
		try {
			Customer cust;
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE USERID=?;");
			statement.setInt(1, userID);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.last();
			cust = Customer.create(resultSet.getInt("USERID"), resultSet.getString("USERPIN"), resultSet.getString("USERNAME"), resultSet.getString("USERADDRESS"));
			return cust;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
	}

	@Override
	public Product getProduct(int prodID)
	{
		try {
			Product prod;
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRODID=?;");
			statement.setInt(1, prodID);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.last();
			prod = Product.create(resultSet.getInt("PRODID"), resultSet.getString("PRODNAME"), resultSet.getDouble("PRODCOST"), resultSet.getDouble("PRODPRICE"), resultSet.getInt("PRODTHRESHOLD"));
			return prod;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
	}

	@Override
	public boolean editCustomerRFID(Customer customer, String rfid)
	{
		try {
            PreparedStatement queryInsert = connection.prepareStatement("UPDATE CUSTOMER SET USERRFID=? WHERE USERID=?;");
            queryInsert.setString(1, rfid);
            queryInsert.setInt(2, customer.getUserID());
            queryInsert.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
	}

	@Override
	public boolean editProductRFID(Product product, String rfid)
	{
		try {
            PreparedStatement queryInsert = connection.prepareStatement("UPDATE PRODUCT SET PRODRFID=? WHERE PRODID=?;");
            queryInsert.setString(1, rfid);
            queryInsert.setInt(2, product.getProdID());
            queryInsert.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
	}

   
}
