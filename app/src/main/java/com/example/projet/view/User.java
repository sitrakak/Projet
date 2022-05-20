package com.example.projet.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projet.R;
import com.example.projet.controller.UserController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User extends AppCompatActivity {
    private UserController controle;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cours);
        this.controle= UserController.getInstance();
        SharedPreferences sh = getSharedPreferences("Autorisation", MODE_PRIVATE);

        String user = sh.getString("user", "");
        String token = sh.getString("token", "");
        getUser(token);
    }
    public void getUser(String token){
        String URL="https://s-learning.herokuapp.com/api/v1/users/";

        RequestQueue queue = Volley.newRequestQueue(User.this);
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("data");
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject users=jsonArray.getJSONObject(i);
                                        Log.d("email: ",users.getString("email"));
                                    }
                                    Log.d("message:",response.toString());
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response : ", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization","Bearer "+ token);
                        return headers;
                    }
                };
        queue.add(jsonObjectRequest);
    }
}
