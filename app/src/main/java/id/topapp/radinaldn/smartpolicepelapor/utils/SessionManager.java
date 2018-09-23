package id.topapp.radinaldn.smartpolicepelapor.utils;

/**
 * Created by radinaldn on 23/09/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.HashMap;

/**
 * Created by radinaldn on 03/07/18.
 */

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_PELAPOR = "id_pelapor";
    public static final String NAMA = "nama";
    public static final String JK = "jk";
    public static final String ALAMAT = "alamat";
    public static final String FOTO = "foto";
    public static final String HP = "hp";

    public static final String SHARE_LOC_IS_ON = "shareLocIsOn";

    public Context get_context(){
        return _context;
    }

    // constructor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String id_pelapor, String nama, String jk, String alamat, String foto, String hp){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(ID_PELAPOR, id_pelapor);
        editor.putString(NAMA, nama);
        editor.putString(JK, jk);
        editor.putString(ALAMAT, alamat);
        editor.putString(FOTO, foto);
        editor.putString(HP, hp);
        editor.commit();
    }

    public HashMap<String, String> getPelaporDetail(){
        HashMap<String,String> pelapor = new HashMap<>();
        pelapor.put(ID_PELAPOR, sharedPreferences.getString(ID_PELAPOR,null));
        pelapor.put(NAMA, sharedPreferences.getString(NAMA,null));
        pelapor.put(JK, sharedPreferences.getString(JK,null));
        pelapor.put(ALAMAT, sharedPreferences.getString(ALAMAT,null));
        pelapor.put(FOTO, sharedPreferences.getString(FOTO,null));
        pelapor.put(HP, sharedPreferences.getString(HP,null));

        return pelapor;
    }

    public void logoutPelapor(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
