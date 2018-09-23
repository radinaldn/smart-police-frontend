package id.topapp.radinaldn.smartpolicepelapor.responses;

/**
 * Created by radinaldn on 22/09/18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.smartpolicepelapor.models.Kategori;

public class ResponseKategori {

    @SerializedName("master")
    @Expose
    private List<Kategori> kategoris = null;

    public List<Kategori> getKategoris() {
        return kategoris;
    }

    public void setKategorisr(List<Kategori> kategorisr) {
        this.kategoris = kategoris;
    }

}