package com.example.newnomads;

public class Session {

    private static int userId;
    private static int idFirme;
    private static String role;
    private static String ime;

    // SETTERI
    public static void setUser(int userId, String role, String ime) {
        Session.userId = userId;
        Session.role = role;
        Session.ime = ime;
    }

    public static void setFirma(int idFirme) {
        Session.idFirme = idFirme;
    }

    // GETTERI
    public static int getUserId() {
        return userId;
    }

    public static int getIdFirme() {
        return idFirme;
    }

    public static String getRole() {
        return role;
    }

    public static String getIme() {
        return ime;
    }

    // LOGOUT
    public static void clear() {
        userId = 0;
        idFirme = 0;
        role = null;
        ime = null;
    }
}
