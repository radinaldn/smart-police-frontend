package id.topapp.radinaldn.smartpolicepelapor.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.topapp.radinaldn.smartpolicepelapor.R;
import id.topapp.radinaldn.smartpolicepelapor.adapters.KantorAdapter;
import id.topapp.radinaldn.smartpolicepelapor.adapters.LaporankuAdapter;
import id.topapp.radinaldn.smartpolicepelapor.models.Kantor;
import id.topapp.radinaldn.smartpolicepelapor.models.Pelapor;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseKantor;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiInterface;
import id.topapp.radinaldn.smartpolicepelapor.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KantorListActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ApiInterface apiService;
    private KantorAdapter adapter;

    DatePickerTimeline dp_timeline;
    RecyclerView rv_laporanku;
    private ArrayList<Kantor> kantorList = new ArrayList<>();

    private String SESSION_ID_PELAPOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kantor_list);

        sessionManager = new SessionManager(this);
        SESSION_ID_PELAPOR = sessionManager.getPelaporDetail().get(Pelapor.TAG_ID_PELAPOR);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        
        rv_laporanku = findViewById(R.id.rv_kantor);
        swipeRefreshLayout = findViewById(R.id.swipe_kantor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_kantor);
        toolbar.setTitle(R.string.kantor_polisi_terdekat);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        // click button back pada title bar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        
        // get current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String [] cur_dates = (formatter.format(date)).split("-");
        int cur_year = Integer.parseInt(cur_dates[0]);
        int cur_month = Integer.parseInt(cur_dates[1]);
        int cur_day = Integer.parseInt(cur_dates[2]);
        System.out.println(cur_year+", "+cur_month+", "+cur_day);
        

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // handling refresh recycler view
                refreshUI();
            }
        });
        
        refreshUI();
        
    }

    private void refreshUI() {
        kantorList.clear();
        apiService.kantorFindAll().enqueue(new Callback<ResponseKantor>() {
            @Override
            public void onResponse(Call<ResponseKantor> call, Response<ResponseKantor> response) {
                if (response.isSuccessful()) {
                    if (response.body().getKantors().size()>0) {
                        kantorList.addAll(response.body().getKantors());

                        adapter = new KantorAdapter(kantorList, KantorListActivity.this);
                        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(KantorListActivity.this);
                        rv_laporanku.setLayoutManager(layoutManager);
                        rv_laporanku.setAdapter(adapter);

                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        Toast.makeText(getApplication(), "Tidak ada data kantor ", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseKantor> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

}

