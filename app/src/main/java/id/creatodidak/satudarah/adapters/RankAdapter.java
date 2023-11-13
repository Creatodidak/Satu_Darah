package id.creatodidak.satudarah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.MRank;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<MRank> rankItemList;
    private Context context;

    public RankAdapter(List<MRank> rankItemList, Context context) {
        this.rankItemList = rankItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MRank rankItem = rankItemList.get(position);

        // Set data to the views
        holder.tvRanks.setText(rankItem.getRank());
        holder.tvNameRanks.setText(rankItem.getNama());
        holder.tvPointRanks.setText(rankItem.getPoint());
        Glide.with(context)
                .load(rankItem.getGambar())
                .circleCrop()
                .placeholder(R.drawable.mrdonor)
                .error(R.drawable.mrdonor)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivRanks);
    }

    @Override
    public int getItemCount() {
        return rankItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRanks;
        ImageView ivRanks;
        TextView tvNameRanks;
        TextView tvPointRanks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRanks = itemView.findViewById(R.id.tvRanks);
            ivRanks = itemView.findViewById(R.id.ivRanks);
            tvNameRanks = itemView.findViewById(R.id.tvNameRanks);
            tvPointRanks = itemView.findViewById(R.id.tvPointRanks);
        }
    }
}
