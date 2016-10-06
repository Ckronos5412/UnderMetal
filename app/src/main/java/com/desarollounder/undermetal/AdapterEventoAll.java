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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterEventoAll extends RecyclerView.Adapter<AdapterEventoAll.EventoViewHolder> {

    ArrayList<clsEvento> eventos;
    private Context context;
    private Activity activity;

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView eventoDescrip, eventoFecha, eventoId;
        ImageView eventoImg;

        EventoViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            eventoId = (TextView)itemView.findViewById(R.id.evento_id);
            eventoDescrip = (TextView)itemView.findViewById(R.id.evento_descrip);
            eventoFecha = (TextView)itemView.findViewById(R.id.evento_fecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),ActivityEventoDetalle.class)
                            .putExtra("IdEvento",eventoId.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    AdapterEventoAll(Context context, ArrayList<clsEvento> eventos){
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evento_all, viewGroup, false);
        EventoViewHolder nvh = new EventoViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(EventoViewHolder eventoViewHolder, int i) {
        eventoViewHolder.eventoDescrip.setText(eventos.get(i).getDescripcionEvento());
        eventoViewHolder.eventoFecha.setText(eventos.get(i).getFechaEvento());
        eventoViewHolder.eventoId.setText(String.valueOf(eventos.get(i).getId()));
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
