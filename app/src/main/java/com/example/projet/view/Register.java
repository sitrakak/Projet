package com.example.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projet.R;
import com.example.projet.controller.UserController;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        UserController controle = UserController.getInstance();
        init();
    }

    private void init(){
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        password=(EditText) findViewById(R.id.mdp);
        bouttonlistner();
    }

    private void bouttonlistner(){
        ((Button)findViewById(R.id.Inscription)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JSONObject object = new JSONObject();

                try {
                    String nom=name.getText().toString();
                    String mail=email.getText().toString();
                    String numero=phone.getText().toString();
                    String mdp=password.getText().toString();
                    if (nom.isEmpty() || mail.isEmpty() || numero.isEmpty() || mdp.isEmpty()) {
                        Toast.makeText(Register.this,"saisie incorect",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        object.put("name", nom);
                        object.put("email", mail);
                        object.put("phone", numero);
                        object.put("password", mdp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Enter the correct url for your api service site
                String url = "https://s-learning.herokuapp.com/api/v1/users/register";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response : ", response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response : ", error.toString());
                    }
                });
                if(object.length()==4) {
                    requestQueue.add(jsonObjectRequest);
                    Toast.makeText(Register.this,"inscription reussi",Toast.LENGTH_SHORT).show();
                    Intent redirect=new Intent(Register.this, Login.class);
                    startActivity(redirect);
                }
                else{
                    Toast.makeText(Register.this,"saisie incorect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
