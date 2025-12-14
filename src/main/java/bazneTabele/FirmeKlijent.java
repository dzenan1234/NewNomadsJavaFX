package bazneTabele;

public class FirmeKlijent {

    private int idFirme;              // PRIMARY KEY
    private String imeFirme;
    private int idGraneRada;
    private String mail;
    private String lozinka;
    private String oFirmi;
    private String vlasnikFirme;
    private String brojTelefona;
    private String drugiKontakt;
    private int drzavaId;

    public FirmeKlijent(int idFirme,
                        String imeFirme,
                        int idGraneRada,
                        String mail,
                        String lozinka,
                        String oFirmi,
                        String vlasnikFirme,
                        String brojTelefona,
                        String drugiKontakt,
                        int drzavaId) {

        this.idFirme = idFirme;
        this.imeFirme = imeFirme;
        this.idGraneRada = idGraneRada;
        this.mail = mail;
        this.lozinka = lozinka;
        this.oFirmi = oFirmi;
        this.vlasnikFirme = vlasnikFirme;
        this.brojTelefona = brojTelefona;
        this.drugiKontakt = drugiKontakt;
        this.drzavaId = drzavaId;
    }

    // GETTERI
    public int getIdFirme() { return idFirme; }
    public String getImeFirme() { return imeFirme; }
    public int getIdGraneRada() { return idGraneRada; }
    public String getMail() { return mail; }
    public String getLozinka() { return lozinka; }
    public String getOFirmi() { return oFirmi; }
    public String getVlasnikFirme() { return vlasnikFirme; }
    public String getBrojTelefona() { return brojTelefona; }
    public String getDrugiKontakt() { return drugiKontakt; }
    public int getDrzavaId() { return drzavaId; }

    // SETTERI
    public void setImeFirme(String imeFirme) { this.imeFirme = imeFirme; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }
    public void setOFirmi(String oFirmi) { this.oFirmi = oFirmi; }
    public void setVlasnikFirme(String vlasnikFirme) { this.vlasnikFirme = vlasnikFirme; }
    public void setBrojTelefona(String brojTelefona) { this.brojTelefona = brojTelefona; }
    public void setDrugiKontakt(String drugiKontakt) { this.drugiKontakt = drugiKontakt; }
    public void setDrzavaId(int drzavaId) { this.drzavaId = drzavaId; }
    public void setIdGraneRada(int idGraneRada) { this.idGraneRada = idGraneRada; }
}
