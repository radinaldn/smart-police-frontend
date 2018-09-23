package id.topapp.radinaldn.smartpolicepelapor.adapters;

import android.content.Context;
import android.content.Intent;
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
import id.topapp.radinaldn.smartpolicepelapor.activities.DetailLaporanActivity;
import id.topapp.radinaldn.smartpolicepelapor.config.ServerConfig;
import id.topapp.radinaldn.smartpolicepelapor.models.Laporan;

/**
 * Created by radinaldn on 23/09/18.
 */

public class LaporankuAdapter extends RecyclerView.Adapter<LaporankuAdapter.LaporankuViewHolder> {

    private ArrayList<Laporan> dataList;
    private static final String TAG = LaporankuAdapter.class.getSimpleName();
    private static final String TAG_ID_PRESENSI = "id_presensi";
    private static final String TAG_STATUS_PRESENSI = "status_presensi";
    private Context context;

    public LaporankuAdapter(ArrayList<Laporan> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public LaporankuAdapter.LaporankuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_laporanku, parent, false);
        return new LaporankuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporankuAdapter.LaporankuViewHolder holder, int position) {
        holder.tv_id_laporan.setText(dataList.get(position).getIdLaporan());
        holder.tv_judul.setText(dataList.get(position).getJudul());
        holder.tv_lokasi.setText(dataList.get(position).getLokasi());
        holder.tv_waktu.setText(dataList.get(position).getCreatedAt());
        holder.tv_kategori.setText(dataList.get(position).getKategori());
        holder.tv_status.setText(dataList.get(position).getStatus());

        if (dataList.get(position).getStatus().equals(Laporan.DIPROSES)){
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.colorFlower));
        } else if (dataList.get(position).getStatus().equals(Laporan.DITOLAK)) {
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.RedBootstrap));
        } else {
            holder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.GreenBootstrap));
        }

        Picasso.get().load(ServerConfig.LAPORAN_PATH+dataList.get(position).getFoto()).into(holder.iv_foto);


    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class LaporankuViewHolder extends RecyclerView.ViewHolder {

        TextView tv_judul, tv_lokasi, tv_waktu, tv_kategori, tv_status, tv_id_laporan;
        ImageView iv_foto;

        public LaporankuViewHolder(final View itemView) {
            super(itemView);

            tv_id_laporan = itemView.findViewById(R.id.tv_id_laporan);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_lokasi = itemView.findViewById(R.id.tv_lokasi);
            tv_waktu = itemView.findViewById(R.id.tv_waktu);
            tv_kategori = itemView.findViewById(R.id.tv_kategori);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_foto = itemView.findViewById(R.id.iv_foto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // handling for aciton click
                    Intent intent = new Intent(itemView.getContext(), DetailLaporanActivity.class);
                    intent.putExtra(Laporan.TAG_ID_LAPORAN, tv_id_laporan.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}
