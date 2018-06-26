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
public class Fragment_Three extends Fragment {

    private Button buttonAllDrivers;
    private Button buttonAddDriver;

    public Fragment_Three() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__three, container, false);

        this.buttonAllDrivers = view.findViewById(R.id.buttonAllDrivers);
        this.buttonAllDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminDriverActivity.class));
            }
        });

        this.buttonAddDriver = view.findViewById(R.id.buttonAddDrivers);
        this.buttonAddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminAddDriverActivity.class));
            }
        });

        return view;
    }

}
