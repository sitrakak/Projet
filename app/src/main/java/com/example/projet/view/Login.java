package com.example.projet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projet.R;
import com.example.projet.controller.UserController;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Inscriptionlistner();
        bouttonlistner();
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
                //Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
                //Log.d("message","click sur le boutton calcul");
                //Toast.makeText(MainActivity.this,"saisie incorrect",Toast.LENGTH_SHORT).show();
                Log.d("message","erreur");
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