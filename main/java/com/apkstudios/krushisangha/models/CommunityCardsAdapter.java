package com.apkstudios.krushisangha.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apkstudios.krushisangha.R;
import java.util.ArrayList;

public class CommunityCardsAdapter extends RecyclerView.Adapter<CommunityCardsAdapter.CardsViewHolder> {
    ArrayList<CommunityCards> cards = new ArrayList<CommunityCards>();
    Context ctx;


    public CommunityCardsAdapter(ArrayList<CommunityCards> cards, Context ctx) {
        this.cards = cards;
        this.ctx = ctx;
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_card_layout, parent, false);
        CardsViewHolder cardsViewHolder = new CardsViewHolder(view, ctx, cards);
        return cardsViewHolder;
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        CommunityCards card = cards.get(position);
        holder.product.setText(card.getProduct());
        holder.current_price.setText(card.getCurrent_price());
        holder.our_price.setText(card.getMin() + " - " + card.getMax());
        holder.quantity.setText(card.getQuantity());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product, current_price, our_price, quantity;
        ArrayList<CommunityCards> cards = new ArrayList<CommunityCards>();
        Context ctx;

        public CardsViewHolder(View view, Context ctx, ArrayList<CommunityCards> cards) {
            super(view);
            this.cards = cards;
            this.ctx = ctx;
            view.setOnClickListener(this);
            product = view.findViewById(R.id.card_product);
            current_price = view.findViewById(R.id.card_current_price);
            our_price = view.findViewById(R.id.card_our_price);
            quantity = view.findViewById(R.id.card_your_quantity);
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