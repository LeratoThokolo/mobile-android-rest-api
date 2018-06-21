package com.thokolo.lerato.draweractivitywithfragments.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thokolo.lerato.draweractivitywithfragments.R;

public class CustomErrorToast {

    public static void createErrorToast(Context context, String text){

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_error_toast, null);
        toast.setView(view);
        TextView textView = view.findViewById(R.id.toast_custom_error);
        textView.setText(text + "...");
        toast.show();
    }
}
