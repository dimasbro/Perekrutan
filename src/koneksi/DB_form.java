/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package koneksi;
import java.sql.*;
/**
 *
 * @author dimas
 */
public class DB_form {
    public Connection cc;
    public Statement ss;
    public ResultSet rs;

    public void Class(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        cc= DriverManager.getConnection("jdbc:mysql://localhost/perekrutan_db","root","");
        System.out.println("koneksi ok!!!");
    }catch(Exception e){
        System.out.println(e);
    }
    }
}
