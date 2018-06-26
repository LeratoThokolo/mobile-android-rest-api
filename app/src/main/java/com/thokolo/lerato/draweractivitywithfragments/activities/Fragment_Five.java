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
public class Fragment_Five extends Fragment {

    private Button buttonAllOrders;


    public Fragment_Five() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_five, container, false);

        this.buttonAllOrders = view.findViewById(R.id.buttonAllViewAllOrders);
        this.buttonAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminOrdersActivity.class));
            }
        });

        return view;
    }

}
