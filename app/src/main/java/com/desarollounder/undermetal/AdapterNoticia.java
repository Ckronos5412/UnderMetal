package com.desarollounder.undermetal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.NoticiaViewHolder> {

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView noticiaDescrip;
        ImageView noticiaImg;

        NoticiaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            noticiaDescrip = (TextView)itemView.findViewById(R.id.noticia_descrip);
            noticiaImg = (ImageView)itemView.findViewById(R.id.noticia_img);
        }
    }

    ArrayList<clsNoticia> noticias;
    private Context context;

    AdapterNoticia(Context context, ArrayList<clsNoticia> noticias){
        this.context = context;
        this.noticias = noticias;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_noticia, viewGroup, false);
        NoticiaViewHolder nvh = new NoticiaViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder noticiaViewHolder, int i) {
        noticiaViewHolder.noticiaDescrip.setText(noticias.get(i).getDescription());
        Glide.with(context).load("http://undermetal.esy.es/resourses/images/noticia/" + noticias.get(i).getId() + ".jpg")
                .into(noticiaViewHolder.noticiaImg);
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
}
