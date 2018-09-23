package id.topapp.radinaldn.smartpolicepelapor.responses;

/**
 * Created by radinaldn on 23/09/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.smartpolicepelapor.models.Pelapor;

public class ResponseLogin {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Pelapor pelapor;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pelapor getPelapor() {
        return pelapor;
    }

    public void setData(Pelapor pelapor) {
        this.pelapor = pelapor;
    }

}