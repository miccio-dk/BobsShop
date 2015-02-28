package data;

import elements.*;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * <pre>
 * The DataConnection abstract class
 * encapsulates all functionality
 * related to the DataConnection attributes.
 *
 * Factory Method Design Pattern
 * with data validity check.
 *
 * </pre>
 *
 * @version 1.1
 * @author Miccio,reviewed and Documented by Yin
 * @see data
 * @see elements
 * @todo add to report
 */

public abstract class DataConnection
{
	
    /**
     * <pre>
     * get Customers.
     * get the arraylist Customers
     * </pre>
     * @return ArrayList<Customer>
     */
    	
	public abstract ArrayList<Customer> getCustomers();

    /**
     * <pre>
     * get Products.
     * get the arraylist Products
     * </pre>
     * @return ArrayList<Product>
     */
        
    public abstract ArrayList<Product> getProducts();
    
    /**
     * <pre>
     * get Purchase.
     * get the arraylist Purchase
     * </pre>
     * @return ArrayList<Purchase>
     */
    
     public abstract ArrayList<Purchase> getPurchase();

    /**
     *<pre>
     * get Balance.
     * get the Balance
     * </pre>
     * @param customer Customer 
     * @return double
     */
     
    public abstract double getBalance(Customer customer);

    /**
     * <pre>
     * get ProductQuantity.
     * get the product quantity
     * </pre>
     * @param product Product
     * @return integer
     */
    
    public abstract int getProductQuantity(Product product);

    /**
     * <pre>
     * get ProductTrend.
     * get the product trend
     * </pre>
     * @param product Product
     * @return ArrayList<Integer>
     */
    
    public abstract ArrayList<Integer> getProductTrend(Product product);

    /**
     *<pre>
     * add Customer.
     * add the customer
     * </pre>
     * @param userPin String
     * @param userName String
     * @param userAddress String
     * @param userActive
     * @return Customer
     */
    public abstract Customer addCustomer(String userPin, String userName, String userAddress, boolean userActive);

    /**
     * <pre>
     * delete Customer.
     * delete the customer
     * </pre>
     * @param customer Customer
     * @return boolean
     */
    
    public abstract boolean delCustomer(Customer customer);

    /**
     * <pre>
     * modify Customer.
     * modify the customer
     * </pre>
     * @param customer Customer
     * @return boolean
     */
    
    public abstract boolean editCustomer(Customer customer);
	
	public abstract boolean editCustomerRFID(Customer customer, String rfid);

    /**
     * <pre>
     * add Product.
     * add the Product
     * </pre>
     * @param prodName String
     * @param prodCost double
     * @param prodPrice double
     * @param prodThreshold integer
     * @return Product
     */
    
    public abstract Product addProduct(String prodName, double prodCost, double prodPrice, int prodThreshold);

    /**
     * <pre>
     * delete Product.
     * delete the product
     * </pre>
     * @param product Product
     * @return boolean
     */
    
    public abstract boolean delProduct(Product product);

    /**
     * <pre>
     * edit Product.
     * edit the product
     * </pre>
     * @param product Product
     * @return boolean
     */
    
    public abstract boolean editProduct(Product product);
	
	public abstract boolean editProductRFID(Product product, String rfid);

    /**
     * <pre>
     * add Receipt.
     * add the receipt
     * </pre>
     * @param customer Customer
     * @param purchaseArrayList ArrayList<Purchase>
     * @param recentDate Date
     * @return boolean
     */
    public abstract boolean addReceipt(Customer customer, ArrayList<Purchase> purchaseArrayList, Date recentDate);
	
	
	public abstract Customer getCustomer(String userRFID);
	public abstract Customer getCustomer(int userID);
	public abstract Product getProduct(String prodRFID);
	public abstract Product getProduct(int prodID);
	
	// etc etc
}
