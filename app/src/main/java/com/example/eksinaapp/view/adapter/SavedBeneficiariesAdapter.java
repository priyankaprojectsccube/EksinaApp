package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Beneficiary;
import com.example.eksinaapp.view.activity.BenefiriesDetails;

import java.util.List;

public class SavedBeneficiariesAdapter extends RecyclerView.Adapter<SavedBeneficiariesAdapter.ViewHolder> {
    private List<Beneficiary> beneficiaries;
    private Context mContext;

    public SavedBeneficiariesAdapter(List<Beneficiary> beneficiaries, Context mContext) {
        this.beneficiaries = beneficiaries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_savedbeneficiaries, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(beneficiaries.get(position).getFirstName() + " " +  beneficiaries.get(position).getLastName());

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), BenefiriesDetails.class);
                intent.putExtra( "ben_id",beneficiaries.get(position).getId());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(beneficiaries != null){
            return beneficiaries.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtName);
        }
    }

}
