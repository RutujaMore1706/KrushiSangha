package com.apkstudios.krushisangha.models;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkstudios.krushisangha.R;

import java.util.ArrayList;

/**
 * Created by Admin on 21-12-2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {
    ArrayList<Cards> cards = new ArrayList<Cards>();
    Context ctx;


    public CardsAdapter(ArrayList<Cards> cards, Context ctx){
        this.cards = cards;
        this.ctx = ctx;
    }
    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        CardsViewHolder cardsViewHolder = new CardsViewHolder(view,ctx,cards);
        return cardsViewHolder;
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        Cards card = cards.get(position);
        holder.title.setText(card.getTitle());
        holder.body.setText(card.getBody());
        holder.time.setText(card.getTime());
        if(card.getType().equals("weather"))
            holder.imageView.setImageResource(R.drawable.ic_wb_sunny);
        else if(card.getType().equals("news"))
            holder.imageView.setImageResource(R.drawable.ic_new_releases);
        else if(card.getType().equals("policy"))
            holder.imageView.setImageResource(R.drawable.ic_gov);
        else if(card.getType().equals("NULL"));
        holder.imageView.setImageResource(R.drawable.ic_new_releases);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,body,time;
        ImageView imageView;
        ArrayList<Cards> cards = new ArrayList<Cards>();
        Context ctx;
        public CardsViewHolder(View view,Context ctx,ArrayList<Cards> cards){
            super(view);
            this.cards = cards;
            this.ctx = ctx;
            view.setOnClickListener(this);
            title = view.findViewById(R.id.card_title);
            body = view.findViewById(R.id.card_body);
            time = view.findViewById(R.id.card_time);
            imageView = view.findViewById(R.id.card_imageView);
        }

        @Override
        public void onClick(View v) {
            /*int position = getAdapterPosition();
            Cards cards = this.cards.get(position);
            Intent intent = new Intent(this.ctx,PDFViewer.class);
            intent.putExtra("ID",cards.getID());
            this.ctx.startActivity(intent);*/
            //Toast.makeText(ctx, cards.getID(), Toast.LENGTH_LONG).show();

        }
    }
}

