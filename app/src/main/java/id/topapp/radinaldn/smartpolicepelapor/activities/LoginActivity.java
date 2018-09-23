package id.topapp.radinaldn.smartpolicepelapor.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.topapp.radinaldn.smartpolicepelapor.models.Pelapor;
import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseLogin;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiClient;
import id.topapp.radinaldn.smartpolicepelapor.rests.ApiInterface;
import id.topapp.radinaldn.smartpolicepelapor.utils.AbsRuntimePermission;
import id.topapp.radinaldn.smartpolicepelapor.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import id.topapp.radinaldn.smartpolicepelapor.R;

public class LoginActivity extends AbsRuntimePermission {

    @BindView(R.id.etnip)
    EditText etnip;

    @BindView(R.id.etpassword)
    EditText etpassword;

    @BindView(R.id.btlogin)
    Button btlogin;

    SessionManager sessionManager;
    ApiInterface apiService;

    String nip, password;
    public final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // do runtime permission
        //request permission here
        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO},
                R.string.msg,REQUEST_PERMISSION);

        //init
        ButterKnife.bind(this);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        if(sessionManager.isLoggedIn()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            // agar tidak balik ke activity ini lagi
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    @Override
    public void onPermissionGranted(int requestcode) {
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    private void loginUser() {
        nip = etnip.getText().toString();
        password = etpassword.getText().toString();

        Log.d(TAG, "loginUser: " + nip +" "+password);

        apiService.pelaporLogin(nip, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: Dapat terhubung ke server");
                    Log.d(TAG, "onResponse: " +response.body().toString());

                    if (response.body().getCode().equalsIgnoreCase("200")){
                        String id_pelapor = response.body().getPelapor().getIdPelapor();
                        String nama = response.body().getPelapor().getNama();
                        String jk = response.body().getPelapor().getJk();
                        String alamat = response.body().getPelapor().getAlamat();
                        String foto = response.body().getPelapor().getFoto();
                        String hp = response.body().getPelapor().getHp();

                        sessionManager.createLoginSession(id_pelapor,
                                nama,
                                jk,
                                alamat,
                                foto,
                                hp);

                        Log.d(TAG, "onResponse: Dapat data dosen");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Gagal login : "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal konek ke server", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }
}
