package com.example.tienda;


import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contacto extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_contacto, container, false );
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText nombre = getView().findViewById( R.id.edit2);
        final EditText editEmail = getView().findViewById( R.id.edit3);
        final EditText editTema = getView().findViewById( R.id.edit3);
        final EditText editMensaje = getView().findViewById( R.id.edit3);
        Button btnEmail = getView().findViewById(R.id.btnMensaje);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nombre.getText().toString();
                String email = editEmail.getText().toString();
                String tema = editTema.getText().toString();
                String mensaje = editMensaje.getText().toString();
                if (TextUtils.isEmpty(name)){
                    nombre.setError("Enter Your Name");
                    nombre.requestFocus();
                    return;
                }

                Boolean onError = false;
                if (!isValidEmail(email)) {
                    onError = true;
                    editEmail.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(tema)){
                    editTema.setError("Enter Your Subject");
                    editTema.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mensaje)){
                    editMensaje.setError("Enter Your Message");
                    editMensaje.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"matiasbendayan9@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, tema);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "name:"+name+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+mensaje);
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));
            }
        });
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
