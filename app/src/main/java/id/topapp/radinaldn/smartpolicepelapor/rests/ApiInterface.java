package id.topapp.radinaldn.smartpolicepelapor.rests;


import java.util.HashMap;
import java.util.Map;

import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseCreateLaporan;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseKantor;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseKategori;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseLaporanku;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseLogin;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by radinaldn on 06/07/18.
 */

public interface ApiInterface {

    /*
    API Dosen
     */

    @GET("laporan/find-by-id-laporan")
    Call<ResponseLaporanku> laporanFindByIdLaporan(
            @Query("id_laporan") String id_laporan
    );

    @GET("laporan/find-all-terbaru-not-ditolak")
    Call<ResponseLaporanku> laporanFindAllTerbaruNotDitolak(
    );

    // untuk mendapatkan data kehadiran dosen berdasarkan inputan nama
    @GET("kantor/find-all")
    Call<ResponseKantor> kantorFindAll(
    );

    // untuk mendapatkan data kehadiran dosen berdasarkan inputan nama
    @GET("kategori/find-all")
    Call<ResponseKategori> kategoriFindAll(
    );

    // untuk mendapatkan data laporan by id_pelapor
    @GET("laporan/find-all-by-id-pelapor")
    Call<ResponseLaporanku> laporanFindAllByIdPelapor(
            @Query("id_pelapor") String id_pelapor
    );

    @GET("laporan/find-all-by-id-pelapor-and-date")
    Call<ResponseLaporanku> laporanFindAllByIdPelaporAndDate(
            @Query("id_pelapor") String id_pelapor,
            @Query("date") String date
    );

    @FormUrlEncoded
    @POST("laporan/create")
    Call<ResponseCreateLaporan> laporanCreate(
            @Field("id_pelapor") String id_pelapor,
            @Field("judul") String judul,
            @Field("lokasi") String lokasi,
            @Field("id_kategori") String id_kategori,
            @Field("foto") String foto,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("ket") String ket
    );

    // untuk menerima presensi dan mengurangi poin tidak hadir
    @FormUrlEncoded
    @POST("login/pelapor")
    Call<ResponseLogin> pelaporLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}
