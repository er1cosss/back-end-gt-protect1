package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                + "	username VARCHAR(30) NOT NULL,\n"
                + "	senha VARCHAR(30) NOT NULL,\n"
                + "	nome VARCHAR(60) NOT NULL,\n"
                + "	email VARCHAR(60) NOT NULL\n"
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


    public void update(int id, String username, String senha, String nome, String email) {
        String sql = "UPDATE User SET username = ? , senha = ? , nome = ? , email = ? WHERE id = ?";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, senha);
            pstmt.setString(3, nome);
            pstmt.setString(4, email);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void read(){
        String sql = "SELECT * FROM User";


        try (Connection conn = this.connect(fileName);
            Statement stmt  = conn.createStatement(); 
            ResultSet rs  = stmt.executeQuery(sql)){ 
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("username") +  "\t" + 
                                   rs.getString("senha") +  "\t" + 
                                   rs.getString("nome") +  "\t" + 
                                   rs.getString("email"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        
    }


    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
