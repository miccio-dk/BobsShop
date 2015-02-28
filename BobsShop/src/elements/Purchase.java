 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 * The Purchase class
 * encapsulates all functionality
 * related to the Purchase and attributes.
 *
 * Factory Method Design Pattern
 * with data validity check.
 *
 * For example:
 * <code>
 * Purchase purchase = Product.create(Product prod, double amount, int purcQty, FlowDirection type);
 * </code>
 * </pre>
 *
 * @version 1.2
 * @author Miccio, reviewed and Documented by Rudolf
 * @see elements
 * @todo FlowDirection may need to migrate upwards in the Coherance factor but
 * in the "next iteration"
 */
public class Purchase
{

	private Product prod;
	private double purcCost;
	private int purcQty;
	private FlowDirection flow;

	/**
	 * <pre>
	 * enumerated FlowDirection to describe the flow of transactions.
	 *
	 * SELL: bob sells.
	 * RESTOCK: bob re-stocks.
	 * TOPUP: bob adds funds to the customer.
	 * </pre>
	 */
	public enum FlowDirection
	{

		SELL,
		RESTOCK,
		TOPUP
	}

	/**
	 * <pre>
	 * Factory method to Create an object of type Purchase.
	 * '
	 *
	 * @overloaded' 4 variables specify the purcCost(unitPrice)
	 * </pre>
	 *
	 * @param prod Product
	 * @param purcCost double
	 * @param purcQty integer
	 * @param flow
	 * @return Purchase | null
	 */
	public static Purchase create(Product prod, double purcCost, int purcQty, FlowDirection flow)
	{
		if (prod != null && purcQty > 0) {
			/*if (purcCost < prod.getProdCost()) {
			 purcCost = prod.getProdCost();
			 }*/
			switch (flow) {
				case RESTOCK:
					return new Purchase(prod, purcCost, purcQty, flow);
				case SELL:
					return new Purchase(prod, purcCost, purcQty, flow);
				case TOPUP:
					return new Purchase(prod, purcCost, purcQty, flow);
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * Factory method to Create an object of type Purchase.
	 * '
	 *
	 * @overloaded' 3 variables use the products native unitPrice
	 * </pre>
	 *
	 * @param prod Product
	 * @param purcQty integer
	 * @param flow
	 * @return Purchase | null
	 */
	public static Purchase create(Product prod, int purcQty, FlowDirection flow)
	{
		if (prod != null) {
			switch (flow) {
				case RESTOCK:
					return new Purchase(prod, prod.getProdCost(), purcQty, flow);
				case SELL:
					return new Purchase(prod, prod.getProdPrice(), purcQty, flow);
				case TOPUP:
					//return new Purchase(prod, prod.getProdPrice(), purcQty, flow);
					return null;
			}
		}
		return null;
	}
	
	
	public static Purchase create(Purchase purc)
	{
		return create(purc.getProd(), purc.getPurcCost(), purc.getPurcQty(), purc.getFlowDirection());
	}
	
// private Purchase(double purcCost, int purcQty) {
//        this.purcCost = purcCost;
//        this.purcQty = purcQty;
//    }
// 
//     public static Purchase create( double purcCost, int purcQty){
//         if (purcQty > 0 && purcCost >= 0) {
//            return new Purchase(purcCost, purcQty);
//        } else {
//            return null;
//        }
//     
//     }

	/**
	 * <pre>
	 * Factory internal Constructor.
	 * </pre>
	 *
	 * @param prod Product
	 * @param purcQty integer
	 * @param type FlowDirecetion
	 */
	private Purchase(Product prod, double purcCost, int purcQty, FlowDirection flow)
	{
		this.prod = prod;
		this.purcCost = purcCost;
		this.purcQty = purcQty;
		this.flow = flow;
	}

	/**
	 * <pre>
	 * return the Product.
	 * </pre>
	 *
	 * @return prod Product
	 */
	public Product getProd()
	{
		return prod;
	}

	/**
	 * <pre>
	 * return the purchase cost.
	 * </pre>
	 *
	 * @return purcCost double
	 */
	public double getPurcCost()
	{
		return purcCost;
	}

	/**
	 * <pre>
	 * return the Flow Direction.
	 * </pre>
	 *
	 *
	 * @return flow FlowDirection
	 */
	public FlowDirection getFlowDirection()
	{
		return flow;
	}

	/**
	 * <pre>
	 * return the purchase Quantity.
	 * </pre>
	 *
	 * @return purcQty integer
	 */
	public int getPurcQty()
	{
		return purcQty;
	}

	/**
	 * <pre>
	 * set the Product.
	 * </pre>
	 *
	 * @param prod the prod to set
	 * @return boolean
	 */
	public boolean setProd(Product prod)
	{
		if (prod != null) {
			this.prod = prod;
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * set the purchase Cost.
	 * </pre>
	 *
	 * @param purcCost double
	 * @return boolean
	 */
	public boolean setPurcCost(double purcCost)
	{
		boolean $return = false;
		if (purcCost < prod.getProdCost()) {
			purcCost = prod.getProdCost();
			$return = true;
		}
		this.purcCost = purcCost;
		return $return;
	}

	/**
	 * <pre>
	 * resets the purchase cost to the native Product's purchase Cost.
	 * </pre>
	 */
	public void resetPurcCost()
	{
		this.purcCost = prod.getProdCost();
	}

	/**
	 * <pre>
	 * sets the purchase Quantity.
	 * </pre>
	 *
	 * @param purcQty integer
	 * @return boolean
	 */
	public boolean setPurcQty(int purcQty)
	{
		if (purcQty > 0) {
			this.purcQty = purcQty;
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

		Purchase purchase = (Purchase)obj;
		return (getProd().equals(purchase.getProd()) && 
				getPurcQty()==purchase.purcQty && 
				getPurcCost()==purchase.getPurcCost());
	}
}
