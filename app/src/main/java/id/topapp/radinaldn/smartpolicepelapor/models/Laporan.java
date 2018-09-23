package id.topapp.radinaldn.smartpolicepelapor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by radinaldn on 23/09/18.
 */

public class Laporan {

    public static String DIPROSES = "Diproses";
    public static String DITOLAK = "Ditolak";
    public static String SELESAI = "Selesai";

    public static String TAG_ID_LAPORAN = "id_laporan";
    public static String TAG_NAMA_PELAPOR = "nama_pelapor";
    public static String JUDUL = "judul";
    public static String LOKASI = "lokasi";
    public static String KET = "ket";
    public static String FOTO = "foto";
    public static String KATEGORI = "kategori";
    public static String STATUS = "status";
    public static String TANGGAL = "tanggal";
    public static String LAT = "lat";
    public static String LNG = "lng";

    @SerializedName("id_laporan")
    @Expose
    private String idLaporan;
    @SerializedName("id_pelapor")
    @Expose
    private String idPelapor;
    @SerializedName("nama_pelapor")
    @Expose
    private String namaPelapor;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(String idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getIdPelapor() {
        return idPelapor;
    }

    public void setIdPelapor(String idPelapor) {
        this.idPelapor = idPelapor;
    }

    public String getNamaPelapor() {
        return namaPelapor;
    }

    public void setNamaPelapor(String namaPelapor) {
        this.namaPelapor = namaPelapor;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
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

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
