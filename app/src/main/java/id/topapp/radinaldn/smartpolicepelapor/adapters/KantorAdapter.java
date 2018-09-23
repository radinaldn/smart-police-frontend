package id.topapp.radinaldn.smartpolicepelapor.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.topapp.radinaldn.smartpolicepelapor.R;
import id.topapp.radinaldn.smartpolicepelapor.config.ServerConfig;
import id.topapp.radinaldn.smartpolicepelapor.models.Kantor;
import id.topapp.radinaldn.smartpolicepelapor.models.Kategori;
import id.topapp.radinaldn.smartpolicepelapor.models.Laporan;

/**
 * Created by radinaldn on 23/09/18.
 */

public class KantorAdapter extends RecyclerView.Adapter<KantorAdapter.KantorViewHolder> {

    private ArrayList<Kantor> dataList;
    private static final String TAG = KantorAdapter.class.getSimpleName();
    private Context context;

    public KantorAdapter(ArrayList<Kantor> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public KantorAdapter.KantorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_kantor, parent, false);
        return new KantorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KantorAdapter.KantorViewHolder holder, int position) {
        holder.tv_id_kantor.setText(dataList.get(position).getIdKantor());
        holder.tv_judul.setText(dataList.get(position).getNama());
        holder.tv_telp.setText(dataList.get(position).getTelp());
        holder.tv_alamat.setText(dataList.get(position).getAlamat());
        holder.tv_kategori.setText(dataList.get(position).getKategori());
        Picasso.get().load(ServerConfig.KANTOR_PATH+dataList.get(position).getFoto()).into(holder.iv_foto);

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class KantorViewHolder extends RecyclerView.ViewHolder {

        TextView tv_judul, tv_id_kantor, tv_alamat, tv_kategori, tv_telp;
        ImageView iv_foto;

        public KantorViewHolder(View itemView) {
            super(itemView);

            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_telp = itemView.findViewById(R.id.tv_telp);
            tv_id_kantor = itemView.findViewById(R.id.tv_id_kantor);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_kategori = itemView.findViewById(R.id.tv_kategori);
            iv_foto = itemView.findViewById(R.id.iv_foto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // action click
                }
            });
        }
    }
}
