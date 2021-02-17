package com.example.app_bancaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    TextView txt1,monto,numerocuenta;
    CardView consignarbutton, retirarbutton, enviarbutton;
    LinearLayout cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txt1=findViewById(R.id.txt1);
        monto=findViewById(R.id.monto);
        Bundle campos = getIntent().getExtras();
        txt1.setText("Bienvenido "+ campos.getString("nombre"));
        monto.setText("$ "+campos.getDouble("monto"));
        numerocuenta = findViewById(R.id.numerocuenta);
        numerocuenta.setText("Cuenta #"+campos.getString("cuenta"));
        consignarbutton = findViewById(R.id.card1);
        consignarbutton.setOnClickListener(view -> {
            Intent a = new Intent(Menu.this, Consignar.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        enviarbutton = findViewById(R.id.card2);
        enviarbutton.setOnClickListener(view -> {
            Intent a = new Intent(Menu.this,Enviar.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        retirarbutton = findViewById(R.id.card3);
        retirarbutton.setOnClickListener(view -> {
            Intent a = new Intent(Menu.this,Retirar.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        cerrarsesion = findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cierre de sesión");
            builder.setMessage("¿Desea cerrar sesión?");
            builder.setPositiveButton(Html.fromHtml("<font color=#FFFFFF>Si</font>"),(dialogInterface, i) -> {
                Intent a = new Intent(Menu.this, MainActivity.class);
                startActivity(a);
                finish();
            });
            builder.setNegativeButton(Html.fromHtml("<font color=#FFFFFF>No</font>"),null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}