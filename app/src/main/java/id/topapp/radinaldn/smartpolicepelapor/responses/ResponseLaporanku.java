package id.topapp.radinaldn.smartpolicepelapor.responses;

/**
 * Created by radinaldn on 23/09/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.smartpolicepelapor.models.Laporan;

public class ResponseLaporanku {

    @SerializedName("master")
    @Expose
    private List<Laporan> laporans = null;

    public List<Laporan> getLaporans() {
        return laporans;
    }

    public void setLaporans(List<Laporan> laporans) {
        this.laporans = laporans;
    }

}