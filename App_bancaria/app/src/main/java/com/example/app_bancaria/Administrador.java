package com.example.app_bancaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends AppCompatActivity {

    ListView lista;
    LinearLayout cerrarsesion;
    List<String> listausuarios = new ArrayList<>();
    ArrayAdapter<String> usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        lista = findViewById(R.id.lv1);
        Model m = new Model();
        Cursor usuarios = m.usuarios(this);
        while(usuarios.moveToNext()) {
            /*"nombre"))+" "+usuarios.getString(usuarios.getColumnIndex("apellido"))+" | "+*/
            listausuarios.add(usuarios.getString(usuarios.getColumnIndex("correo")));
            usuario = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    listausuarios);
            lista.setAdapter(usuario);
        }
        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            String correo22 = lista.getAdapter().getItem(i).toString().trim();
            Model m2 = new Model();
            if(m2.verestado(Administrador.this,correo22)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("El usuario está habilitado");
                builder.setMessage("¿Desea deshabilitarlo?");
                builder.setPositiveButton(Html.fromHtml("<font color=#FFFFFF>Si</font>"),(dialogInterface, i1) -> {
                   m2.deshabilitarusuario(Administrador.this,correo22);
                    Toast.makeText(this, "Usuario deshabilitado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("El usuario está deshabilitado");
                builder.setMessage("¿Desea habilitarlo?");
                builder.setPositiveButton(Html.fromHtml("<font color=#FFFFFF>Si</font>"),(dialogInterface, i1) -> {
                    m2.habilitarusuario(Administrador.this,correo22);
                    Toast.makeText(this, "Usuario habilitado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        cerrarsesion = findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cierre de sesión");
            builder.setMessage("¿Desea cerrar sesión como administrador?");
            builder.setPositiveButton(Html.fromHtml("<font color=#FFFFFF>Si</font>"),(dialogInterface, i) -> {
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);
                finish();
            });
            builder.setNegativeButton(Html.fromHtml("<font color=#FFFFFF>No</font>"),null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}