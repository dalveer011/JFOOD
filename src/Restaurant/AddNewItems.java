
package Restaurant;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnection;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jfood.HeaderFooter;
import jfood.LogOut;

public class AddNewItems extends RestaurantMenuBar{
    
    private JLabel lblrestId,lblItemNum,lblItemDesc,lblCategory,lblCost,lblHeading,lblCopyRight,imgWest;
    private JTextField txtRestId,txtItemNum,txtItemDesc,txtPrice;
    private JButton confirm,reset;
    private JComboBox category;
    private JPanel PanelCenter;
    private DataOutputStream inFile;
    private DataInputStream outFile;
    String id;
    private DBConnection db = null;
    private ResultSet rsAddNewItem = null;
    
    public AddNewItems(final String id)
    {
        this.id = id;
        this.initComponents();
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);

        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);

        this.setTitle("Add new Food items");
       
        this.add(PanelCenter,BorderLayout.CENTER);
        this.setJMenuBar(restMenuBar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String restid = txtRestId.getText();
                String itemnum = txtItemNum.getText();
                String itemDesc = txtItemDesc.getText();
                String price = txtPrice.getText();
                String cat = category.getSelectedItem().toString();
                
                //double price1 = Double.parseDouble(price);
                if(restid.trim().isEmpty()||itemnum.trim().isEmpty()||itemDesc.trim().isEmpty()||cat.trim().isEmpty()||price.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Fields cannot be empty","Attention",JOptionPane.WARNING_MESSAGE);
                }
                else
                
                {
                    
                   db = new DBConnection();
                    try {
                        Socket socketAdditems = new Socket("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketAdditems.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream (socketAdditems.getOutputStream());
                        
                        dataToServer.writeInt(8);
                        dataToServer.writeUTF(itemnum);
                        dataToServer.writeUTF(restid);
                        dataToServer.writeUTF(cat);
                        dataToServer.writeUTF(itemDesc);
                        dataToServer.writeUTF(price);
                        
                        boolean checkAdded = dataFromServer.readBoolean();
                        
                        if (checkAdded){
                            JOptionPane.showMessageDialog(null, "Item Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                            new RestaurantHome(id);
                            AddNewItems.this.dispose();
                        }else{
                            JOptionPane.showConfirmDialog(null,"An item already exists with the given id","Attention",JOptionPane.WARNING_MESSAGE);
                            JOptionPane.showMessageDialog(null,"Item couldnot be added");
                        }
                        socketAdditems.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }          
            }
        }
            
     });
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==reset)
                {
                    
                    JOptionPane.showMessageDialog(null,"Reset","Reset",JOptionPane.INFORMATION_MESSAGE);
                    AddNewItems.this.dispose();
                    new AddNewItems(id);
                    
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
                 AddNewItems.this.dispose();
                new RestaurantHome(id);
               
            }                
            }
        );
        
        addItem.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                 AddNewItems.this.dispose();
                new AddNewItems(id);
               
            }                
            }
        );
        
        deleteItem.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                    AddNewItems.this.dispose();
                new DeleteItems(id);
            
            }                
            }
        );
        
        updateItem.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new UpdateItems(id);
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
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
                AddNewItems.this.dispose();
            }                
            }
        );
        
    }
    
    private void initComponents()
    {
        lblrestId = new JLabel("RestaurantID");
        lblItemNum = new JLabel("Item Num");
        lblCategory = new JLabel("Category");
        lblItemDesc = new JLabel("Desciption");
        lblCost = new JLabel("Cost");
        
        txtRestId = new JTextField();
        txtRestId.setEditable(false);
        txtRestId.setText(id);
        
        
        
        txtItemNum = new JTextField();
        txtItemDesc = new JTextField();
        txtPrice = new JTextField();
        
        confirm = new JButton("Add Item");
        reset = new JButton("Reset");
        
        
        String [] catlist = {"Rice","Noodles","Chicken","Fish","Dessert","Entree","Soup","Salad", "Breakfast", "Dinner", "Mains", "Entree"};
        category = new JComboBox(catlist);
        
        PanelCenter = new JPanel(new GridLayout(6,2));
        PanelCenter.add(lblrestId);
        PanelCenter.add(txtRestId);
        PanelCenter.add(lblItemNum);
        PanelCenter.add(txtItemNum);
        PanelCenter.add(lblCategory);
        PanelCenter.add(category);
        PanelCenter.add(lblItemDesc);
        PanelCenter.add(txtItemDesc);
        PanelCenter.add(lblCost);
        PanelCenter.add(txtPrice);
        PanelCenter.add(confirm);
        PanelCenter.add(reset); 
    }
}
