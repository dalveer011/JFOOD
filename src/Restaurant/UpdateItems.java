/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

import Restaurant.RestaurantMenuBar;
import Restaurant.RestaurantHome;
import Restaurant.DeleteItems;
import Restaurant.AddNewItems;
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
import database.DBConnection;
import java.net.Socket;
import jfood.HeaderFooter;
import jfood.LogOut;

public class UpdateItems extends RestaurantMenuBar{
    
    private JLabel lblRestId,lblItemNum,lblAvail,lblPriceOld,lblPriceNew,lblHeading,lblCopyRight,imgWest,lblItemDesc,lblCategory;
    private JTextField txtRestId,txtItemNum,txtPriceOld,txtPriceNew,txtItemDesc;
    private JComboBox avail,category;
    private JPanel PanelNorth,PanelSouth,PanelCenter;
    private JButton btnConfirm,btnViewInfo;
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
                String itemnum = txtItemNum.getText().toUpperCase();
                String itemDesc = txtItemDesc.getText();
                String price = txtPriceNew.getText();
                String cat = category.getSelectedItem().toString();
                
                if(restid.trim().isEmpty()||itemnum.trim().isEmpty()||itemDesc.trim().isEmpty()||cat.trim().isEmpty()||price.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Fields cannot be empty","Attention",JOptionPane.WARNING_MESSAGE);
                }
                 else if(!price.matches("^[0-9]+(\\.[0-9]{1,2})?$"))
                {
                    JOptionPane.showMessageDialog(null,"Wrong format for price. has to be xxxxx.xx","Attention",JOptionPane.WARNING_MESSAGE);
                    txtPriceNew.setText("");
                }
                else
                {
                double price1 = Double.parseDouble(price);
                   try {
                        Socket socketUpdateitems = new Socket("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketUpdateitems.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream (socketUpdateitems.getOutputStream());
                        
                        dataToServer.writeInt(9);
                        dataToServer.writeUTF(itemnum);
                        dataToServer.writeUTF(restid);
                        dataToServer.writeUTF(cat);
                        dataToServer.writeUTF(itemDesc);
                        dataToServer.writeDouble(price1);
                       
                        boolean check = dataFromServer.readBoolean();
                        if(check)
                        {
                            JOptionPane.showMessageDialog(null, "Item Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                           int option =  JOptionPane.showConfirmDialog(null,"Do you want to update more items","Attention",JOptionPane.YES_NO_OPTION);
                            if(option==0)
                            {
                                new UpdateItems(id);
                                UpdateItems.this.dispose();
                                
                            }
                            else
                            {
                                new RestaurantHome(id);
                                UpdateItems.this.dispose();
                            }
                            
                        }else
                           {
                                JOptionPane.showMessageDialog(null, "No item with the provided item num", "Attention", JOptionPane.WARNING_MESSAGE);
                                JOptionPane.showMessageDialog(null,"Item couldnot be updated");
                           }
                        socketUpdateitems.close();
               
                }catch (IOException ex) {
                   System.out.println(ex.getMessage());
                }
            }
        }
            
        });
        btnViewInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  String restName = txtRestId.getText();
               ViewInformation v1 = new ViewInformation(restName);
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
       
         
    }
    
    
    private void initComponents()
    {
      
        lblRestId = new JLabel("RestaurantId");
        lblItemNum = new JLabel("Item Number");
       
        lblPriceOld = new JLabel("Present Price");
        lblPriceNew = new JLabel("New Price");
        lblItemDesc = new JLabel("Item Description");
        lblCategory = new JLabel("Item Category");
        btnConfirm = new JButton("Confirm");
        btnViewInfo = new JButton("ViewInfo");
        
        
        
        String [] catlist = {"Rice","Noodles","Chicken","Fish","Dessert","Entree","Soup","Salad"};
        category = new JComboBox(catlist);
        
        txtRestId = new JTextField();
        txtRestId.setEditable(false);
        txtRestId.setText(id);
        txtItemNum = new JTextField();
        txtPriceOld = new JTextField();
        txtPriceNew = new JTextField();
        txtItemDesc = new JTextField();
    
        
        PanelCenter = new JPanel(new GridLayout(6,2));
        PanelCenter.add(lblRestId);
        PanelCenter.add(txtRestId);
        PanelCenter.add(lblItemNum);
        PanelCenter.add(txtItemNum);
        PanelCenter.add(lblItemDesc);
        PanelCenter.add(txtItemDesc);
        PanelCenter.add(lblCategory);
        PanelCenter.add(category);
        
        
        PanelCenter.add(lblPriceNew);
        PanelCenter.add(txtPriceNew);
        PanelCenter.add(btnConfirm);
        PanelCenter.add(btnViewInfo);

    }

}
