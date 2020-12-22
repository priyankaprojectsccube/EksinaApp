package com.example.eksinaapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Beneficiary;
import com.example.eksinaapp.model.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConvertCurrencyAdapter extends BaseAdapter {
    private List<Country> countryList=new ArrayList<>();
    Context context;

    public ConvertCurrencyAdapter(List<Country> countryList, Context context) {
        this.countryList = countryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(countryList != null){
            return countryList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.item_convert_currency,parent,false);
        }
        ImageView imageView;
        TextView textView,txtError;
        imageView=convertView.findViewById(R.id.imageView);
        textView=convertView.findViewById(R.id.textView);


        final Country country= countryList.get(position);
        //final int subCategoryId= Integer.parseInt(countryList.get(position).get());
        if (country.getCountryName().isEmpty()){
            textView.setText("Select Country");
        }else {
            textView.setText(country.getCountryName());
        }
        if(country.getFlag() != null && country.getFlag().length()>0)
        {
            Picasso.get().load(country.getFlag()).placeholder(R.drawable.france).into(imageView);
        }else {
            //Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show();
            Picasso.get().load(R.drawable.france).into(imageView);
        }

        return convertView;
    }
    }

