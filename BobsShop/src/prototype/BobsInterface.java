package prototype;

import forms.*;

/**
 * <pre>
 * The BobInterface class.
 *
 * For example:
 * <code>
 * JFrame frame = new JFrame("Bob's Awesome Hardware Store");
 * frame.add(new BobsInterface(new ProductsInterface(frame, conn), new CustomersInterface(frame, conn)));
 * </code>
 * </pre>
 *
 * @author Yin,reviewed and documented by Yin.
 * @see javax.swing.JPanel
 * @see forms
 * @see prototype
 */
public class BobsInterface extends javax.swing.JPanel {

    ProductsInterface productInterface =  null;
    CustomersInterface customerInterface = null;
    
    /**
     * Creates new form BobInterface
     * @param productInterface ProductInterface
     * @param customerInterface CustomerInterface
     */
    
    public BobsInterface( ProductsInterface productInterface, CustomersInterface customerInterface) {
        initComponents();
        this.customerInterface = customerInterface;
        this.productInterface =  productInterface;
        
     	// add forms.CustomersInterface to custPanel
        
        custPanel.add(customerInterface);
        custPanel.revalidate();
        
        // add forms.ProductsInterface to prodPanel
	
        prodPanel.add(productInterface);
        prodPanel.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        TabbedPane = new javax.swing.JTabbedPane();
        custPanel = new javax.swing.JPanel();
        prodPanel = new javax.swing.JPanel();
        jTBCustomer = new javax.swing.JToolBar();
        tbAddCustomer1 = new javax.swing.JButton();
        tbTopup1 = new javax.swing.JButton();
        tbAssignCustomer1 = new javax.swing.JButton();
        tbAssignCustomer1.setVisible(false);
        jTBProduct = new javax.swing.JToolBar();
        tbAddProduct = new javax.swing.JButton();
        tbRestock = new javax.swing.JButton();
        tbAssignProduct = new javax.swing.JButton();
        tbAssignProduct.setVisible(false);
        logOff = new javax.swing.JButton();
        jFmenubar = new javax.swing.JInternalFrame();
        menubar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        menuNewCusotmer = new javax.swing.JMenuItem();
        menuNewProduct = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        menuTopup = new javax.swing.JMenuItem();
        menuAssignP = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuRestock = new javax.swing.JMenuItem();
        menuAssignC = new javax.swing.JMenuItem();
        window = new javax.swing.JMenu();
        menuCustomer = new javax.swing.JMenuItem();
        menuProduct = new javax.swing.JMenuItem();
        background = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setMaximumSize(new java.awt.Dimension(716, 599));
        setMinimumSize(new java.awt.Dimension(716, 599));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(716, 599));

        jPanel1.setMaximumSize(new java.awt.Dimension(716, 599));
        jPanel1.setMinimumSize(new java.awt.Dimension(716, 599));
        jPanel1.setName(""); // NOI18N
        jPanel1.setLayout(null);

        mainPanel.setMaximumSize(new java.awt.Dimension(720, 440));
        mainPanel.setLayout(null);

        TabbedPane.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TabbedPane.addTab("Customer", custPanel);
        TabbedPane.addTab("Product", prodPanel);

        mainPanel.add(TabbedPane);
        TabbedPane.setBounds(0, 26, 716, 430);

        jTBCustomer.setRollover(true);

        tbAddCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/add_user.png"))); // NOI18N
        tbAddCustomer1.setBorderPainted(false);
        tbAddCustomer1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbAddCustomer1.setFocusable(false);
        tbAddCustomer1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbAddCustomer1.setMaximumSize(new java.awt.Dimension(40, 40));
        tbAddCustomer1.setMinimumSize(new java.awt.Dimension(40, 40));
        tbAddCustomer1.setPreferredSize(new java.awt.Dimension(40, 40));
        tbAddCustomer1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbAddCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAddCustomer1ActionPerformed(evt);
            }
        });
        jTBCustomer.add(tbAddCustomer1);

        tbTopup1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/Top-up-750x750.jpg"))); // NOI18N
        tbTopup1.setBorderPainted(false);
        tbTopup1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbTopup1.setFocusable(false);
        tbTopup1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbTopup1.setMaximumSize(new java.awt.Dimension(40, 40));
        tbTopup1.setMinimumSize(new java.awt.Dimension(40, 40));
        tbTopup1.setPreferredSize(new java.awt.Dimension(40, 40));
        tbTopup1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbTopup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbTopup1ActionPerformed(evt);
            }
        });
        jTBCustomer.add(tbTopup1);

        tbAssignCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/rfid.gif"))); // NOI18N
        tbAssignCustomer1.setBorderPainted(false);
        tbAssignCustomer1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbAssignCustomer1.setFocusable(false);
        tbAssignCustomer1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbAssignCustomer1.setMaximumSize(new java.awt.Dimension(40, 40));
        tbAssignCustomer1.setMinimumSize(new java.awt.Dimension(40, 40));
        tbAssignCustomer1.setPreferredSize(new java.awt.Dimension(40, 40));
        tbAssignCustomer1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbAssignCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAssignCustomer1ActionPerformed(evt);
            }
        });
        jTBCustomer.add(tbAssignCustomer1);

        mainPanel.add(jTBCustomer);
        jTBCustomer.setBounds(0, -1, 159, 30);

        jTBProduct.setRollover(true);

        tbAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/addp.PNG"))); // NOI18N
        tbAddProduct.setBorderPainted(false);
        tbAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbAddProduct.setFocusable(false);
        tbAddProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbAddProduct.setMaximumSize(new java.awt.Dimension(40, 40));
        tbAddProduct.setMinimumSize(new java.awt.Dimension(40, 40));
        tbAddProduct.setPreferredSize(new java.awt.Dimension(40, 40));
        tbAddProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAddProductActionPerformed(evt);
            }
        });
        jTBProduct.add(tbAddProduct);

        tbRestock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/restock.PNG"))); // NOI18N
        tbRestock.setBorderPainted(false);
        tbRestock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbRestock.setFocusable(false);
        tbRestock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbRestock.setMaximumSize(new java.awt.Dimension(40, 40));
        tbRestock.setMinimumSize(new java.awt.Dimension(40, 40));
        tbRestock.setPreferredSize(new java.awt.Dimension(40, 40));
        tbRestock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbRestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbRestockActionPerformed(evt);
            }
        });
        jTBProduct.add(tbRestock);

        tbAssignProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/rfid.gif"))); // NOI18N
        tbAssignProduct.setBorderPainted(false);
        tbAssignProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbAssignProduct.setFocusable(false);
        tbAssignProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbAssignProduct.setMaximumSize(new java.awt.Dimension(40, 40));
        tbAssignProduct.setMinimumSize(new java.awt.Dimension(40, 40));
        tbAssignProduct.setPreferredSize(new java.awt.Dimension(40, 40));
        tbAssignProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbAssignProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAssignProductActionPerformed(evt);
            }
        });
        jTBProduct.add(tbAssignProduct);

        mainPanel.add(jTBProduct);
        jTBProduct.setBounds(165, -1, 200, 30);

        jPanel1.add(mainPanel);
        mainPanel.setBounds(0, 20, 720, 440);

        logOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/logoff.gif"))); // NOI18N
        logOff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOffActionPerformed(evt);
            }
        });
        jPanel1.add(logOff);
        logOff.setBounds(670, 553, 44, 46);

        jFmenubar.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jFmenubar.setVisible(true);

        file.setText("File");

        menuNewCusotmer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNewCusotmer.setText("New Customer");
        menuNewCusotmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewCusotmerActionPerformed(evt);
            }
        });
        file.add(menuNewCusotmer);

        menuNewProduct.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        menuNewProduct.setText("New Product");
        menuNewProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewProductActionPerformed(evt);
            }
        });
        file.add(menuNewProduct);
        file.add(jSeparator3);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        file.add(menuExit);

        menubar.add(file);

        edit.setText("Edit");

        menuTopup.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        menuTopup.setText("Top-up");
        menuTopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTopupActionPerformed(evt);
            }
        });
        edit.add(menuTopup);

        menuAssignP.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menuAssignP.setText("Assign Customer");
        menuAssignP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAssignPActionPerformed(evt);
            }
        });
        edit.add(menuAssignP);
        edit.add(jSeparator1);

        menuRestock.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuRestock.setText("Restock");
        menuRestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRestockActionPerformed(evt);
            }
        });
        edit.add(menuRestock);

        menuAssignC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        menuAssignC.setText("Assign Product");
        menuAssignC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAssignCActionPerformed(evt);
            }
        });
        edit.add(menuAssignC);

        menubar.add(edit);

        window.setText("Window");

        menuCustomer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuCustomer.setText("Customers Overview");
        menuCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCustomerActionPerformed(evt);
            }
        });
        window.add(menuCustomer);

        menuProduct.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuProduct.setText("Products Overview");
        menuProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProductActionPerformed(evt);
            }
        });
        window.add(menuProduct);

        menubar.add(window);

        jFmenubar.setJMenuBar(menubar);

        jPanel1.add(jFmenubar);
        jFmenubar.setBounds(-9, -28, 732, 52);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prototype/backg.jpg"))); // NOI18N
        background.setToolTipText("");
        jPanel1.add(background);
        background.setBounds(0, 0, 715, 620);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbAddCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAddCustomer1ActionPerformed
       customerInterface.addCustomer();
    }//GEN-LAST:event_tbAddCustomer1ActionPerformed

    private void tbTopup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbTopup1ActionPerformed
        customerInterface.addCredit();
    }//GEN-LAST:event_tbTopup1ActionPerformed

    private void tbAssignCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAssignCustomer1ActionPerformed
    customerInterface.assignCustomer();
    }//GEN-LAST:event_tbAssignCustomer1ActionPerformed

    private void tbAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAddProductActionPerformed
        productInterface.addProduct();
    }//GEN-LAST:event_tbAddProductActionPerformed

    private void tbRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbRestockActionPerformed
        productInterface.restockProduct();
    }//GEN-LAST:event_tbRestockActionPerformed

    private void tbAssignProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAssignProductActionPerformed
        productInterface.assignProduct();
    }//GEN-LAST:event_tbAssignProductActionPerformed

    private void logOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOffActionPerformed
        System.exit(0);
    }//GEN-LAST:event_logOffActionPerformed

    private void menuNewCusotmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewCusotmerActionPerformed
        customerInterface.addCustomer();
    }//GEN-LAST:event_menuNewCusotmerActionPerformed

    private void menuNewProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewProductActionPerformed

        productInterface.addProduct();
    }//GEN-LAST:event_menuNewProductActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        logOffActionPerformed(evt);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuTopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTopupActionPerformed
        customerInterface.addCredit();
    }//GEN-LAST:event_menuTopupActionPerformed

    private void menuAssignPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAssignPActionPerformed
        productInterface.assignProduct();
    }//GEN-LAST:event_menuAssignPActionPerformed

    private void menuRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRestockActionPerformed
        productInterface.restockProduct();
    }//GEN-LAST:event_menuRestockActionPerformed

    private void menuAssignCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAssignCActionPerformed
        customerInterface.assignCustomer();
    }//GEN-LAST:event_menuAssignCActionPerformed

    private void menuCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCustomerActionPerformed
        TabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_menuCustomerActionPerformed

    private void menuProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProductActionPerformed
        TabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_menuProductActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JLabel background;
    private javax.swing.JPanel custPanel;
    private javax.swing.JMenu edit;
    private javax.swing.JMenu file;
    private javax.swing.JInternalFrame jFmenubar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar jTBCustomer;
    private javax.swing.JToolBar jTBProduct;
    private javax.swing.JButton logOff;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuItem menuAssignC;
    private javax.swing.JMenuItem menuAssignP;
    private javax.swing.JMenuItem menuCustomer;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuNewCusotmer;
    private javax.swing.JMenuItem menuNewProduct;
    private javax.swing.JMenuItem menuProduct;
    private javax.swing.JMenuItem menuRestock;
    private javax.swing.JMenuItem menuTopup;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JPanel prodPanel;
    private javax.swing.JButton tbAddCustomer1;
    private javax.swing.JButton tbAddProduct;
    private javax.swing.JButton tbAssignCustomer1;
    private javax.swing.JButton tbAssignProduct;
    private javax.swing.JButton tbRestock;
    private javax.swing.JButton tbTopup1;
    private javax.swing.JMenu window;
    // End of variables declaration//GEN-END:variables
}
