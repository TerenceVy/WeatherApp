package com.example.basicweatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basicweatherapp.Retrofit.WeatherApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class Search extends Fragment implements OnClickListener {
    private SharedViewModel viewModel;
    private EditText editText;
    private TextView errorText;
    private static WeatherApi WeatherApi;
    private String cityName;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Connexion to the API
         */
        this.configureRetrofit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button button = view.findViewById(R.id.button_search);
        this.editText = view.findViewById(R.id.editText_search);
        this.errorText = view.findViewById(R.id.text_error_info);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public void onClick(View view) {
        errorText.setVisibility(View.INVISIBLE);
        cityName = this.editText.getText().toString();
        if (!cityName.matches("")) {
            WeatherApi.getCurrentWeather("metric", cityName).enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.code() != 200) {
                        errorText.setText("(" + cityName + ") n'existe pas ");
                        errorText.setTextColor(Color.parseColor("#CD1C1C"));
                        errorText.setVisibility(View.VISIBLE);
                    }
                    else {
                        viewModel.setText(editText.getText());
                        errorText.setText("La météo pour " + cityName + " est maintenant affichée dans (Météo)");
                        errorText.setTextColor(Color.parseColor("#3E971E"));
                        errorText.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
        else {
            errorText.setText("La barre de recherche est vide");
            errorText.setTextColor(Color.parseColor("#CD1C1C"));
            errorText.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Retrofit's configuration
     */
    private void configureRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://community-open-weather-map.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WeatherApi = retrofit.create(WeatherApi.class);

    }

}