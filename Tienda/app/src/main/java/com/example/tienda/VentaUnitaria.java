package com.example.tienda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VentaUnitaria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_venta_unitaria );
        String titulo = getIntent().getStringExtra("titulo");
        int precio = getIntent().getIntExtra("precio",0);
        int imagen = getIntent().getIntExtra("imagen",0);
        VentaUnitaria.mYAdapter adapter = new VentaUnitaria.mYAdapter( this,titulo,imagen,precio);
    }
    class mYAdapter extends ArrayAdapter<String> {
        String rTitle;
        int  rimg;
        int  rprecio;
        mYAdapter(Context c,String titulo,int imagen,int precio){
            super(c,R.layout.fila2,R.id.textView1);
            this.rTitle = titulo;
            this.rimg = imagen;
            this.rprecio = precio;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View fila = layoutInflater.inflate( R.layout.fila2,parent,false );
            TextView title = fila.findViewById( R.id.textView1);
            TextView precio = fila.findViewById( R.id.precio);
            ImageView images = fila.findViewById( R.id.imagenes );
            images.setImageResource(rimg);
            title.setText( rTitle);
            String precio2 = String.valueOf( rprecio);
            precio.setText( precio2);
            return fila;
        }
    }
}
