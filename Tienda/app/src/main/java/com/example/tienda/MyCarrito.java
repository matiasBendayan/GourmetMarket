package com.example.tienda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyCarrito extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_carrito );
        List<Carrito> carritos;
        carritos = this.GetAll();
        listView = findViewById( R.id.ListViewmyCarrito );
        mYAdapter adapter = new mYAdapter( getApplicationContext(), carritos);
        listView.setAdapter( adapter );
        String total = this.getTotal(carritos);
        TextView txvot = findViewById( R.id.total );
        txvot.setText( "El total es de $ " + total );
        Button comprar = findViewById( R.id.btnComprar );
        comprar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( MyCarrito.this, "Funcionaaa", Toast.LENGTH_LONG ).show();
            }
        } );
    }

    private String getTotal(List<Carrito> carritos) {
        int total = 0;
        for(Carrito carr: carritos){
            total+=carr.getPrecio();
        }
        return String.valueOf( total );
    }

    class mYAdapter extends ArrayAdapter<Carrito> {
        List<Carrito> carritos =  new ArrayList<>();
        mYAdapter(Context c, List<Carrito> carritos2) {
            super( c, R.layout.filamycarrito,R.id.textView1,carritos2);
            for (Carrito s : carritos2){
                carritos.add( s );}
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View fila = layoutInflater.inflate( R.layout.filamycarrito, parent, false );
            TextView title = fila.findViewById( R.id.textView2 );
            TextView precio = fila.findViewById( R.id.textView3 );
            title.setText( this.carritos.get( position ).getDescripciones() + "(x"+this.carritos.get( position ).getCantidad()+")" );
            precio.setText("$ " + String.valueOf( this.carritos.get( position ).getPrecio() ) );
            return fila;
        }
    }

    public List<Carrito> GetAll() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper( this, "administracion", null, 1 );
        SQLiteDatabase BaseDeDatabase = admin.getReadableDatabase();
        List<Carrito> carritos = new ArrayList<>();
        Carrito carrito = new Carrito();
        Cursor fila = BaseDeDatabase.rawQuery( "select * from carrito ", null );
        fila.moveToFirst();
        do {
            carrito.setId( fila.getInt( 0 ) );
            carrito.setDescripciones( fila.getString( 1 ) );
            carrito.setPrecio( fila.getInt( 2 ) );
            carrito.setCantidad( fila.getInt( 3 ) );
            carritos.add( carrito );
            fila.moveToNext();
        } while (fila.isLast());
        fila.moveToFirst();
        BaseDeDatabase.close();
        return carritos;
    }
}

class Carrito {
    int id;
    String descripciones;
    int precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripciones() {
        return descripciones;
    }

    public void setDescripciones(String descripciones) {
        this.descripciones = descripciones;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    int cantidad;

}