package id.creatodidak.satudarah.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.GridLayoutData;

public class GridLayoutAdapter extends BaseAdapter {
    private final Context context;
    private final List<GridLayoutData> data;
    private final OnClickListener onClickListener;
    private final SharedPreferences sh;

    public GridLayoutAdapter(Context context, List<GridLayoutData> data, OnClickListener onClickListener) {
        this.context = context;
        this.data = data;
        this.onClickListener = onClickListener;

        sh = context.getSharedPreferences("DATAMANAGER", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }
        GridLayoutData item = data.get(position);
        CardView glWrapper = convertView.findViewById(R.id.glWrapper);
        ImageView iv = convertView.findViewById(R.id.glImg);
        iv.setImageResource(item.getGambar());
        glWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(item.getId());
            }
        });
        return convertView;
    }

    public interface OnClickListener{
        void onClick(int id);
    }
}
