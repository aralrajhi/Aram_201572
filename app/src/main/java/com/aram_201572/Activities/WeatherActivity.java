package com.aram_201572.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aram_201572.R;
import com.aram_201572.Utils.SharedPrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class WeatherActivity extends AppCompatActivity {

    EditText edtxtCity;
    Button btnSaveCity;
    TextView txtWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        edtxtCity= findViewById(R.id.edtxtCity);
        btnSaveCity= findViewById(R.id.btnSaveCity);
        txtWeather= findViewById(R.id.txtWeather);

        String city = SharedPrefsHelper.getCity(WeatherActivity.this);
        edtxtCity.setText(city);
        getWeather("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=76b4f5fc92f432d9fc68dffc349aa1cf&units=metric");

        btnSaveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = edtxtCity.getText().toString();
                if(!city.equalsIgnoreCase("")){
                    SharedPrefsHelper.setCity(WeatherActivity.this, city);
                    getWeather("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=76b4f5fc92f432d9fc68dffc349aa1cf&units=metric");
                }
                else{
                    Toasty.error(WeatherActivity.this, "please enter a valid value", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    public void getWeather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject jsonMain = response.getJSONObject("main");
                double temp = jsonMain.getDouble("temp");
                double humid = jsonMain.getDouble("humidity");
                txtWeather.setText("Temperature: "+String.valueOf(temp)+"°C\nHumidity: "+String.valueOf(humid));

            }
            catch (JSONException e){
                e.printStackTrace();

            }
        }, error -> Log.d("Student", error.getMessage()));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}