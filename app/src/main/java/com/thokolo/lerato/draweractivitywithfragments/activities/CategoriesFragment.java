package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Category;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private ListView categoriesListView;
    private ArrayList<Category> categories;
    private RequestQueue requestQueue;


    SharedPreferences sharedPreferences;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Categories");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);
        this.requestQueue = Volley.newRequestQueue(view.getContext());


        this.categoriesListView = view.findViewById(R.id.categories_list);

        this.getCategories();

        return view;
    }



    private void getCategories(){


        String baseUrl = "http://10.0.2.2:8080/category";
        this.categories = new ArrayList<>();
        final ArrayList<String> categoryName = new ArrayList<>();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                baseUrl + "/all-categories",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response", response.toString());
                        final Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Category>>(){}.getType();

                        categories = gson.fromJson(String.valueOf(response), type);
                        Log.e("Rest Response", response.toString());

                        for(int i = 0; i < categories.size(); i++){

                            Category category = categories.get(i);

                            Log.e("Category name = ", category.getName());
                            categoryName.add(categories.get(i).getName());

                        }

                       // final ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.custom_list_view, categoryName);
                        final CategoryAdapter categoryAdapter = new CategoryAdapter(categories, getContext());
                        categoriesListView.setAdapter(categoryAdapter);

                        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Category category = categoryAdapter.getItem(position);

                                sharedPreferences = getActivity().getSharedPreferences("CategoryValue", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                Gson gson1 = new Gson();

                                editor.putString("category", gson.toJson(category));
                                editor.apply();

                                Intent intent = new Intent(getActivity(), CategoryItems.class);
                                //intent.putExtra("categoryID", cate);
                                startActivity(intent);

                            }
                        });



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );


        this.requestQueue.add(arrayRequest);


    }
}
