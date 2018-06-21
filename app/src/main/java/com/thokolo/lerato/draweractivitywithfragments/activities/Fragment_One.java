package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.thokolo.lerato.draweractivitywithfragments.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_One extends Fragment {

    private Button buttonAllProducts;
    private Button buttonAddProduct;


    public Fragment_One() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__one, container, false);
        this.buttonAllProducts = view.findViewById(R.id.buttonAllProducts);
        this.buttonAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminProductsActivity.class));
            }
        });

        this.buttonAddProduct = view.findViewById(R.id.buttonAddProducts);
        this.buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminAddProductActivity.class));
            }
        });



        return view;
    }
}
