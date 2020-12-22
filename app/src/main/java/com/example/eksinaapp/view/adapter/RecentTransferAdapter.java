package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Beneficiary;
import com.example.eksinaapp.model.RecentTransfer;
import com.example.eksinaapp.model.Transaction;

import java.util.List;

public class RecentTransferAdapter extends RecyclerView.Adapter<RecentTransferAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context mContext;

    public RecentTransferAdapter(List<Transaction> transactions, Context mContext) {
        this.transactions = transactions;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_recent_transfer, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(transactions.get(position).getFirstName() + " " +  transactions.get(position).getLastName());
        holder.txtDate.setText(transactions.get(position).getPayDate());
        holder.txtAmount.setText(transactions.get(position).getPaidAmount());
        holder.txtTransfer.setText(transactions.get(position).getPaymentMethod());
    }

    @Override
    public int getItemCount() {
        if (transactions!=null){
            return transactions.size();
        }else {
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
