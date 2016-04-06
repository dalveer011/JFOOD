
package jfood;

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
//                    try {
//                   //-----------To check if item already exists--------//
//                        DataInputStream dis = new DataInputStream(new FileInputStream("addItems.txt"));
//                    boolean check = true;
//                    String enteredItemNum = txtItemNum.getText();
//                    while(dis.available() > 0) {
//                    String restId = dis.readUTF();
//                    String alreadyThere = dis.readUTF();
//                    if((restId.toUpperCase().equals(id.toUpperCase())) & (enteredItemNum.toUpperCase().equals(alreadyThere.toUpperCase()))  ){
//                     JOptionPane.showMessageDialog(null, "This item Id already Exits. Please provide another Id","Id already exists", JOptionPane.ERROR_MESSAGE);
//                    check = false;
//                    txtItemNum.setText("");
//                    }
//                    for(int i = 1;i <=3;i++) {
//                    dis.readUTF();
//                    }
//                } 
//                dis.close();
//                //---------Ends here------------//
//                if(check) {
//                inFile = new DataOutputStream(new FileOutputStream ("addItems.txt",true));
//                String restId = txtRestId.getText();
//                 String itemCategory = category.getSelectedItem().toString();
//               String price = txtPrice.getText();
//                String itemNum = txtItemNum.getText();
//                String itemDesc = txtItemDesc.getText();
//                if(restId.isEmpty() || itemDesc.isEmpty()||itemNum.isEmpty()||txtPrice.getText().isEmpty()) {
//                    JOptionPane.showMessageDialog(null,"Fields Cant be empty","Attention",JOptionPane.INFORMATION_MESSAGE);
//                }else
//                {
//                inFile.writeUTF(restId);
//                inFile.writeUTF(itemNum);
//                 inFile.writeUTF(itemCategory);
//                inFile.writeUTF(itemDesc);
//                inFile.writeUTF(price);
//               inFile.close();
//                }
//                 int a =JOptionPane.showConfirmDialog(null, "Item Added.Do you Want to Add Another Item ?", "Choices",JOptionPane.YES_NO_OPTION);
//                 if(a == 0) 
//                 {  
//                  txtItemNum.setText("");
//                  txtItemDesc.setText("");
//                  txtPrice.setText("");
//                 }else {
//                  AddNewItems.this.dispose();
//                  new RestaurantHome(id);
//                 }
//                
//            }
//                    }        
//            catch (FileNotFoundException ex) {
//                System.out.println("File not found");
//            }
//            catch (Exception ex) {
//                System.out.println("Error in Add items file.No fields can be empty");
//               // JOptionPane.showMessageDialog(null,ex.getMessage(),"name",JOptionPane.ERROR_MESSAGE);
//            }

                String restid = txtRestId.getText();
                String itemnum = txtItemNum.getText();
                String itemDesc = txtItemDesc.getText();
                String price = txtPrice.getText();
                String cat = category.getSelectedItem().toString();
                
                if(restid.trim().isEmpty()||itemnum.trim().isEmpty()||itemDesc.trim().isEmpty()||cat.trim().isEmpty()||price.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Fields cannot be empty","Attention",JOptionPane.WARNING_MESSAGE);
                }
                else
                
                {
                   db = new DBConnection();
                    try {
                        db.addNewItems(itemnum, restid, cat, itemDesc, price);
                        JOptionPane.showMessageDialog(null, "Item Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showConfirmDialog(null,"An item already exists with the given id","Attention",JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(null,"Item couldnot be added");
                        
                    }
                   
                   
                   
                   
                   
                       
                    db.closeConnection();
                    System.out.println("Connection closed");
                    new RestaurantHome(id);
                    AddNewItems.this.dispose();
                    
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
