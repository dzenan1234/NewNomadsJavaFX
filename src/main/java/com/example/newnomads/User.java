package com.example.newnomads;

public class User {
    private int id;
    private String ime;
    private String email;
    private String password;
    private String role;

    public User(String ime, String email, String password, String role) {
        this.ime = ime;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getIme() { return ime; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
