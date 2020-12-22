package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.RecenetTransaction;

import java.util.List;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.ViewHolder> {

List<RecenetTransaction> recenetTransactions;
Context context;

    public RecentTransactionAdapter(List<RecenetTransaction> recenetTransactions, Context context) {
        this.recenetTransactions = recenetTransactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_recent_transfer, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(recenetTransactions.get(position).getFirstName() + " " +  recenetTransactions.get(position).getLastName());
        holder.txtDate.setText(recenetTransactions.get(position).getPayDate());
        holder.txtAmount.setText(recenetTransactions.get(position).getPaidAmount() +" " + recenetTransactions.get(position).getCurrency());
        holder.txtTransfer.setText(recenetTransactions.get(position).getPaymentMethod());
    }

    @Override
    public int getItemCount() {
        if(recenetTransactions != null){
            return recenetTransactions.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtDate,txtAmount,txtTransfer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtAmount=itemView.findViewById(R.id.txtAmount);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtTransfer=itemView.findViewById(R.id.txtTransfer);
        }
    }
}
