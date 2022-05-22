package com.example.projet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Inscriptionlistner();
        init();
        this.controle=UserController.getInstance();
    }

    private EditText email;
    private EditText password;
    private UserController controle;

    /**
     * Initialisation des liens avec les objets graphique
     */
    private void init(){
        email=(EditText) findViewById(R.id.emailLog);
        password=(EditText) findViewById(R.id.mdpLog);
        bouttonlistner();
    }

    private void bouttonlistner(){
        ((Button)findViewById(R.id.Connexion)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JSONObject object = new JSONObject();
                try {
                    String mail=email.getText().toString();
                    String mdp=password.getText().toString();
                    if (mail.isEmpty() || mdp.isEmpty()) {
                        Toast.makeText(Login.this,"saisie incorect",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        object.put("email", mail);
                        object.put("password", mdp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Enter the correct url for your api service site
                String url = "https://s-learning.herokuapp.com/api/v1/users/login";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response : ", response.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences("Autorisation", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                try {
                                    myEdit.putString("token", response.getString("token"));
                                    myEdit.putString("user", response.getString("user"));
                                    myEdit.putString("idUser", response.getString("id"));
                                    myEdit.apply();
                                    if(response.getString("admin")=="true") {
                                        Intent redirect = new Intent(Login.this, User.class);
                                        startActivity(redirect);
                                    }
                                    else{
                                        Intent redirect = new Intent(Login.this, User.class);
                                        //startActivity(redirect);
                                        Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        Log.d("Response : ", error.toString());
                    }
                });
                if(object.length()==2) {
                    requestQueue.add(jsonObjectRequest);
                }
                else{
                    Toast.makeText(Login.this,"saisie incorect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Inscriptionlistner(){
        ((TextView)findViewById(R.id.inscrire)).setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                Intent redirect=new Intent(Login.this, Register.class);
                startActivity(redirect);
                //setContentView(R.layout.register);
            }
        });
    }

}