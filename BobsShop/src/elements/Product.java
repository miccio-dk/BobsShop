/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.Objects;

/**
 * <pre>
 * The Product class
 * encapsulates all functionality
 * related to the Product attributes.
 *
 * Factory Method Design Pattern
 * with data validity check.
 *
 * For example:
 * <code>
 * Product product = Product.create(int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold);
 * </code>
 * </pre>
 *
 * @version 1.2
 * @author Miccio,reviewed and Documented by Rudolf
 * @see ProductNGTest
 * @todo add to report
 */
public class Product {
    private final int prodID;
    private String prodName;
    private double prodCost;
    private double prodPrice;
    private int prodThreshold;
    
    /**
     * <pre>
     * create.
     * 
     * Factory method to Create an object of type Product
     * </pre>
     *
     * @param prodID final integer
     * @param prodName String
     * @param prodCost double
     * @param prodPrice double
     * @param prodThreshold integer
     * @return Product | null
     */
    public static Product create(int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold) {
        if (prodID >= 0 && !prodName.trim().isEmpty() && prodPrice > prodCost && prodCost >= 0 && prodThreshold >= 0) {
            return new Product(prodID, prodName, prodCost, prodPrice, prodThreshold);
        } else {
            return null;
        }
    }

   /**
     * <pre>
     * Constructor.
     * 
     * Factory internal Constructor.
     * </pre>
     * @param prodID final integer
     * @param prodName String
     * @param prodCost double
     * @param prodPrice double
     * @param prodThreshold integer
     */
    private Product(int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodCost = prodCost;
        this.prodPrice = prodPrice;
        this.prodThreshold = prodThreshold;
    }

    /**
     * <pre>
     * get prodID.
     * 
     * return the product ID
     * </pre>
     * @return prodID integer
     */
    public int getProdID() {
        return prodID;
    }

    /**
     * <pre>
     * get prodName.
     * get the product name
     * </pre>
     * @return prodName
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * <pre>
     * get prodCost.
     *
     * get product Cost the cost at which BOB would purchase the product
     * @return prodCost
     */
    public double getProdCost() {
        return prodCost;
    }

    /**
     * <pre>
     * get prodPrice.
     * 
     * get the product Price the price at which the customer purchases the Producct
     * </pre>
     * @return prodPrice
     */
    public double getProdPrice() {
        return prodPrice;
    }

    /**
     * <pre>
     * get prodThreshold.
     * 
     * get the product threshold (quantity at which Bob should consider ordering more)
     * </pre>
     * @return prodThreshold
     */
    public int getProdThreshold() {
        return prodThreshold;
    }

    /**
     * <pre>
     * set prodName.
     *
     * data validity 
     * -check trimmed String prodName is not empty
     * </pre>
     * @param prodName String
     * @return boolean
     */
    public boolean setProdName(String prodName) {
        if (!prodName.trim().isEmpty()) {
            this.prodName = prodName;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * set prodCost.
     * 
     * sets the product Cost
     * 
     * data validity check
     * -prodCost greater or equal to 0
     * -prodCost less than prodPrice
     * </pre>
     * @param prodCost double
     * @return boolean
     */
    public boolean setProdCost(double prodCost) {
        if (prodCost >= 0 && prodPrice > prodCost) {
            this.prodCost = prodCost;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * set prodPrice.
     * 
     * sets the product Price
     * 
     * data validity check
     * -prodPrice greater than prodCost
     * <pre>
     *
     * @param prodPrice double
     * @return boolean
     */
    public boolean setProdPrice(double prodPrice) {
        if (prodPrice > prodCost) {
            this.prodPrice = prodPrice;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * set prodThreshold.
     * 
     * sets the product threshold
     * 
     * data validity check
     * -prodThreshold greater or equal to 0
     * <pre>
     * @param prodThreshold integer
     * @return boolean
     */
    public boolean setProdThreshold(int prodThreshold) {
        if (prodThreshold >= 0) {
            this.prodThreshold = prodThreshold;
            return true;
        }
        return false;
    }

    /**
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

		Product product = (Product)obj;
		return (getProdID()==product.getProdID() && 
				getProdName().equals(product.getProdName()) && 
				getProdCost()==product.getProdCost() &&
				getProdPrice()==product.getProdPrice() &&
				getProdThreshold()==product.getProdThreshold());
	}

   
}
