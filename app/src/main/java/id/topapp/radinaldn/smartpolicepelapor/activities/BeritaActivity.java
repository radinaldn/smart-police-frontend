package id.topapp.radinaldn.smartpolicepelapor.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.topapp.radinaldn.smartpolicepelapor.R;
import id.topapp.radinaldn.smartpolicepelapor.adapters.LaporankuAdapter;
import id.topapp.radinaldn.smartpolicepelapor.models.Laporan;
import id.topapp.radinaldn.smartpolicepelapor.models.Pelapor;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseLaporanku;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiInterface;
import id.topapp.radinaldn.smartpolicepelapor.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ApiInterface apiService;
    private LaporankuAdapter adapter;


    RecyclerView rv_laporanku;
    private ArrayList<Laporan> laporankuList = new ArrayList<>();

    private String SESSION_ID_PELAPOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        sessionManager = new SessionManager(this);
        SESSION_ID_PELAPOR = sessionManager.getPelaporDetail().get(Pelapor.TAG_ID_PELAPOR);

        apiService = ApiClient.getClient().create(ApiInterface.class);


        rv_laporanku = findViewById(R.id.rv_laporanku);
        swipeRefreshLayout = findViewById(R.id.swipe_laporanku);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_kinerja);
        toolbar.setTitle(R.string.berita_terkini);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        // click button back pada title bar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // handling refresh recycler view
//            }
//        });

        refreshUI();

    }

    private void refreshUI() {
        laporankuList.clear();
        apiService.laporanFindAllTerbaruNotDitolak().enqueue(new Callback<ResponseLaporanku>() {
            @Override
            public void onResponse(Call<ResponseLaporanku> call, Response<ResponseLaporanku> response) {
                if (response.isSuccessful()){
                    if (response.body().getLaporans().size()>0){
                        laporankuList.addAll(response.body().getLaporans());

                        adapter = new LaporankuAdapter(laporankuList, BeritaActivity.this);
                        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(BeritaActivity.this);
                        rv_laporanku.setLayoutManager(layoutManager);
                        rv_laporanku.setAdapter(adapter);



                        Log.i("TAG", "onResponse: ada "+response.body().getLaporans().size()+" data");
                        for (int i = 0; i < response.body().getLaporans().size(); i++) {
                            Log.i("TAG", "onResponse: judul "+response.body().getLaporans().get(i).getJudul());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Tidak ada laporan terbaru tanggal ", Toast.LENGTH_SHORT).show();
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
