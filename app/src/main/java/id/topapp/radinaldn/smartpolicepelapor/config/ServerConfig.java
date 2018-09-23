package id.topapp.radinaldn.smartpolicepelapor.config;

/**
 * Created by radinaldn on 22/09/18.
 */

public class ServerConfig {

    public static final String DOMAIN_SERVER = "http://192.168.1.100/";
    public static final String SERVER_URL = DOMAIN_SERVER+"smart-police/api/v1/";
    public static final String API_ENDPOINT = SERVER_URL;
    public static final String UPLOAD_FOTO_ENDPOINT = DOMAIN_SERVER+"smart-police/api/upload/upload-foto.php";
    public static final String GOOGLE_API_ENDPOINT = "https://maps.googleapis.com/maps/api/";
    public static final String GOOGLE_API_KEY = "AIzaSyABTxpRVD2u_czfqMX7bb6H_WYBxbeXvC4";
    public static final String POLISI_PATH = DOMAIN_SERVER+"smart-police/web/files/polisi/";
    public static final String PELAPOR_PATH = DOMAIN_SERVER+"smart-police/web/files/pelapor/";
    public static final String KANTOR_PATH = DOMAIN_SERVER+"smart-police/web/files/kantor/";
    public static final String LAPORAN_PATH = DOMAIN_SERVER+"smart-police/web/files/laporan/";
}
