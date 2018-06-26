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
public class Fragment_Four extends Fragment {

    private Button buttonAllSuppliers;
    private Button buttonAddSupplier;

    public Fragment_Four() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_four, container, false);

        this.buttonAllSuppliers = view.findViewById(R.id.buttonAllSuppliers);
        this.buttonAddSupplier = view.findViewById(R.id.buttonAddSupplier);

        this.buttonAllSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminSuppliersActivity.class));
            }
        });

        this.buttonAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminAddSupplierActivity.class));
            }
        });

        return view;
    }

}
