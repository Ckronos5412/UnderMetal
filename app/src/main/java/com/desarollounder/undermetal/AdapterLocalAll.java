package com.desarollounder.undermetal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterLocalAll extends RecyclerView.Adapter<AdapterLocalAll.LocalViewHolder> {

    ArrayList<clsLocal> locales;
    private Context context;
    private Activity activity;

    public static class LocalViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView localNombre, latLocal, lonLocal, localId, localDir;
        ImageView eventoImg;

        LocalViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            localNombre = (TextView)itemView.findViewById(R.id.local_nombre);
            localDir = (TextView)itemView.findViewById(R.id.local_dir);
            localId = (TextView)itemView.findViewById(R.id.local_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),ActivityLocalDetalle.class)
                            .putExtra("IdLocal",localId.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    AdapterLocalAll(Context context, ArrayList<clsLocal> locales){
        this.context = context;
        this.locales = locales;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_local_all, viewGroup, false);
        LocalViewHolder nvh = new LocalViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(LocalViewHolder localViewHolder, int i) {
        localViewHolder.localNombre.setText(locales.get(i).getNombreLocal());
        localViewHolder.localDir.setText(locales.get(i).getDirLocal());
        localViewHolder.localId.setText(String.valueOf(locales.get(i).getIdLocal()));
    }

    @Override
    public int getItemCount() {
        return locales.size();
    }
}
