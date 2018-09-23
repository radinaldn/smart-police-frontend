package id.topapp.radinaldn.smartpolicepelapor.models;

/**
 * Created by radinaldn on 23/09/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kantor {

    @SerializedName("id_kantor")
    @Expose
    private String idKantor;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("foto")
    @Expose
    private String foto;

    public String getIdKantor() {
        return idKantor;
    }

    public void setIdKantor(String idKantor) {
        this.idKantor = idKantor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}