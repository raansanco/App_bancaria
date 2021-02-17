package com.example.app_bancaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txt2;
    EditText correo,contrasena;
    Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        ingresar = findViewById(R.id.ingresar);
        ingresar.setOnClickListener(view -> {
            boolean v= true;
            if(correo.getText().toString().matches("")){
                correo.setError("Campo vacio");
                Toast.makeText(this, "Complete todos los campos de ingreso", Toast.LENGTH_SHORT).show();
                v=false;
            }
            if(contrasena.getText().toString().matches("")){
                contrasena.setError("Campo vacio");
                Toast.makeText(this, "Complete todos los campos de ingreso", Toast.LENGTH_SHORT).show();
                v=false;
            }
            if(v){
                if(!(correo.getText().toString().equals("admin@wposs.com") && contrasena.getText().toString().equals("Admin123*"))) {
                        Model m = new Model();
                        Bundle campos;
                        campos = m.readlogin2(MainActivity.this, correo.getText().toString(), contrasena.getText().toString());
                        if (campos.getBoolean("rsql")) {
                            if(campos.getString("estado").equals("habilitado")) {
                                Toast.makeText(this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                                Intent a = new Intent(MainActivity.this, Menu.class);
                                a.putExtras(campos);
                                startActivity(a);
                                finish();
                            }else{
                                Toast.makeText(this, "El usuario se encuentra deshabilitado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Intent a = new Intent(MainActivity.this,Administrador.class);
                    startActivity(a);
                    finish();
                }
            }
        });
        txt2 = findViewById(R.id.txt2);
        txt2.setOnClickListener(view -> {
            Intent a = new Intent(MainActivity.this,Registro.class);
            startActivity(a);
        });
    }
}