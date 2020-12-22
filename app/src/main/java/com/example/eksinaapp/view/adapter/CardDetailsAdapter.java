package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Cards;
import com.example.eksinaapp.model.Datum;

import java.util.List;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.ViewHolder> {
    List<Datum> data;
    Context context;

    public CardDetailsAdapter(List<Datum> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.card_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCardName.setText(data.get(position).getFunding());
        holder.txtVisa.setText(data.get(position).getBrand());
        holder.txtBal.setText("**** **** **** " + data.get(position).getLast4());
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCardName,txtVisa,txtBal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCardName=itemView.findViewById(R.id.txtCardName);
            txtVisa=itemView.findViewById(R.id.txtVisa);
            txtBal=itemView.findViewById(R.id.txtBal);
        }
    }
}
