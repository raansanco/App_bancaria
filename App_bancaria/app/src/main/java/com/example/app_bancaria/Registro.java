package com.example.app_bancaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText nombre,apellido,identificacion,correo,contrasena;
    TextView numcuenta1,regresar;
    Button registrarse;
    String numcuenta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        identificacion = findViewById(R.id.identificacion);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        numcuenta1 = findViewById(R.id.numcuenta);
        generarnumcuenta();
        regresar = findViewById(R.id.regresar);
        regresar.setOnClickListener(view -> {
            Intent a = new Intent(this,Menu.class);
            startActivity(a);
            finish();
        });
        registrarse = findViewById(R.id.registrarse);
        registrarse.setOnClickListener(view -> {
            if(valcampos()){
                Model m = new Model();
                Campos c = new Campos();
                c.setNombre(nombre.getText().toString());
                c.setApellido(apellido.getText().toString());
                c.setIdentificacion(identificacion.getText().toString());
                c.setCorreo(correo.getText().toString());
                c.setContrasena(contrasena.getText().toString());
                c.setCuenta(numcuenta);
                c.setMonto(1000000);
                c.setEstado("habilitado");
                m.insert(Registro.this,c);
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(Registro.this, MainActivity.class);
                startActivity(a);
                finish();
            }
        });

    }

    void generarnumcuenta(){
        boolean e = true;
        while (e){
            if(numcuenta.length()<9){
                numcuenta =String.valueOf((int)(Math.random()*1000000000)+1);
            }else{
                numcuenta=numcuenta+String.valueOf((int)(Math.random()*9)+1);
                numcuenta1.setText(numcuenta1.getText()+" "+numcuenta);
                e=false;
            }
        }
    }

    boolean valcampos(){
        boolean valc=true;
        if(nombre.getText().toString().matches("")){
            nombre.setError("Campo vacío");
            valc=false;
        }
        if(apellido.getText().toString().matches("")){
            apellido.setError("Campo vacío");
            valc=false;
        }
        if(identificacion.getText().toString().matches("")){
            identificacion.setError("Campo vacío");
            valc=false;
        }
        if(correo.getText().toString().matches("")){
            correo.setError("Campo vacío");
            valc=false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString().trim()).matches()){
            valc=false;
            correo.setError("Correo inválido");
        }
        if(contrasena.getText().toString().matches("")){
            contrasena.setError("Campo vacío");
            valc=false;
        }else if(!valcontrasena()){
            contrasena.setError("La contraseña debe contener numeros y letras");
            valc=false;
        }
        if (valc){
            Toast.makeText(this, "Campos completados correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debe completar correctamente todo los campos", Toast.LENGTH_SHORT).show();
        }
        return valc;
    }

    boolean valcontrasena(){
        boolean v=false,v1=false,v2=false;
        if(Pattern.compile("[0-9]").matcher(contrasena.getText().toString()).find())
            v1=true;
        if(Pattern.compile("[a-z]").matcher(contrasena.getText().toString()).find())
            v2=true;
        if(v1 && v2)
            v=true;
        return v;
    }
}