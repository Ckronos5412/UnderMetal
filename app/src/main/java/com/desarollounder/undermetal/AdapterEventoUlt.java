package com.desarollounder.undermetal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterEventoUlt extends RecyclerView.Adapter<AdapterEventoUlt.EventoViewHolder> {

    ArrayList<clsEvento> eventos;
    private Context context;

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
            eventoImg = (ImageView)itemView.findViewById(R.id.evento_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),ActivityEventoDetalle.class)
                            .putExtra("IdEvento",eventoId.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    AdapterEventoUlt(Context context, ArrayList<clsEvento> eventos){
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evento_ult, viewGroup, false);
        EventoViewHolder nvh = new EventoViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(EventoViewHolder eventoViewHolder, int i) {
        eventoViewHolder.eventoDescrip.setText(eventos.get(i).getDescripcionEvento());
        eventoViewHolder.eventoId.setText(String.valueOf(eventos.get(i).getId()));
        eventoViewHolder.eventoFecha.setText(eventos.get(i).getFechaEvento());
        Glide.with(context).load(eventos.get(i).getImagenEvento())
                .into(eventoViewHolder.eventoImg);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
