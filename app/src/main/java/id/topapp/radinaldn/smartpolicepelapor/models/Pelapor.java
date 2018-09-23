package id.topapp.radinaldn.smartpolicepelapor.models;

/**
 * Created by radinaldn on 23/09/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pelapor {

    public static final String TAG_FOTO = "foto";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ID_PELAPOR = "id_pelapor";
    public static final String TAG_HP = "hp";

    @SerializedName("id_pelapor")
    @Expose
    private String idPelapor;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jk")
    @Expose
    private String jk;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("hp")
    @Expose
    private String hp;

    public String getIdPelapor() {
        return idPelapor;
    }

    public void setIdPelapor(String idPelapor) {
        this.idPelapor = idPelapor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

}