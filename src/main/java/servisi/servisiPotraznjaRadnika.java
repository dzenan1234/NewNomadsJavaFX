package servisi;

import com.example.newnomads.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import bazneTabele.PotraznjaRadnika;

public class servisiPotraznjaRadnika {

    private static PotraznjaRadnika mapPotraznja(ResultSet rs) throws Exception {
        return new PotraznjaRadnika(
                rs.getInt("idPotraznjeRadnika"),
                rs.getTimestamp("datumKreiranjaPotraznje"),
                rs.getInt("drzavaId"),
                rs.getInt("idFirme"),
                rs.getDate("krajnjiRok"),
                rs.getString("naslovPotraznje"),
                rs.getString("opisPotraznje"),
                rs.getString("statusPotraznje"),
                rs.getString("grana"),           // dodano
                rs.getInt("brojRadnika")         // dodano
        );
    }


    public static Task<Boolean> kreirajPotraznjuAsync(
            int drzavaId,
            int idFirme,
            Date krajnjiRok,
            String naslovPotraznje,
            String opisPotraznje
    ) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {

                // Validacija: krajnjiRok >= danas
                LocalDate danas = LocalDate.now();
                if (krajnjiRok.toLocalDate().isBefore(danas)) {
                    // datum je stariji od današnjeg -> ne dozvoljavamo kreiranje
                    return false;
                }

                String query = """
                        INSERT INTO potraznjaRadnika
                        (drzavaId, idFirme, krajnjiRok, naslovPotraznje, opisPotraznje, statusPotraznje)
                        VALUES (?, ?, ?, ?, ?, 'aktivna')
                        """;

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, drzavaId);
                    stmt.setInt(2, idFirme);
                    stmt.setDate(3, krajnjiRok);
                    stmt.setString(4, naslovPotraznje);
                    stmt.setString(5, opisPotraznje);

                    return stmt.executeUpdate() > 0;
                }
            }
        };
    }

    public static Task<ObservableList<PotraznjaRadnika>> getAktivnePotraznjeSortiranoAsync() {
        return new Task<>() {
            @Override
            protected ObservableList<PotraznjaRadnika> call() throws Exception {
                ObservableList<PotraznjaRadnika> lista = FXCollections.observableArrayList();

                String query = """
                        SELECT * FROM potraznjaRadnika
                        WHERE statusPotraznje = 'aktivna'
                        ORDER BY krajnjiRok ASC
                        """;

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        lista.add(mapPotraznja(rs));
                    }
                }

                return lista;
            }
        };
    }


    public static Task<ObservableList<PotraznjaRadnika>> getNeaktivnePotraznjeSortiranoAsync() {
        return new Task<>() {
            @Override
            protected ObservableList<PotraznjaRadnika> call() throws Exception {
                ObservableList<PotraznjaRadnika> lista = FXCollections.observableArrayList();

                String query = """
                        SELECT * FROM potraznjaRadnika
                        WHERE statusPotraznje = 'neaktivna'
                        ORDER BY krajnjiRok ASC
                        """;

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        lista.add(mapPotraznja(rs));
                    }
                }

                return lista;
            }
        };
    }


    public static Task<ObservableList<PotraznjaRadnika>> getPotraznjeByFirmaSortiranoAsync(int idFirme) {
        return new Task<>() {
            @Override
            protected ObservableList<PotraznjaRadnika> call() throws Exception {
                ObservableList<PotraznjaRadnika> lista = FXCollections.observableArrayList();

                String query = """
                        SELECT * FROM potraznjaRadnika
                        WHERE idFirme = ?
                        ORDER BY krajnjiRok ASC
                        """;

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, idFirme);

                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            lista.add(mapPotraznja(rs));
                        }
                    }
                }

                return lista;
            }
        };
    }
}
