package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    
    String fileName = "a.db";

    private Connection connect(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\josee\\Desktop\\Projeto\\banco_de_dados" + fileName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public User(String fileName){
        this.fileName = fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                + "    username VARCHAR(30) NOT NULL,\n"
                + "    senha VARCHAR(30) NOT NULL,\n"
                + "    nome VARCHAR(60) NOT NULL,\n"
                + "    email VARCHAR(60) NOT NULL\n"
                + ");";
        
        try (Connection conn = this.connect(fileName);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insert(String username, String senha, String nome, String email) {
        String sql = "INSERT INTO User(username,senha,nome,email) VALUES(?,?,?,?)";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, senha);
            pstmt.setString(3, nome);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
}