package id.topapp.radinaldn.smartpolicepelapor.rests;



import id.topapp.radinaldn.smartpolicepelapor.responses.ResponseReverseGeocoding;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by radinaldn on 10/08/18.
 */

public interface MyGoogleApiInterface {
    // untuk mendapatkan response dari Google Reverse Geocoding
    @GET("geocode/json")
    Call<ResponseReverseGeocoding> geocodeJson(
            @Query("latlng") String latlng,
            @Query("key") String key
    );
}
