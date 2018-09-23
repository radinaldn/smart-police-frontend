package id.topapp.radinaldn.smartpolicepelapor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.ankit.gpslibrary.MyTracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.topapp.radinaldn.smartpolicepelapor.R;
import id.topapp.radinaldn.smartpolicepelapor.config.ServerConfig;
import id.topapp.radinaldn.smartpolicepelapor.models.Pelapor;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseCreateLaporan;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseKategori;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseReverseGeocoding;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiInterface;
import id.topapp.radinaldn.smartpolicepelapor.rests.MyGoogleApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.MyGoogleApiInterface;
import id.topapp.radinaldn.smartpolicepelapor.utils.ConnectionDetector;
import id.topapp.radinaldn.smartpolicepelapor.utils.HttpFileUpload;
import id.topapp.radinaldn.smartpolicepelapor.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporCreateActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static String SESSION_ID_PELAPOR;
    SessionManager sessionManager;
    Double myLat, myLng;
    MyTracker tracker;
    private LinearLayout ll_rg;
    private RadioButton[] rb_kategori;
    RadioGroup rg;

    private GoogleMap mMap;
    private ProgressDialog pDialog;
    EditText et_judul, et_ket, et_lokasi;
    TextView tv_lat, tv_lng;
    Button bt_ambil_lokasi, bt_kirim, bt_batal;
    ImageButton bt_kamera;
    ImageView iv_compress;
    MyGoogleApiInterface googleApiService;
    ApiInterface apiService;
    private static final String TAG = LaporCreateActivity.class.getSimpleName();

    String hasilGeocode;
    File destFile;
    File file;
    private SimpleDateFormat dateFormatter;
    private Uri imageCaptureUri;
    Bitmap bmp;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String IMAGE_DIRECTORY = "SmartPolice";
    private Boolean upflag = false;
    String fname;
    String finalPhotoName;
    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor_create);
        sessionManager = new SessionManager(this);
        SESSION_ID_PELAPOR = sessionManager.getPelaporDetail().get(Pelapor.TAG_ID_PELAPOR);
        tracker=new MyTracker(this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        googleApiService = MyGoogleApiClient.getGoogleApiClient().create(MyGoogleApiInterface.class);
        cd = new ConnectionDetector(this);

        et_judul = findViewById(R.id.et_judul);
        et_ket = findViewById(R.id.et_ket);
        et_lokasi = findViewById(R.id.et_lokasi);
        iv_compress = findViewById(R.id.ivImageCompress);
        tv_lat = findViewById(R.id.tvlat);
        tv_lng = findViewById(R.id.tvlng);
        bt_ambil_lokasi = findViewById(R.id.bt_ambil_lokasi);
        ll_rg = findViewById(R.id.layout_rg);
        bt_kamera = findViewById(R.id.btnCamera);
        bt_kirim = findViewById(R.id.btkirim);
        bt_batal = findViewById(R.id.btbatal);

        rb_kategori = new RadioButton[3];

        dateFormatter = new SimpleDateFormat(
                DATE_FORMAT, Locale.US);

        file = new File(Environment.getExternalStorageDirectory()
                + "/" + IMAGE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }

        // get kategori
        refreshRadioButtonKategori();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new GetMyLocation().execute();

        bt_ambil_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetMyLocation().execute();
            }
        });

        bt_kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalPhotoName = "laporan_" + dateFormatter.format(new Date()) + ".jpg";
                clickKamera();
            }
        });

        bt_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimLaporan();
            }
        });

        bt_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_compress.setImageResource(android.R.color.transparent);
                iv_compress.setVisibility(v.GONE);
                fname = null;
                et_judul.setText("");
                // replace with action default checked radio button kegiatan
//                etlokasikec.setText("");
//                etlokasikel.setText("");
                et_ket.setText("");
            }
        });
    }

    private void kirimLaporan() {
        String judul = et_judul.getText().toString();
        String foto = finalPhotoName;
        String id_kategori = String.valueOf(rg.getCheckedRadioButtonId());
        String ket = et_ket.getText().toString();
        String lokasi = et_lokasi.getText().toString();
        String lat = tv_lat.getText().toString();
        String lng = tv_lng.getText().toString();

        // save foto into internal storage
        if (destFile!=null) {
            saveFile(destFile);
        } else {
            Toast.makeText(getApplicationContext(), "Anda belum mengambil gambar", Toast.LENGTH_SHORT).show();
        }

        apiService.laporanCreate(SESSION_ID_PELAPOR, judul, lokasi, id_kategori, foto, lat, lng, ket).enqueue(new Callback<ResponseCreateLaporan>() {
            @Override
            public void onResponse(Call<ResponseCreateLaporan> call, Response<ResponseCreateLaporan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equalsIgnoreCase("200")){
                        String id_laporan = response.body().getIdLaporan();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateLaporan> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

        System.out.println("Judul : "+judul+"\nFoto : "+foto+"\nid_kategori : "+id_kategori+"\n ket : "+ket+"\nlokasi : "+lokasi+"\nlat : "+lat+"\nlng : "+lng);


    }

    private void clickKamera() {
        destFile = new File(file, finalPhotoName);
        imageCaptureUri = Uri.fromFile(destFile);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
        startActivityForResult(intentCamera, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            switch (requestCode){
                case 101:
                    if (resultCode == Activity.RESULT_OK){
                        upflag = true;
                        Log.d(TAG+ ". Pick camera image ", "Selected image uri path : "+imageCaptureUri);

                        bmp = decodeFile(destFile, finalPhotoName);
                        iv_compress.setVisibility(View.VISIBLE);
                        iv_compress.setImageBitmap(bmp);

                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshRadioButtonKategori() {
        apiService.kategoriFindAll().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful()){

                    int jumlah_kategori = response.body().getKategoris().size();

                    rb_kategori = new RadioButton[jumlah_kategori];

                    rg = new RadioGroup(LaporCreateActivity.this);
                    rg.setOrientation(RadioGroup.VERTICAL);
                    for (int i = 0; i < jumlah_kategori; i++) {
                        rb_kategori[i] = new RadioButton(LaporCreateActivity.this);
                        rb_kategori[i].setId(Integer.parseInt(response.body().getKategoris().get(i).getIdKategori()));
                        rb_kategori[i].setText(response.body().getKategoris().get(i).getJudul());
                        rg.addView(rb_kategori[i]);
                    }

                    rb_kategori[0].setChecked(true);
                    ll_rg.addView(rg);

                } else {
                    Log.e(TAG, "onResponse Failure : "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(16);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068,101.447779)));

        if (myLat!=null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLat,myLng)));
        }

        mMap.setMyLocationEnabled(true);
    }

    void getLocation(){


        myLat = tracker.getLatitude();
        myLng = tracker.getLongitude();

        System.out.println(tracker.getLatitude());

        System.out.println(tracker.getLongitude());
        System.out.println(tracker.getLocation());

        tv_lat.setText(String.valueOf(tracker.getLatitude()));
        tv_lng.setText(String.valueOf(tracker.getLongitude()));

        String latlng = myLat+","+myLng;
        String lokasiGeocode = getKotaByGeocodeJson(latlng, ServerConfig.GOOGLE_API_KEY);

        et_lokasi.setText(lokasiGeocode);

    }

    class GetMyLocation extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(LaporCreateActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengambil lokasi..");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (tracker==null){
                tracker = new MyTracker(LaporCreateActivity.this);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            getLocation();
            Toast.makeText(getApplicationContext(), "Selesai mendapatkan lokasi ", Toast.LENGTH_LONG).show();
            // selesaikan activity

        }
    }

    private String getKotaByGeocodeJson(String latlng, String googleApiKey) {
        googleApiService.geocodeJson(latlng, googleApiKey).enqueue(new Callback<ResponseReverseGeocoding>() {
            @Override
            public void onResponse(Call<ResponseReverseGeocoding> call, Response<ResponseReverseGeocoding> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equalsIgnoreCase("OK")){

                        hasilGeocode = response.body().getResults().get(0).getFormattedAddress();
                    }
                } else {
                    Log.e(TAG, "onResponse Failed: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseReverseGeocoding> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

        return hasilGeocode;
    }

    private Bitmap decodeFile(File f, String final_photo_name) {
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Width :" + b.getWidth() + " Height :" + b.getHeight());

        fname = final_photo_name;
        destFile = new File(file, fname);

        return b;



    }

    // for saving image to storage
    private void saveFile(File destination) {
        if (destination.exists()) destination.delete();

        try {
            FileOutputStream out = new FileOutputStream(destFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

            if (cd.isConnectingToInternet()){
                new UploadFoto().execute();
            } else {
                Toast.makeText(getApplicationContext(), "Anda tidak memiliki koneksi internet", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UploadFoto extends AsyncTask <String, String, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(LaporCreateActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengupload gambar..");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // Set your file path here
                FileInputStream fstrm = new FileInputStream(destFile);
                // Set your server page url (and the file title/description)
                HttpFileUpload hfu = new HttpFileUpload(ServerConfig.UPLOAD_FOTO_ENDPOINT, "ftitle", "fdescription", finalPhotoName);
                upflag = hfu.Send_Now(fstrm);

            } catch (FileNotFoundException e) {
                // Error: File not found
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (upflag) {
                Toast.makeText(getApplicationContext(), "Upload gambar berhasil", Toast.LENGTH_LONG).show();
                // selesaikan activity
                finish();
                restartFirstActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Sayangnya gambar tidak bisa diupload..", Toast.LENGTH_LONG).show();
            }
        }

        private void restartFirstActivity()
        {
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName() );

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);
        }
    }
}
