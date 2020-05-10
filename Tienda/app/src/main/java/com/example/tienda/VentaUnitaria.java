package com.example.tienda;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VentaUnitaria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_venta_unitaria );
        final String titulo = getIntent().getStringExtra("titulo");
        final int precio = getIntent().getIntExtra("precio",0);
        int imagen = getIntent().getIntExtra("imagen",0);
        TextView title = findViewById( R.id.textView1);
        TextView txtprecio = findViewById( R.id.precio);
        ImageView images = findViewById( R.id.imagenes );
        images.setImageResource(imagen);
        title.setText( titulo);
        String precio2 = String.valueOf( precio);
        txtprecio.setText("$ " + precio2);
        EditText edtCantidad = findViewById(R.id.Cantidad);
        final int cantidad = Integer.valueOf(edtCantidad.getText().toString());
        Button btnAgregar = findViewById( R.id.agregar);
        btnAgregar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar(titulo,precio,cantidad);
            }
        } );
    }
    public void agregar(String titulo,int precio,int cantidad){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues NuevoCarrito = new ContentValues();
        NuevoCarrito.put("nombre", titulo);
        NuevoCarrito.put("precio", precio);
        NuevoCarrito.put("cantidad", cantidad);
        BaseDeDatos.insert("carrito", null, NuevoCarrito);
        BaseDeDatos.close();Toast.makeText(this,"Se a agregado correctamente al carrito", Toast.LENGTH_SHORT).show();
        }
    }
