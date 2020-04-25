package com.example.tienda;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class Catalogo extends Fragment {
    ListView listView;
    String mTitle[] = {"cosa1","cosa2","Cosa3","Cosa4","Cosa5","Cosa6"};

    int mImgs[] = {R.drawable.comida,R.drawable.miel,R.drawable.aderesos,R.drawable.cookies,R.drawable.comida,R.drawable.comida};


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = getView().findViewById( R.id.ListView );
        mYAdapter adapter = new mYAdapter( getActivity().getApplicationContext(),mTitle,mImgs);
        listView.setAdapter( adapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getView().getContext().getApplicationContext(),VentaObjetos.class);
                i.putExtra("posicion",position);
                startActivity(i);
            }
        } );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_catalogo, container, false );
    }
    class mYAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        int  rimg[];

        mYAdapter(Context c,String title[],int imgs[]){
            super(c,R.layout.fila,R.id.textView1,title);
            this.rTitle = title;
            this.rimg = imgs;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View fila = layoutInflater.inflate( R.layout.fila,parent,false );
            TextView title = fila.findViewById( R.id.textView1);
            ImageView images = fila.findViewById( R.id.imagenes );
            images.setImageResource( rimg[position]);
            title.setText( rTitle[position]);
            return fila;

        }
    }
}

