package banking;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {

    static Connection con;

    public static Connection getConnection(){
        try {

            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/bank";
            String user = "root";
            String password="123";

            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, password);

        }catch (Exception e){
            System.out.println("La connexion a échouée");
        }

        return con;
    }



}
