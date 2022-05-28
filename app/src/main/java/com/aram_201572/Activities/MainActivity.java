package com.aram_201572.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aram_201572.R;
import com.aram_201572.Utils.SharedPrefsHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnFirebase,btnSqlite,btnWeather;
    TextView txtWeather;

    @Override
    protected void onResume() {
        super.onResume();
        String city = SharedPrefsHelper.getCity(MainActivity.this);
        getWeather("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=76b4f5fc92f432d9fc68dffc349aa1cf&units=metric");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFirebase = findViewById(R.id.btnFirebase);
        btnWeather = findViewById(R.id.btnWeather);
        btnSqlite = findViewById(R.id.btnSqlite);
        txtWeather= findViewById(R.id.txtWeather);

        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirebaseActivity.class);
                startActivity(intent);
            }
        });

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        btnSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SqliteActivity.class);
                startActivity(intent);
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