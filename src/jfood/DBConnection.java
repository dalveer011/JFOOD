/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Mazhar
 */
public class DBConnection {
    private static String userName = "n01131759";
    private static String password = "oracle";
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@dilbert.humber.ca:1521:grok";
    
    private Statement statement = null;
    private static Connection conn = null;
    private ResultSet rs = null;
    
    private PreparedStatement stmt = null;
    
    //Constructor
    public DBConnection (){
        this.getConnection();
    }
    
    //Methods:
     public static Connection returnConnection (){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);  
            JOptionPane.showMessageDialog(null, "Connection Established", "Connection Status", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Class Not Found Exception Thrown", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception Thrown", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
    public void getConnection (){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            
            //JOptionPane.showMessageDialog(null, "Connection Established", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Class Not Found Exception Thrown", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception Thrown", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void closeConnection (){
        try {
            if (statement != null){
                statement.close();
            }
            
            if (conn != null){
                conn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception Thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addSecurityQuestions (String loginId, String pswd, String role, String sq1, String ans1, String sq2, String ans2)
    {
        
        String addSecurityQuestions = "INSERT INTO SECURITY_QUESTIONS_JFOOD (LOGINID, PASSWORD, ROLE,  SECURITY_QUESTION1, ANSWER_1, SECURITY_QUESTION2, ANSWER_2) " + 
                " VALUES (?,?,?,?,?,?,?)";
        
        try {
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(addSecurityQuestions);
            
            stmt.setString(1, loginId);
            stmt.setString(2, pswd);
            stmt.setString(3, role);
            stmt.setString(4, sq1);
            stmt.setString(5, ans1);
            stmt.setString(6, sq2);
            stmt.setString(7, ans2);
            
            stmt.executeQuery();
            
            conn.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Security Questions Table", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addCreditCardInfo (String loginId, String currBal, String cardInfo, String expiry, String ccv)
    {
        
        String addCreditCard = "INSERT INTO CUSTOMER_WALLET_JFOOD (LOGINID, CURRENTBAL, CARDINFO, EXPIRY, CCV) " + 
                " VALUES (?,?,?,?,?)";
        
        try {
            conn.setAutoCommit(false);
            
            stmt = conn.prepareStatement(addCreditCard);
            
            stmt.setString(1, loginId);
            stmt.setString(2, currBal);
            stmt.setString(3, cardInfo);
            stmt.setString(4, expiry);
            stmt.setString(5, ccv);
            
            stmt.executeQuery();
            
            conn.commit();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Credit Card Table", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void addRestaurantInfo (String loginId, String pass, String role, String name, 
            String streetAdd, String city,
            String province, String postalCode, 
            String email, String phone)
    {
        String addRegistrationInfo = "INSERT INTO RESTAURANTOWNERS_JFOOD (LOGINID, PASSWORD, ROLE, NAME, ADDRESS, CITY, PROVINCE, POSTALCODE, EMAIL, PHONE) " + 
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
                
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(addRegistrationInfo);
            stmt.setString(1, loginId);
            stmt.setString(2, pass);
            stmt.setString(3, role);
            stmt.setString(4, name);
            stmt.setString(5, streetAdd);
            stmt.setString(6, city);
            stmt.setString(7, province);
            stmt.setString(8, postalCode);
            stmt.setString(9, email);
            stmt.setString(10, phone);
            
            stmt.executeQuery();
            conn.commit();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Registration Table", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void addCustomerInfo (String loginId, String pass, String role, String fName, 
            String lName, String streetAdd, String city,
            String province, String postalCode, 
            String email, String phone)
    {
        String addRegistrationInfo = "INSERT INTO CUSTOMERS_JFOOD (LOGINID, PASSWORD, ROLE, FNAME, LNAME, ADDRESS, CITY, PROVINCE, POSTALCODE, EMAIL, PHONE) " + 
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(addRegistrationInfo);
            stmt.setString(1, loginId);
            stmt.setString(2, pass);
            stmt.setString(3, role);
            stmt.setString(4, fName);
            stmt.setString(5, lName);
            stmt.setString(6, streetAdd);
            stmt.setString(7, city);
            stmt.setString(8, province);
            stmt.setString(9, postalCode);
            stmt.setString(10, email);
            stmt.setString(11, phone);
            
            stmt.executeQuery();
            conn.commit();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Registration Table", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    public void UpdateCustomerInfo (String loginId, String pass, String fName, 
            String lName, String streetAdd, String city,
            String province, String postalCode, 
            String email, String phone)
    {
        String updateQuery = "UPDATE CUSTOMERS_JFOOD SET PASSWORD = ?, FNAME = ?, LNAME = ?, ADDRESS = ?, "
                + "CITY = ?, PROVINCE = ?, POSTALCODE = ?, EMAIL = ?, PHONE = ? WHERE LOGINID = ?";
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, pass);
            stmt.setString(2, fName);
            stmt.setString(3, lName);
            stmt.setString(4, streetAdd);
            stmt.setString(5, city);
            stmt.setString(6, province);
            stmt.setString(7, postalCode);
            stmt.setString(8, email);
            stmt.setString(9, phone);
            stmt.setString(10, loginId);
            stmt.executeQuery();
            conn.commit();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void UpdateSecurityInfo (String loginId, String pass, String sq1, String ans1, 
            String sq2, String ans2)
    {
        String updateQuery = "UPDATE Security_Questions_Jfood1 SET PASSWORD = ?, SECURITY_QUESTION1 = ?, ANSWER_1 = ?, SECURITY_QUESTION2 = ?, "
                + "ANSWER_2 = ? WHERE LOGINID = ?";
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, pass);
            stmt.setString(2, sq1);
            stmt.setString(3, ans1);
            stmt.setString(4, sq2);
            stmt.setString(5, ans2);
            stmt.setString(6, loginId);
            stmt.executeQuery();
            conn.commit();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
            
   public ResultSet getInfo(String query){
        try {
            
             statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Exception thrown "  +ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
        
        return rs;
    }
   
   public void addNewItems(String itemnum,String restid ,String category , String itemDesc,String price) throws SQLException
    {
        String addNewItems = "insert into menu_items_jfood(itemnum,restaurantid,category,item_description,price)values(?,?,?,?,?)";
       
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(addNewItems);
            stmt.setString(1, itemnum);
            stmt.setString(2, restid);
            stmt.setString(3,category);
            stmt.setString(4,itemDesc);
            stmt.setString(5,price);
//            stmt.addBatch();
            stmt.executeQuery();
            conn.commit();
            
        
        
        
    }
    
    public void updateItems(String itemnum,String restid ,String category , String itemDesc,String price) throws SQLException
    {
     String updateItems = "update menu_items_jfood set price=? where itemnum = ?";  
     
     conn.setAutoCommit(false);
     stmt = conn.prepareStatement(updateItems);
     stmt.setString(1,price);
     stmt.setString(2,itemnum);
     stmt.addBatch();
     stmt.executeBatch();
     conn.commit();
     
     
    }
    
    public void deleteItems(String itemnum) throws SQLException
    {
     String deleteItems = "delete from menu_items_jfood where itemnum=?";
        
     conn.setAutoCommit(false);
     stmt = conn.prepareStatement(deleteItems);
     stmt.setString(1,itemnum);
     stmt.addBatch();
     stmt.executeBatch();
     conn.commit();
    }

}



