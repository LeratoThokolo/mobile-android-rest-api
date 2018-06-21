package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.ApplicationHeaders;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateOrderActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutDelivered;
    private TextInputLayout textInputLayoutDriverID;
    private RequestQueue requestQueueUpdateOrder;
    private Button buttonUpdateOrder;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        getSupportActionBar().setTitle("Change order status");

        this.requestQueueUpdateOrder = Volley.newRequestQueue(this);
        Gson gson  = new Gson();

        Intent intent = getIntent();

        this.order = gson.fromJson(intent.getStringExtra("order"), Order.class);

        this.initializeGadgets();
        this.buttonUpdateOrder.setOnClickListener(updateOrderStatus());
    }


    private void initializeGadgets(){

        this.textInputLayoutDelivered = findViewById(R.id.textInputDelivered);
        this.textInputLayoutDelivered.getEditText().setText(this.order.getDelivered());


        this.textInputLayoutDriverID = findViewById(R.id.textInputDriverID);
        this.textInputLayoutDriverID.getEditText().setText(String.valueOf(this.order.getDriverID()));
        this.buttonUpdateOrder = findViewById(R.id.buttonUpdateOrder);

    }

    private boolean validateDriverID(){

        if( this.textInputLayoutDriverID.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutDriverID.setError("Driver ID is required!!");
            return false;

        }else{

            this.textInputLayoutDriverID.setError(null);

            return true;
        }
    }

    private boolean validateDelivered(){

        if(this.textInputLayoutDelivered.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutDelivered.setError("Delivered is required!!");
            return  false;
        }
        else{

            this.textInputLayoutDelivered.setError(null);
            return true;
        }
    }

    private void updateOrderLogic() {

        if(!this.validateDelivered() | !this.validateDriverID()){

            return;
        }
        else{

            String url = "http://10.0.2.2:8080/order/update-order";
            String delivered = this.textInputLayoutDelivered.getEditText().getText().toString();
            int driverID = Integer.parseInt(this.textInputLayoutDriverID.getEditText().getText().toString());

            Map<String, String> params = new HashMap<String, String>();
            params.put("orderID", String.valueOf(this.order.getOrderID()));
            params.put("delivered", delivered);
            params.put("driverID", String.valueOf(driverID));
            params.put("orderNumber", String.valueOf(this.order.getOrderNumber()));
            params.put("area", this.order.getArea());
            params.put("houseNo", this.order.getHouseNo());
            params.put("fullNames", this.order.getFullNames());
           // params.put("dateCreated", String.valueOf(this.order.getDateCreated()));
            params.put("amount", String.valueOf(this.order.getAmount()));
            params.put("userID", String.valueOf(this.order.getUserID()));


            JSONObject jsonObjectUpdate = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonObjectUpdate,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();
                            Order order1 = gson.fromJson(String.valueOf(response), Order.class);

                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateOrderActivity.this)
                                    .setTitle("Server response")
                                    .setMessage("Order # " + order1.getOrderNumber() + " updated successfully!!")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(UpdateOrderActivity.this, DriverActivity.class));
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(ApplicationHeaders.HEADERS, ApplicationHeaders.HEADERSVALUE);

                    return headers;
                }
            };

            this.requestQueueUpdateOrder.add(jsonObjectRequest);

        }
    }

    private View.OnClickListener updateOrderStatus() {

        View.OnClickListener update = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateOrderLogic();
            }
        };

        return update;
    }

}
