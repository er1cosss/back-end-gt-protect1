
package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ListTraining {

    private String fileName;

    public ListTraining(String fileName) {
        this.fileName = fileName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS TrainingList (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n"
                + "    exercise VARCHAR(50) NOT NULL,\n"
                + "    sets INTEGER NOT NULL,\n"
                + "    repetitions INTEGER NOT NULL,\n"
                + "    duration INTEGER,\n"
                + "    notes TEXT\n"
                + ");";

        try (Connection conn = this.connect(fileName);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\josee\\Desktop\\Projeto\\banco_de_dados\\" + fileName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
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

    public void insert(String exercise, int sets, int repetitions, Integer duration, String notes) {
        String sql = "INSERT INTO TrainingList(exercise, sets, repetitions, duration, notes) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exercise);
            pstmt.setInt(2, sets);
            pstmt.setInt(3, repetitions);
            pstmt.setInt(4, duration);
            pstmt.setString(5, notes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, String exercise, int sets, int repetitions, Integer duration, String notes) {
        String sql = "UPDATE TrainingList SET exercise = ?, sets = ?, repetitions = ?, duration = ?, notes = ? WHERE id = ?";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exercise);
            pstmt.setInt(2, sets);
            pstmt.setInt(3, repetitions);
            pstmt.setInt(4, duration);
            pstmt.setString(5, notes);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read() {
        String sql = "SELECT * FROM TrainingList";

        try (Connection conn = this.connect(fileName);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("exercise") + "\t" +
                        rs.getInt("sets") + "\t" +
                        rs.getInt("repetitions") + "\t" +
                        rs.getInt("duration") + "\t" +
                        rs.getString("notes"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM TrainingList WHERE id = ?";

        try (Connection conn = this.connect(fileName);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}