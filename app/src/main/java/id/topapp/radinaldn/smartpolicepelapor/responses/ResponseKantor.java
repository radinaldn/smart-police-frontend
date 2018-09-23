package id.topapp.radinaldn.smartpolicepelapor.responses;

/**
 * Created by radinaldn on 23/09/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.smartpolicepelapor.models.Kantor;

public class ResponseKantor {

    @SerializedName("master")
    @Expose
    private List<Kantor> kantors = null;

    public List<Kantor> getKantors() {
        return kantors;
    }

    public void setKantors(List<Kantor> kantors) {
        this.kantors = kantors;
    }

}