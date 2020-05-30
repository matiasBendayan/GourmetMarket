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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VentaObjetos extends AppCompatActivity {
    ListView listView;
    String mTitle[];

    int mImgs[] = {R.drawable.comida, R.drawable.miel, R.drawable.aderesos, R.drawable.cookies, R.drawable.comida, R.drawable.comida};
    int mprecios[];
    List<Ariculos> listaArticulos = new ArrayList<>();
    List<String> categorias = new ArrayList();
    List<String> nombresFiltrados = new ArrayList<>();
    List<Double> preciosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_venta_objetos );
        int dato = getIntent().getIntExtra( "posicion", 0 );
        categorias = getIntent().getStringArrayListExtra( "categorias" );
        listaArticulos = (List<Ariculos>) getIntent().getSerializableExtra( "articulos" );
        obtenerArticulos( categorias.toArray()[dato].toString() );
        mTitle = new String[nombresFiltrados.size()];
        mprecios = new int[nombresFiltrados.size()];
        llenarmprecios();
        llenarmtitle();
        listView = findViewById( R.id.listObjetos );
        VentaObjetos.mYAdapter adapter = new VentaObjetos.mYAdapter( getApplicationContext(), mTitle, mImgs, mprecios );
        listView.setAdapter( adapter );
    }

    class mYAdapter extends ArrayAdapter<String> {
        String rTitle[];
        int rimg[];
        int rprecio[];

        mYAdapter(Context c, String title[], int imgs[], int precios[]) {
            super( c, R.layout.fila2, R.id.textView1, title );
            this.rTitle = title;
            this.rimg = imgs;
            this.rprecio = precios;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View fila = layoutInflater.inflate( R.layout.fila2, parent, false );
            TextView title = fila.findViewById( R.id.textView1 );
            TextView precio = fila.findViewById( R.id.precio );
            ImageView images = fila.findViewById( R.id.imagenes );
            images.setImageResource( rimg[position] );
            title.setText( rTitle[position] );
            String precio2 = String.valueOf( rprecio[position] );
            precio.setText( "$ " + precio2 );
            listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent i = new Intent( getApplicationContext(), VentaUnitaria.class );
                    i.putExtra( "precio", rprecio[position] );
                    i.putExtra( "imagen", rimg[position] );
                    i.putExtra( "titulo", rTitle[position] );
                    startActivity( i );
                }
            } );
            return fila;
        }
    }

    void obtenerArticulos(String categoria) {
        for (Ariculos art : listaArticulos) {
            if (art.getCategoria().equals( categoria )) {
                nombresFiltrados.add( art.getDescripcion() );
                preciosFiltrados.add( art.getPrecio() );
            }
        }
    }

    private void llenarmtitle() {
        int i = 0;
        for (String cat : nombresFiltrados) {
            mTitle[i] = cat;
            i++;
        }
    }

    private void llenarmprecios() {
        int i = 0;
        for (Double precio : preciosFiltrados) {
            mprecios[i] = precio.intValue();
            i++;
        }
    }
}
