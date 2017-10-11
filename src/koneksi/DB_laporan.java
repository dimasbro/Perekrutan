/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
/**
 *
 * @author dimas
 */
public class DB_laporan {
    public static String PathReport= System.getProperty("user.dir") + "/src/Laporan/";
    private static Connection koneksi;
    public static Connection getkoneksi(){
    if(koneksi == null){
        try{
            String url=  new String();
            String user= new String();
            String password= new String();
            url= "jdbc:mysql://localhost:3306/perekrutan_db";
            user= "root";
            password= "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi= DriverManager.getConnection(url, user, password);
        }catch(Exception e){
            System.out.println("koneksi error!!!");
        }
    }
    return koneksi;
}
}
