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

public class LaporankuActivity extends AppCompatActivity {


    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ApiInterface apiService;
    private LaporankuAdapter adapter;

    DatePickerTimeline dp_timeline;
    RecyclerView rv_laporanku;
    private ArrayList<Laporan> laporankuList = new ArrayList<>();

    private String SESSION_ID_PELAPOR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporanku);
        sessionManager = new SessionManager(this);
        SESSION_ID_PELAPOR = sessionManager.getPelaporDetail().get(Pelapor.TAG_ID_PELAPOR);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        dp_timeline = findViewById(R.id.dpt_timeline);
        rv_laporanku = findViewById(R.id.rv_laporanku);
        swipeRefreshLayout = findViewById(R.id.swipe_laporanku);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_kinerja);
        toolbar.setTitle(R.string.laporanku);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        // click button back pada title bar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dp_timeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return Integer.toString(calendar.get(Calendar.MONTH) + 1) +"/" +(calendar.get(Calendar.YEAR) % 2000);
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

        // init widget datetimeline
        dp_timeline.setFirstVisibleDate(cur_year, Calendar.JULY, 1);
        dp_timeline.setSelectedDate(cur_year, (cur_month-1), cur_day);
        dp_timeline.setLastVisibleDate(2020, Calendar.JULY, 1);

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // handling refresh recycler view
//            }
//        });

        String tahun = String.valueOf(cur_year);
        String bulan = ((cur_month < 10 ? "0" : "") +cur_month);
        String tanggal = (cur_day < 10 ? "0" : "") + cur_day;


        refreshUI(tahun, bulan, tanggal);

        dp_timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                //Toast.makeText(getApplicationContext(), "Sekarang tahun "+year+", bulan "+(month+1)+", tanggal "+day, Toast.LENGTH_LONG).show();

                int month2 = month+1;
                String tahun = String.valueOf(year);
                String bulan = ((month2 < 10 ? "0" : "") +month2);
                String tanggal = (day < 10 ? "0" : "") + day;



                refreshUI(tahun, bulan, tanggal);
            }
        });


    }

    private void refreshUI(final String tahun, final String bulan, final String tanggal) {
        laporankuList.clear();
        String date = tahun+"-"+bulan+"-"+tanggal;
        apiService.laporanFindAllByIdPelaporAndDate(SESSION_ID_PELAPOR, date).enqueue(new Callback<ResponseLaporanku>() {
            @Override
            public void onResponse(Call<ResponseLaporanku> call, Response<ResponseLaporanku> response) {
                if (response.isSuccessful()){
                    if (response.body().getLaporans().size()>0){
                        laporankuList.addAll(response.body().getLaporans());

                        adapter = new LaporankuAdapter(laporankuList, LaporankuActivity.this);
                        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(LaporankuActivity.this);
                        rv_laporanku.setLayoutManager(layoutManager);
                        rv_laporanku.setAdapter(adapter);



                        Log.i("TAG", "onResponse: ada "+response.body().getLaporans().size()+" data");
                        for (int i = 0; i < response.body().getLaporans().size(); i++) {
                            Log.i("TAG", "onResponse: judul "+response.body().getLaporans().get(i).getJudul());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Tidak ada laporan tanggal "+tanggal+" "+bulan+" "+tahun, Toast.LENGTH_SHORT).show();
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
