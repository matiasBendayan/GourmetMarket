package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class VentaUnitaria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_venta_unitaria );
        String titulo = getIntent().getStringExtra("titulo");
        int precio = getIntent().getIntExtra("precio",0);
        int imagen = getIntent().getIntExtra("imagen",0);
    }
}
