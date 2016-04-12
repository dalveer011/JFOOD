
package Restaurant;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import database.DBConnection;
import jfood.HeaderFooter;
import jfood.LogOut;

public class DeleteItems extends RestaurantMenuBar {
    
    private JLabel lblHeading,lblCopyRight,imgWest,lblRestId,lblItemNum,lblItemDesc,lblCategory;
    private JTextField txtRestId,txtItemNum,txtItemDesc;
    private JComboBox category;
    private JButton delete,btnviewInfo;
    private JPanel PanelCenter;
    private String id;
    private DBConnection db = null;
    private ResultSet rsDeleteItem = null;
    
    public DeleteItems(final String id)
    {
        this.id = id;
        this.initComponents();
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);

        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);

        this.setTitle("Delete  Food items");
       
        this.add(PanelCenter,BorderLayout.CENTER);
        this.setJMenuBar(restMenuBar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);        
       
         btnviewInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String restName = txtRestId.getText();
               ViewInformation v1 = new ViewInformation(restName);
                

            }
        });
        
        delete.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {  
             
                 String rid = id;
                 String inum = txtItemNum.getText();
                 String icat = category.getSelectedItem().toString();
                 if(inum.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Item num field can not be left blank", "Empty field",JOptionPane.WARNING_MESSAGE);
                 }
                 else {
                 
                 //String idesc = txtItemDesc.getText();
//                File c = new File("addItems.txt");
//                File addItem = new File("addItems.txt");
//                File temp = new File("temp.txt");
//                
//                DataInputStream dis = new DataInputStream(new FileInputStream(addItem));
//                DataOutputStream dos = new DataOutputStream(new FileOutputStream(temp,true));
//                int count = 0;  //If no such item exists
//                while(dis.available() > 0)
//                {
//                    String restid = dis.readUTF();
//                    String itemnum = dis.readUTF();
//                    String cat = dis.readUTF();     
//                if(restid.equals(rid)&inum.equals(itemnum)&cat.equals(icat))
//                {
//                     for(int j=1;j <= 2;j++)
//                    {
//                        dis.readUTF();
//                    }
//                     count++;
//                }else{
//                dos.writeUTF(restid);
//                dos.writeUTF(itemnum);
//                dos.writeUTF(cat);
//                 dos.writeUTF(dis.readUTF());
//                 dos.writeUTF(dis.readUTF());
//                }
//                }
//                if(count > 0) {
//                JOptionPane.showMessageDialog(null, "Deleted", "Choices",JOptionPane.INFORMATION_MESSAGE);
//                }else {
//                JOptionPane.showMessageDialog(null, "Item not found in your restaurant Menu", "Item not Found",JOptionPane.ERROR_MESSAGE);
//                }
//                dos.close();
//                dis.close();
//               if(addItem.delete())
//                         System.out.println("deleted");
//                if(temp.renameTo(c))
//                         System.out.println("Renamed");
//                 }
//                 }catch (FileNotFoundException ex) {
//                System.out.println("File not found");
//            }       catch (IOException ex) {
//                     System.out.println("error");        
//            }
//            txtItemNum.setText("");
              try {
                         db = new DBConnection();
                         String query = "select itemnum,restaurantid,category,item_description,price from menu_items_jfood";
                         boolean check = true;
                         rsDeleteItem=db.getInfo(query);
                         
                         
                            while(rsDeleteItem.next())
                            {
                             if(rsDeleteItem.getString(1).equals(inum))
                             {
                               rsDeleteItem.deleteRow();
                             //db.deleteItems(inum);
                              check = false;
                             JOptionPane.showMessageDialog(null,"Item Deleted","Success",JOptionPane.INFORMATION_MESSAGE);
                             
                             }
                             
                            }
                            
                             if(check)
                             {
                             JOptionPane.showMessageDialog(null,"No items to delete with provided item num","Attention",JOptionPane.WARNING_MESSAGE);
                             }
                            
                         
                         db.closeConnection();
                         
                         
                     } catch (SQLException ex) {
                         JOptionPane.showMessageDialog(null,"Failed to delete","Attention", JOptionPane.INFORMATION_MESSAGE);
                     }
               
               
               
                
              
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
                RestaurantHome a = new RestaurantHome(id);
                DeleteItems.this.dispose();
            }                
            }
        );
        
        addItem.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new AddNewItems(id);
                DeleteItems.this.dispose();
            }                
            }
        );
        
        deleteItem.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new DeleteItems(id);
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
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
                DeleteItems.this.dispose();
            }                
            }
        );
    }
    
    private void initComponents()
    {
    
        lblRestId = new JLabel("Resturant Id");
        lblItemNum = new JLabel("Item Num");
        lblCategory =  new JLabel("Category");
        txtRestId = new JTextField();
        txtRestId.setText(id);
        txtRestId.setEditable(false);
        txtItemNum = new JTextField();
        
        String [] catlist = {"Rice","Noodles","Chicken","Fish","Dessert","Entree","Soup","Salad"};
        category = new JComboBox(catlist);
        delete = new JButton("delete");
       btnviewInfo = new JButton("Get Item Details");
        PanelCenter = new JPanel(new GridLayout(4,2));
        PanelCenter.add(lblRestId);
        PanelCenter.add(txtRestId);
        PanelCenter.add(lblItemNum);
        PanelCenter.add(txtItemNum);
        PanelCenter.add(lblCategory);
        PanelCenter.add(category);
         PanelCenter.add(btnviewInfo);
        PanelCenter.add(delete);
    }
    
}
