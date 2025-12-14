package com.example.newnomads;

import bazneTabele.PotraznjaRadnika;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PotraznjaDAO {
    public static boolean dodajPotraznju(PotraznjaRadnika p) {
        String sql = "INSERT INTO potraznjaRadnika " +
                "(datumKreiranjaPotraznje, drzavaId, idFirme, krajnjiRok, naslovPotraznje, opisPotraznje, statusPotraznje, grana, brojRadnika) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, p.getDatumKreiranjaPotraznje());
            ps.setInt(2, p.getDrzavaId());
            ps.setInt(3, p.getIdFirme());
            ps.setDate(4, p.getKrajnjiRok());
            ps.setString(5, p.getNaslovPotraznje());
            ps.setString(6, p.getOpisPotraznje());
            ps.setString(7, p.getStatusPotraznje());
            ps.setString(8, p.getGrana());
            ps.setInt(9, p.getBrojRadnika());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<PotraznjaRadnika> getPotraznjeZaFirmu(int idFirme) {
        List<PotraznjaRadnika> lista = new ArrayList<>();
        String sql = "SELECT * FROM potraznjaRadnika WHERE idFirme = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFirme);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PotraznjaRadnika p = new PotraznjaRadnika(
                        rs.getInt("idPotraznjeRadnika"),
                        rs.getTimestamp("datumKreiranjaPotraznje"),
                        rs.getInt("drzavaId"),
                        rs.getInt("idFirme"),
                        rs.getDate("krajnjiRok"),
                        rs.getString("naslovPotraznje"),
                        rs.getString("opisPotraznje"),
                        rs.getString("statusPotraznje"),
                        rs.getString("grana"),
                        rs.getInt("brojRadnika")
                );
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }



    public static List<PotraznjaRadnika> getPotraznjeByFirma(int idFirme) {
        List<PotraznjaRadnika> lista = new ArrayList<>();
        String sql = "SELECT * FROM potraznjaRadnika WHERE idFirme = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFirme);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PotraznjaRadnika p = new PotraznjaRadnika(
                        rs.getInt("idPotraznjeRadnika"),
                        rs.getTimestamp("datumKreiranjaPotraznje"),
                        rs.getInt("drzavaId"),
                        rs.getInt("idFirme"),
                        rs.getDate("krajnjiRok"),
                        rs.getString("naslovPotraznje"),
                        rs.getString("opisPotraznje"),
                        rs.getString("statusPotraznje"),
                        rs.getString("grana"),
                        rs.getInt("brojRadnika")
                );
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
