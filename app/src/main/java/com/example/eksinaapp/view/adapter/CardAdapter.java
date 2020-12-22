package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Cards;
import com.example.eksinaapp.model.Datum;
import com.example.eksinaapp.presenter.OnItemClick;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Datum> data;
    Context context;
    private OnItemClick mCallback;
    private int mCheckedPostion = -1;
    String cardId;
    boolean isChecked;

    public CardAdapter(List<Datum> data, Context context, OnItemClick mCallback) {
        this.data = data;
        this.context = context;
        this.mCallback=mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_show_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtCardName.setText(data.get(position).getFunding());
        holder.txtVisa.setText(data.get(position).getBrand());
        holder.txtBal.setText("**** **** **** " + data.get(position).getLast4());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int newpos= (int) holder.checkBox.getTag();
                if (b){
                    data.get(newpos).setChecked(true);

//                for (int i=0;i<data.size();i++){
//                    data.get(i).setChecked(false);
//                    notifyDataSetChanged();
//
//                }
//                data.get(newpos).setChecked(true);
//                holder.checkBox.setChecked(true);
                }else {
                    data.get(newpos).setChecked(false);
                    //   holder.checkBox.setChecked(false);


                }
            }
        });
        //holder.checkBox.setEnabled(data.get(position).isEnabled());

        if(holder.checkBox.isChecked()){
            holder.checkBox.setChecked(position == mCheckedPostion);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == mCheckedPostion) {
                        holder.checkBox.setChecked(false);
                        mCheckedPostion = -1;
                    } else {
                        mCheckedPostion = position;
                        cardId= data.get(position).getId();
                        Log.d("getdata",cardId);
                        mCallback.onClick(cardId);
                        notifyDataSetChanged();
                    }
                }
            });
        }
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
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCardName=itemView.findViewById(R.id.txtCardName);
            txtVisa=itemView.findViewById(R.id.txtVisa);
            txtBal=itemView.findViewById(R.id.txtBal);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }

}
