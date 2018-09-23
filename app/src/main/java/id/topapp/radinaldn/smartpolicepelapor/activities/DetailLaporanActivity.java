package id.topapp.radinaldn.smartpolicepelapor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.topapp.radinaldn.smartpolicepelapor.R;
import id.topapp.radinaldn.smartpolicepelapor.config.ServerConfig;
import id.topapp.radinaldn.smartpolicepelapor.models.Laporan;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseLaporanku;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLaporanActivity extends AppCompatActivity {

    TextView tv_pelapor, tv_lokasi, tv_ket, tv_kategori, tv_status, tv_tanggal;
    ImageView iv_foto;
    String ID_LAPORAN;
    ApiInterface apiService;
    CollapsingToolbarLayout collapsingToolbar;
    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        ID_LAPORAN = getIntent().getStringExtra(Laporan.TAG_ID_LAPORAN);

        iv_foto = findViewById(R.id.iv_foto);
        tv_pelapor = findViewById(R.id.tv_pelapor);
        tv_lokasi = findViewById(R.id.tv_lokasi);
        tv_ket = findViewById(R.id.tv_ket);
        tv_kategori = findViewById(R.id.tv_kategori);
        tv_status = findViewById(R.id.tv_status);
        tv_tanggal = findViewById(R.id.tv_tanggal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       collapsingToolbar = findViewById(R.id.toolbar_layout);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (lat!=null){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lng+"&z=17"));
                    startActivity(intent);
                }

            }
        });

        apiService.laporanFindByIdLaporan(ID_LAPORAN).enqueue(new Callback<ResponseLaporanku>() {
            @Override
            public void onResponse(Call<ResponseLaporanku> call, Response<ResponseLaporanku> response) {
                if (response.isSuccessful()){
                    if (response.body().getLaporans().size()>0){


                        ArrayList<Laporan> laporans = new ArrayList<>();
                        laporans.addAll(response.body().getLaporans());
                        Laporan laporan = laporans.get(0);

                        Picasso.get().load(ServerConfig.LAPORAN_PATH+laporan.getFoto()).into(iv_foto);

                        collapsingToolbar.setTitle(laporan.getJudul());
                        tv_pelapor.setText(laporan.getNamaPelapor());
                        tv_lokasi.setText(laporan.getLokasi());
                        tv_ket.setText(laporan.getKet());
                        tv_kategori.setText(laporan.getKategori());
                        tv_status.setText(laporan.getStatus());
                        tv_tanggal.setText(laporan.getCreatedAt());
                        lat = laporan.getLat();
                        lng = laporan.getLng();

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLaporanku> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
}
