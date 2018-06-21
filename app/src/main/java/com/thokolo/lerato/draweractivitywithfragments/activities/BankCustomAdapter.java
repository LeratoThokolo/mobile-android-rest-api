package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Bank;

import java.util.ArrayList;

public class BankCustomAdapter extends BaseAdapter {

    private ArrayList<Bank> banks;
    private Context contextBanks;
    private TextView textViewBankName;

    public BankCustomAdapter(ArrayList<Bank> banks, Context contextBanks) {
        this.banks = banks;
        this.contextBanks = contextBanks;
    }

    @Override
    public int getCount() {
        return banks.size();
    }

    @Override
    public Bank getItem(int position) {
        return banks.get(position);
    }

    @Override
    public long getItemId(int position) {

        return banks.get(position).getBankID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(contextBanks, R.layout.banks_custom_layout, null);

        textViewBankName = convertView.findViewById(R.id.textViewBankName);
        textViewBankName.setText(this.banks.get(position).getBankName());

        return convertView;
    }
}
