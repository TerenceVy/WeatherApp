package com.example.basicweatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CitiesList extends Fragment implements OnClickListener{
    private SharedViewModel viewModel;
    private TextView errorText2;
    private List<String> myList = new ArrayList<>();
    private Object cityItem;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        Button displayButton = view.findViewById(R.id.button_displayCity);
        displayButton.setOnClickListener(this);
        Button deleteButton = view.findViewById(R.id.button_deleteCity);
        deleteButton.setOnClickListener(this);

        errorText2 = view.findViewById(R.id.text_error_info2);
        final ListView citiesList = view.findViewById(R.id.list_cities);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                cityItem = citiesList.getItemAtPosition(position);
                errorText2.setText(cityItem.toString());
                errorText2.setTextColor(Color.parseColor("#3E971E"));
                errorText2.setVisibility(View.VISIBLE);
            }
        });

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Map<String,?> keys = sharedPreferences.getAll(); // Récupération du shared preferences
        for(Map.Entry<String,?> entry : keys.entrySet()) {
            myList.add(entry.getValue().toString()) ;
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myList);

        citiesList.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_displayCity:
                viewModel.setText(cityItem.toString());
                //errorText2.setText(String.format("La météo de %s est affichée dans 'Météo'", cityItem.toString()));
                Toast.makeText(getActivity(), "La météo de " + cityItem.toString() + " est affichée dans 'Météo'", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_deleteCity:
                errorText2.setText("");
                errorText2.setVisibility(View.INVISIBLE);
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(cityItem.toString()).apply();

                myList.remove(cityItem.toString());
                adapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "(" + cityItem + ") a bien été supprimé", Toast.LENGTH_LONG).show();


                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }
}