package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.thokolo.lerato.draweractivitywithfragments.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Two extends Fragment {

    private Button buttonViewAllCategories;
    private Button buttonAddCategory;


    public Fragment_Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__two, container, false);
        this.buttonViewAllCategories = view.findViewById(R.id.buttonAllCategories);
        this.buttonViewAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminCategoriesActivity.class));
            }
        });

        this.buttonAddCategory = view.findViewById(R.id.buttonAddCategories);
        this.buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminAddCategoriesActivity.class));
            }
        });

        return view;
    }

}
