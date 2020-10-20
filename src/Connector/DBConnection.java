package Connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class  DBConnection{

    public Connection getConnection(String dbname) throws ClassNotFoundException{
        Connection connection=null;
        try{
           Class.forName("com.mysql.jdbc.Driver");
           connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname,"root","UPKFA<72-(");
        }catch (SQLException e){
            e.printStackTrace();}
         return connection;
        }
    } 
 