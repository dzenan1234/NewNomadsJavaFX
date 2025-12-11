package servisi;

import com.example.newnomads.DB;
import bazneTabele.FirmeKlijent;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class servisiFirme{


    public static Task<Boolean> kreirajFirmuKlijentaAsync(
            String imeFirme,
            int idGraneRada,
            String mail,
            String lozinka,
            String oFirmi,
            String vlasnikFirme,
            String brojTelefona,
            String drugiKontakt,
            int drzavaId
    ) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = """
                        INSERT INTO firmeKlijenti
                        (imeFirme, idGraneRada, mail, lozinka, oFirmi,
                         vlasnikFirme, brojTelefona, drugiKontakt, drzavaId)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """;

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, imeFirme);
                    stmt.setInt(2, idGraneRada);
                    stmt.setString(3, mail);
                    stmt.setString(4, lozinka);
                    stmt.setString(5, oFirmi);
                    stmt.setString(6, vlasnikFirme);
                    stmt.setString(7, brojTelefona);
                    stmt.setString(8, drugiKontakt);
                    stmt.setInt(9, drzavaId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<FirmeKlijent> getFirmaByRegruterIdAsync(int regruterId) {
        return new Task<>() {
            @Override
            protected FirmeKlijent call() throws Exception {

                String query = "SELECT * FROM firmeKlijenti WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, regruterId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new FirmeKlijent(
                                    rs.getInt("regruterId"),
                                    rs.getString("imeFirme"),
                                    rs.getInt("idGraneRada"),
                                    rs.getString("mail"),
                                    rs.getString("lozinka"),
                                    rs.getString("oFirmi"),
                                    rs.getString("vlasnikFirme"),
                                    rs.getString("brojTelefona"),
                                    rs.getString("drugiKontakt"),
                                    rs.getInt("drzavaId")
                            );
                        }
                    }
                }
                return null;
            }
        };
    }


    public static Task<FirmeKlijent> getFirmaByMailAsync(String mail) {
        return new Task<>() {
            @Override
            protected FirmeKlijent call() throws Exception {

                String query = "SELECT * FROM firmeKlijenti WHERE mail = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, mail);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new FirmeKlijent(
                                    rs.getInt("regruterId"),
                                    rs.getString("imeFirme"),
                                    rs.getInt("idGraneRada"),
                                    rs.getString("mail"),
                                    rs.getString("lozinka"),
                                    rs.getString("oFirmi"),
                                    rs.getString("vlasnikFirme"),
                                    rs.getString("brojTelefona"),
                                    rs.getString("drugiKontakt"),
                                    rs.getInt("drzavaId")
                            );
                        }
                    }
                }
                return null;
            }
        };
    }




    public static Task<Boolean> promijeniLozinkuAsync(int regruterId, String novaLozinka) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET lozinka = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, novaLozinka);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }

    public static Task<Boolean> promijeniOFirmiAsync(int regruterId, String noviOpis) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET oFirmi = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, noviOpis);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniImeFirmeAsync(int regruterId, String novoImeFirme) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET imeFirme = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, novoImeFirme);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniVlasnikaFirmeAsync(int regruterId, String noviVlasnik) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET vlasnikFirme = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, noviVlasnik);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniBrojTelefonaAsync(int regruterId, String noviBrojTelefona) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET brojTelefona = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, noviBrojTelefona);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniDrugiKontaktAsync(int regruterId, String noviDrugiKontakt) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET drugiKontakt = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, noviDrugiKontakt);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniDrzavaIdAsync(int regruterId, int novaDrzavaId) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET drzavaId = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, novaDrzavaId);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }


    public static Task<Boolean> promijeniIdGraneRadaAsync(int regruterId, int noviIdGraneRada) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String query = "UPDATE firmeKlijenti SET idGraneRada = ? WHERE regruterId = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, noviIdGraneRada);
                    stmt.setInt(2, regruterId);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }
}
