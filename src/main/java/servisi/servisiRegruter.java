package servisi;

import com.example.newnomads.DB;
import bazneTabele.Regruter;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class servisiRegruter {


    public static Task<Boolean> kreirajRegruteraAsync(String ime, String mail, String lozinka, boolean potvrdjen) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "INSERT INTO regruter (ime, mail, lozinka, potvrdjen) VALUES (?, ?, ?, ?)";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, ime);
                    stmt.setString(2, mail);
                    stmt.setString(3, lozinka);
                    stmt.setBoolean(4, potvrdjen);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniLozinkuAsync(int regruterId, String novaLozinka) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE regruter SET lozinka = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, novaLozinka);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Regruter> getRegruterByIdAsync(int id) {
        return new Task<>() {
            @Override
            protected Regruter call() throws Exception {
                String query = "SELECT * FROM regruter WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, id);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new Regruter(
                                    rs.getInt("regruterId"),
                                    rs.getString("ime"),
                                    rs.getString("mail"),
                                    rs.getString("lozinka"),
                                    rs.getBoolean("potvrdjen")
                            );
                        }
                    }
                }
                return null;
            }
        };
    }


    public static Task<Regruter> getRegruterByMailAsync(String mail) {
        return new Task<>() {
            @Override
            protected Regruter call() throws Exception {
                String query = "SELECT * FROM regruter WHERE mail = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, mail);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new Regruter(
                                    rs.getInt("regruterId"),
                                    rs.getString("ime"),
                                    rs.getString("mail"),
                                    rs.getString("lozinka"),
                                    rs.getBoolean("potvrdjen")
                            );
                        }
                    }
                }
                return null;
            }
        };
    }
}
