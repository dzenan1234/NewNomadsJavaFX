package servisi;

import com.example.newnomads.DB;
import bazneTabele.Drzava;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class servisiDrzave {

    private static final String SELECT_ALL = "SELECT drzavaId, nazivDrzave FROM drzave";


    public static Task<ObservableList<Drzava>> getSveDrzaveAsync() {
        return new Task<>() {
            @Override
            protected ObservableList<Drzava> call() throws Exception {
                ObservableList<Drzava> lista = FXCollections.observableArrayList();

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("drzavaId");
                        String naziv = rs.getString("nazivDrzave");
                        lista.add(new Drzava(id, naziv));
                    }
                }

                return lista;
            }
        };
    }

    public static Drzava getDrzavaById(int id) throws Exception {
        String query = "SELECT drzavaId, nazivDrzave FROM drzave WHERE drzavaId = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Drzava(
                            rs.getInt("drzavaId"),
                            rs.getString("nazivDrzave")
                    );
                }
            }
        }

        return null;
    }

}
