package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private ArrayList<Category> categories;
    private Context categoryContext;
    private TextView textCategoryName;

    public CategoryAdapter(ArrayList<Category> categories, Context categoryContext) {
        this.categories = categories;
        this.categoryContext = categoryContext;
    }

    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Category getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categories.get(position).getCategoryID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(categoryContext, R.layout.category_custom, null);

        this.textCategoryName = convertView.findViewById(R.id.textViewCategoryName);

        this.textCategoryName.setText(this.categories.get(position).getName());


        return convertView;
    }
}
