package project;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bancoSQLite {
    
    public static void createNewDatabase(String fileName){

        String url = "jdbc:sqlite:C:/Users/josee/Desktop/Projeto/banco_de_dados/" + fileName;
        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println("Exceção");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException{
        
        createNewDatabase("_database.db");
        User user = new User("_database.db");
    }
}
