package id.topapp.radinaldn.smartpolicepelapor.responses;

/**
 * Created by radinaldn on 23/09/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCreateLaporan {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id_laporan")
    @Expose
    private String idLaporan;
    @SerializedName("message")
    @Expose
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(String idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}