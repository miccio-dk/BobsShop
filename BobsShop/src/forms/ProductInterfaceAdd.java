package forms;

import data.*;
import javax.swing.JDialog;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Riccardo
 * @see data
 * 
 */
public class ProductInterfaceAdd extends javax.swing.JPanel
{
	private JDialog dialog = null;
	private DataConnection conn = null;
	
	
	/**
	 * Creates new form AddStudentInterface
     * @param dialog JDialog
     * @param conn DataConnection
	 */
	public ProductInterfaceAdd(JDialog dialog, DataConnection conn)
	{
		initComponents();
		this.dialog = dialog;
		this.conn = conn;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        prodname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        prodcost = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jToggleButton = new javax.swing.JToggleButton();
        prodthreshold = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        prodprice = new javax.swing.JTextField();

        jLabel2.setText("Product name");

        prodname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodnameActionPerformed(evt);
            }
        });

        jLabel3.setText("Unit. cost");

        prodcost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodcostActionPerformed(evt);
            }
        });

        jLabel4.setText("Threshold");

        jToggleButton.setText("Add new product");
        jToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonActionPerformed(evt);
            }
        });

        prodthreshold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodthresholdActionPerformed(evt);
            }
        });

        jLabel5.setText("Unit. price");

        prodprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodpriceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prodname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(prodcost))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(prodprice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(prodthreshold, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prodname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prodcost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prodthreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prodprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jToggleButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jToggleButtonActionPerformed
    {//GEN-HEADEREND:event_jToggleButtonActionPerformed
		dialog.dispose();
		dialog.setVisible(false);
    }//GEN-LAST:event_jToggleButtonActionPerformed

    private void prodnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodnameActionPerformed

    private void prodcostActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_prodcostActionPerformed
    {//GEN-HEADEREND:event_prodcostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodcostActionPerformed

    private void prodthresholdActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_prodthresholdActionPerformed
    {//GEN-HEADEREND:event_prodthresholdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodthresholdActionPerformed

    private void prodpriceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_prodpriceActionPerformed
    {//GEN-HEADEREND:event_prodpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prodpriceActionPerformed

    /**
     *
     * @return String
     */
    public String getProdname()
	{
		return prodname.getText();
	}

    /**
     *
     * @return double
     */
    public double getProdcost()
	{
		return Double.parseDouble(prodcost.getText());
	}

    /**
     *
     * @return double
     */
    public double getProdprice()
	{
		return Double.parseDouble(prodprice.getText());
	}

    /**
     *
     * @return integer
     */
    public int getProdthreshold()
	{
		return Integer.parseInt(prodthreshold.getText());
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JToggleButton jToggleButton;
    private javax.swing.JTextField prodcost;
    private javax.swing.JTextField prodname;
    private javax.swing.JTextField prodprice;
    private javax.swing.JTextField prodthreshold;
    // End of variables declaration//GEN-END:variables
}