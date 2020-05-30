package com.example.tienda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class Catalogo extends Fragment {
    ListView listView;
    int mImgs[] = {R.drawable.comida, R.drawable.miel, R.drawable.aderesos, R.drawable.cookies, R.drawable.comida, R.drawable.comida};


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        leerArticulos();
        String mTitle[] = new String[categoriasdistintas.size()];
        int i = 0;
        for (String cat : categoriasdistintas){
            mTitle[i] = cat;
            i++;
        }
        listView = getView().findViewById( R.id.ListView );
        mYAdapter adapter = new mYAdapter( getActivity().getApplicationContext(), mTitle, mImgs );
        listView.setAdapter( adapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent( getView().getContext().getApplicationContext(), VentaObjetos.class );
                i.putExtra( "posicion", position );
                startActivity( i );
            }
        } );
    }

    private List<Ariculos> listadearticulos = new ArrayList<>();
    private List<String> categoriasdistintas = new ArrayList<>();

    private void leerArticulos() {
        InputStream is = getResources().openRawResource( R.raw.data );
        BufferedReader reader = new BufferedReader( new InputStreamReader( is, Charset.forName( "UTF-8" ) )
        );
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split( ";" );
                Ariculos ariculo = new Ariculos();
                ariculo.setCategoria( tokens[0] );
                ariculo.setDescripcion( tokens[1] );
                ariculo.setPrecio( Double.valueOf( tokens[2] ) );
                listadearticulos.add( ariculo );
                agregarAcategorias(tokens[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregarAcategorias(String token) {
        Boolean repetido = false;
        for(String cat : categoriasdistintas){
            if (cat.equals(token)) {
                repetido = true;
            }
        }
        if (!repetido){
            categoriasdistintas.add( token );
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_catalogo, container, false );
    }

    class mYAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        int rimg[];

        mYAdapter(Context c, String title[], int imgs[]) {
            super( c, R.layout.fila, R.id.textView1, title );
            this.rTitle = title;
            this.rimg = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View fila = layoutInflater.inflate( R.layout.fila, parent, false );
            TextView title = fila.findViewById( R.id.textView1 );
            ImageView images = fila.findViewById( R.id.imagenes );
            images.setImageResource( rimg[position] );
            title.setText( rTitle[position] );
            return fila;

        }
    }
}

