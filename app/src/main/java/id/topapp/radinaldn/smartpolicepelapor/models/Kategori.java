package id.topapp.radinaldn.smartpolicepelapor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by radinaldn on 22/09/18.
 */

public class Kategori {

    @SerializedName("id_kategori")
    @Expose
    private String idKategori;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("ket")
    @Expose
    private String ket;

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

}
