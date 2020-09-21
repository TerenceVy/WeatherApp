package com.example.basicweatherapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Adapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CitiesList extends Fragment {
    private SharedViewModel viewModel;
    private TextView errorText2;


    private List<String> myList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);

        errorText2 = view.findViewById(R.id.text_error_info2);
        final ListView citiesList = view.findViewById(R.id.list_cities);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object cityItem = citiesList.getItemAtPosition(position);
                errorText2.setText(cityItem.toString());
                errorText2.setTextColor(Color.parseColor("#3E971E"));
                errorText2.setVisibility(View.VISIBLE);
                viewModel.setText(cityItem.toString());
            }
        });

       SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

       Map<String,?> keys = sharedPreferences.getAll(); // Récupération du shared preferences
         for(Map.Entry<String,?> entry : keys.entrySet()){
            myList.add(entry.getValue().toString()) ;
        }
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, myList);

        citiesList.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }
}