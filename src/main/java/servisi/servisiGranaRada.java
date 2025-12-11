package servisi;

import com.example.newnomads.DB;
import bazneTabele.GranaRada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class servisiGranaRada {


    public static Task<ObservableList<GranaRada>> getSveGraneRadaAsync() {
        return new Task<>() {
            @Override
            protected ObservableList<GranaRada> call() throws Exception {
                ObservableList<GranaRada> lista = FXCollections.observableArrayList();

                String query = "SELECT idGraneRada, nazivGraneRada FROM granaRada";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        lista.add(new GranaRada(
                                rs.getInt("idGraneRada"),
                                rs.getString("nazivGraneRada")
                        ));
                    }
                }

                return lista;
            }
        };
    }


    public static Task<GranaRada> getGranaRadaByIdAsync(int id) {
        return new Task<>() {
            @Override
            protected GranaRada call() throws Exception {
                String query = "SELECT * FROM granaRada WHERE idGraneRada = ?";

                try (Connection conn = DB.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setInt(1, id);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return new GranaRada(
                                    rs.getInt("idGraneRada"),
                                    rs.getString("nazivGraneRada")
                            );
                        }
                    }
                }

                return null;
            }
        };
    }
}
