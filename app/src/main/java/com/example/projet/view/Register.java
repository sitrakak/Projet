package com.example.projet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.controller.UserController;

public class Register extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        bouttonlistner();
        this.controle= UserController.getInstance();
    }

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private UserController controle;

    private void init(){
        /*name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        password=(EditText) findViewById(R.id.mdp);*/
        bouttonlistner();
    }

    private void bouttonlistner(){
        ((Button)findViewById(R.id.Inscription)).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent redirect=new Intent(Register.this, Login.class);
                startActivity(redirect);
            }
        });
    }
}
