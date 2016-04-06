/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateItems extends RestaurantMenuBar{
    
    private JLabel lblRestId,lblItemNum,lblAvail,lblPriceOld,lblPriceNew,lblHeading,lblCopyRight,imgWest,lblItemDesc,lblCategory;
    private JTextField txtRestId,txtItemNum,txtPriceOld,txtPriceNew,txtItemDesc;
    private JComboBox avail,category;
    private JPanel PanelNorth,PanelSouth,PanelCenter;
    private JButton btnConfirm,btnDone;
    private DataInputStream outFile;
    private DataOutputStream inFile;
    private String id;
    private DBConnection db = null;
    
    private ResultSet rsUpdateItem = null;
    
    public UpdateItems(final String id)
    { 
        this.id = id;
        this.initComponents();
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);

        this.setTitle("Update Food items");
        this.add(PanelCenter,BorderLayout.CENTER);
        this.setJMenuBar(restMenuBar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
               String restid = txtRestId.getText();
                String itemnum = txtItemNum.getText();
                String itemDesc = txtItemDesc.getText();
                String price = txtPriceNew.getText();
                String cat = category.getSelectedItem().toString();
                
                if(restid.trim().isEmpty()||itemnum.trim().isEmpty()||itemDesc.trim().isEmpty()||cat.trim().isEmpty()||price.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Fields cannot be empty","Attention",JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                db = new DBConnection();
                   try {
                       
                       String query = "select itemnum,restaurantid,category,item_description,price from menu_items_jfood";
                      
                       rsUpdateItem= db.getInfo(query);
                       
                       
                       boolean check=true;
                       
                       while(rsUpdateItem.next())
                       {
                           
                           if(rsUpdateItem.getString(1).equals(itemnum))
                           {
                                db.updateItems(itemnum, restid, cat, itemDesc, price);
                                check=false;
                                JOptionPane.showMessageDialog(null, "Item Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                           }
                       } 
                           if(check)
                           {
                               JOptionPane.showMessageDialog(null, "No item with the provided item num", "Attention", JOptionPane.WARNING_MESSAGE);
                           }
                       
                      
                       
                      
                   }
                   catch (SQLException ex) {
                       JOptionPane.showConfirmDialog(null,"Problem in updating item","Attention",JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(null,"Item couldnot be updated");
                   }
                   catch(Exception exe)
                   {
                       System.out.println("this is throwing an exception");  
                   }
                   
                   
                       
                    db.closeConnection();
                    System.out.println("Connection closed");
                    new RestaurantHome(id);
                    UpdateItems.this.dispose();
               
                }
             
                
            }
            
        });
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==btnDone)
                {
                    
                    JOptionPane.showMessageDialog(null,"Done","Submit",JOptionPane.INFORMATION_MESSAGE);
                    new RestaurantHome(id);
                    UpdateItems.this.dispose();
                }
            }
        });   
        
        //Adding action listener to the menubar!
        gotoHome.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new RestaurantHome (id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        addItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new AddNewItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        deleteItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new DeleteItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        updateItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new UpdateItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        logOut.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new LogOut();
                UpdateItems.this.dispose();
            }                
            }
        );
        
        //Adding Tool Bar
        this.add(this.getRestaurantToolBar(),BorderLayout.EAST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        tbBtnAdd.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddNewItems a = new AddNewItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );

        tbBtnDelete.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                DeleteItems a = new DeleteItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        tbBtnUpdate.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                UpdateItems a = new UpdateItems(id);
                UpdateItems.this.dispose();
            }                
            }
        );
        
        tbBtnLogOut.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut a = new LogOut();
                UpdateItems.this.dispose();
            }                
            }
        );
        
        tbBtnResHome.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                RestaurantHome a = new RestaurantHome(id);
                UpdateItems.this.dispose();
            }                 
            
            }
        );
        avail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(avail.getSelectedItem().equals("Yes"))
        {
            txtPriceOld.setVisible(true);
            txtPriceNew.setVisible(true);
        }
         else if(avail.getSelectedItem().equals("No"))
        {
            txtPriceOld.setVisible(false);
            txtPriceNew.setVisible(false);
            
        }
                
            }
        });
         
    }
    
    
    private void initComponents()
    {
      
        lblRestId = new JLabel("RestaurantId");
        lblItemNum = new JLabel("Item Number");
        lblAvail = new JLabel("Availability");
        lblPriceOld = new JLabel("Present Price");
        lblPriceNew = new JLabel("New Price");
        lblItemDesc = new JLabel("Item Description");
        lblCategory = new JLabel("Item Category");
        btnConfirm = new JButton("Confirm");
        btnDone = new JButton("Done");
        
        String[] availlist = {"Yes","No"};
        avail = new JComboBox(availlist);
        
        String [] catlist = {"Rice","Noodles","Chicken","Fish","Dessert","Entree","Soup","Salad"};
        category = new JComboBox(catlist);
        
        txtRestId = new JTextField();
        txtRestId.setEditable(false);
        txtRestId.setText(id);
        txtItemNum = new JTextField();
        txtPriceOld = new JTextField();
        txtPriceNew = new JTextField();
        txtItemDesc = new JTextField();
    
        
        PanelCenter = new JPanel(new GridLayout(8,2));
        PanelCenter.add(lblRestId);
        PanelCenter.add(txtRestId);
        PanelCenter.add(lblItemNum);
        PanelCenter.add(txtItemNum);
        PanelCenter.add(lblItemDesc);
        PanelCenter.add(txtItemDesc);
        PanelCenter.add(lblCategory);
        PanelCenter.add(category);
        PanelCenter.add(lblAvail);
        PanelCenter.add(avail);
        PanelCenter.add(lblPriceOld);
        PanelCenter.add(txtPriceOld);
        PanelCenter.add(lblPriceNew);
        PanelCenter.add(txtPriceNew);
        PanelCenter.add(btnConfirm);
        PanelCenter.add(btnDone);

    }

}
