/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.ArrayList;

/**
 * <pre>
 *
 * The Cart class
 * encapsulates all functionality
 * related to the Cart attributes.
 *
 * Factory Method Design Pattern
 * with data validity check.
 *
 * </pre>
 *
 * @version 1.1
 * @since change the field purchasesList and customer to final
 * @author Miccio, reviewed and documented by Yin.
 * @see elements.Customer
 * @see elements.Purchase
 * @todo add to report
 */
public class Cart
{

	private ArrayList<Purchase> purchasesList;
	private Customer customer;

	/**
	 *
	 * @param purchasesList ArrayList<Purchase>
	 * @param customer Customer
	 * @return
	 */
	public static Cart create(Customer customer, ArrayList<Purchase> purchasesList)
	{
		if (customer == null || purchasesList == null) {
			return null;
		} else {
			return new Cart(customer, purchasesList);
		}
	}

	/**
	 * <pre>
	 * Constructor.
	 *
	 * Factory internal Constructor.
	 * </pre>
	 *
	 * @param customer Customer
	 * @param purchasesList ArrayList<Purchase>
	 */
	public Cart(Customer customer, ArrayList<Purchase> purchasesList)
	{
		this.purchasesList = purchasesList;
		this.customer = customer;
	}

	/**
	 * <pre>
	 * Factory internal Constructor.
	 * </pre>
	 *
	 * @param customer Customer
	 */
	public Cart(Customer customer)
	{
		this.purchasesList = new ArrayList();
		this.customer = customer;
	}

	/**
	 * <pre>
	 * Factory internal Constructor.
	 * </pre>
	 *
	 */
	public Cart()
	{
		this.purchasesList = null;
		this.customer = null;
	}
	
	public synchronized void erasePurchasesList() {
		purchasesList = new ArrayList();
	}
	
	public synchronized void eraseCustomer() {
		customer = null;
	}


	/**
	 * <pre>
	 * get purchasesList.
	 *
	 * get the purchase ArrayList
	 * </pre>
	 *
	 * @return ArrayList<Purchase>
	 */
	public synchronized ArrayList<Purchase> getPurchasesList()
	{
		return purchasesList;
	}

	/**
	 * <pre>
	 * return customer.
	 *
	 * </pre>
	 *
	 * @return customer
	 */
	public synchronized Customer getCustomer()
	{
		return customer;
	}

	/**
	 * <pre>
	 * add purchase.
	 *
	 * add the purchase
	 *
	 * <pre>
	 *
	 * @param purchase Purchase
	 * @return boolean
	 */
	public synchronized boolean addPurchase(Purchase purchase)
	{
		return purchasesList.add(purchase);
	}

	/**
	 * <pre>
	 * rem purchase.
	 *
	 * delete the purchase
	 *
	 * <pre>
	 *
	 * @param purchase Purchase
	 * @return boolean
	 */
	public synchronized boolean remPurchase(Purchase purchase)
	{
		return purchasesList.remove(purchase);
	}

	/**
	 * <pre>
	 * rem purchase.
	 *
	 * delete the purchase
	 *
	 * data validity check
	 * -purchase.getProd().getProdID equals to prodID
	 * <pre>
	 *
	 * @param prodID integer
	 * @return boolean
	 */
	public synchronized boolean remPurchase(int prodID)
	{
		for (Purchase purchase : purchasesList) {
			if (purchase.getProd().getProdID() == prodID) {
				return purchasesList.remove(purchase);
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * edit purchase.
	 *
	 * modify the purchase
	 *
	 * data validity check
	 * -purchaseList contains old purchase
	 * -get the index of old purchase
	 * -set the new purchase to the purchaseList
	 * <pre>
	 *
	 * @param oldPurchase Purchase
	 * @param newPurchase Purchase
	 * @return boolean
	 */
	public synchronized boolean editPurchase(Purchase oldPurchase, Purchase newPurchase)
	{
		if (purchasesList.contains(oldPurchase)) {
			int index = purchasesList.indexOf(oldPurchase);
			purchasesList.set(index, newPurchase);
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * get purchase.
	 *
	 * get the purchase
	 *
	 * data validity check
	 * -purchase.getProd equals to product
	 * <pre>
	 *
	 * @param product Product
	 * @return purchase
	 */
	public synchronized Purchase getPurchase(Product product)
	{
		for (Purchase purchase : purchasesList) {
			if (purchase.getProd().equals(product)) {
				return purchase;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * get purchase.
	 *
	 * get the purchase
	 *
	 * data validity check
	 * -purchase.getProd().getProdID() equals to prodID
	 * <pre>
	 *
	 * @param prodID integer
	 * @return purchase
	 */
	public synchronized Purchase getPurchase(int prodID)
	{
		for (Purchase purchase : purchasesList) {
			if (purchase.getProd().getProdID() == prodID) {
				return purchase;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * contains purchase.
	 *
	 * contain the purchase
	 *
	 * data validity check
	 * -purchase.getProd() equals to product
	 * <pre>
	 *
	 * @param product Product
	 * @return boolean
	 */
	public synchronized boolean containsProduct(Product product)
	{
		for (Purchase purchase : purchasesList) {
			if (purchase.getProd().equals(product)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 *get Total Cost.
	 *
	 *get the total cost of the total of the shopping by the customer
	 * <pre>
	 *
	 * @return double
	 */
	public synchronized double getTotalCost()
	{
		double price = 0;
		for (Purchase purchase : purchasesList) {
			price += purchase.getPurcCost() * purchase.getPurcQty();
		}
		return price;
	}

	/**
	 * <pre>
	 *equals.
	 *override the equals method
	 * </pre>
	 *
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		Cart cart = (Cart) obj;
		System.out.println(getCustomer().getUserName());
		System.out.println(cart.getCustomer().getUserName());

		if (!getCustomer().equals(cart.getCustomer())) {
			return false;
		}

		//might need another implementation
		if (getPurchasesList().containsAll(cart.getPurchasesList())) {
			if (cart.getPurchasesList().containsAll(getPurchasesList())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * toString.
	 * </pre>
	 *
	 * @return String "Customer:\t" + userName + "\n" + "Products:\n" + prodName
	 * + "\t" + purcQty + "\t" + purcCost + "kr\n"+ "Total:\t" + TotalCost +
	 * "kr"
	 *
	 * @override
	 */
	@Override
	public String toString()
	{
		String str = "";
		str += "Customer:\t" + customer.getUserName() + "\n";
		str += "Products:\n";
		for (Purchase purchase : purchasesList) {
			str += purchase.getProd().getProdName() + "\t" + purchase.getPurcQty() + "\t" + purchase.getPurcCost() + "kr\n";
		}
		str += "Total:\t" + getTotalCost() + "kr";

		return str;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}