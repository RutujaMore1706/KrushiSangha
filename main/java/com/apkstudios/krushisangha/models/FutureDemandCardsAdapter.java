package com.apkstudios.krushisangha.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkstudios.krushisangha.R;

import java.util.ArrayList;

public class FutureDemandCardsAdapter  extends RecyclerView.Adapter<FutureDemandCardsAdapter.CardsViewHolder>{

    ArrayList<FutureDemandCards> cards = new ArrayList<FutureDemandCards>();
    Context ctx;


    public FutureDemandCardsAdapter(ArrayList<FutureDemandCards> cards, Context ctx) {
        this.cards = cards;
        this.ctx = ctx;
    }

    @Override
    public FutureDemandCardsAdapter.CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.future_demand_card, parent, false);
        FutureDemandCardsAdapter.CardsViewHolder cardsViewHolder = new FutureDemandCardsAdapter.CardsViewHolder(view, ctx, cards);
        return cardsViewHolder;
    }

    @Override
    public void onBindViewHolder(FutureDemandCardsAdapter.CardsViewHolder holder, int position) {
        FutureDemandCards card = cards.get(position);
        holder.product.setText(card.getProduct());
        holder.o_name.setText(card.getOrganisation_name());
        holder.quality.setText(card.getQuality());
        holder.delivery.setText(card.getDelivery());
        holder.quantity.setText(card.getQuantity());
        holder.created_at.setText(card.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product, o_name, quality, quantity, delivery,created_at;
        ArrayList<FutureDemandCards> cards = new ArrayList<FutureDemandCards>();
        Context ctx;

        public CardsViewHolder(View view, Context ctx, ArrayList<FutureDemandCards> cards) {
            super(view);
            this.cards = cards;
            this.ctx = ctx;
            view.setOnClickListener(this);
            product = view.findViewById(R.id.future_card_product);
            o_name = view.findViewById(R.id.future_card_organization_name);
            quality = view.findViewById(R.id.future_card_quality);
            created_at = view.findViewById(R.id.future_card_date);
            quantity = view.findViewById(R.id.future_card_quantity);
            delivery = view.findViewById(R.id.future_card_delivery);
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
