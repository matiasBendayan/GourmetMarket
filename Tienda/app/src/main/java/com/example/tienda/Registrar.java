package com.example.tienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Registrar extends AppCompatActivity {
EditText mail,clave;
FirebaseAuth.AuthStateListener mAuthListerner;
private ProgressDialog progressDialog;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener( mAuthListerner );
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener( mAuthListerner );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registrar );
        mail = findViewById( R.id.mail );
        clave = findViewById( R.id.clave );
        progressDialog = new ProgressDialog( this );
        Button btnregistrar = findViewById( R.id.buttonRegistrar );
        btnregistrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarse(mail.getText().toString(),clave.getText().toString());
            }
        } );
    mAuthListerner = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user != null){
                Toast.makeText( Registrar.this, "Secion iniciada con el email"+user.getEmail(), Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText( Registrar.this, "Secion cerrada", Toast.LENGTH_SHORT ).show();
            }
        }
    };
    }
    public void registrarse(String email,String clave2){
        if(email.length()==0){
            Toast.makeText( Registrar.this, "Se debe ingresar email", Toast.LENGTH_SHORT ).show();
        }
        if(clave.length()==0){
            Toast.makeText( Registrar.this, "Se debe ingresar contrasena", Toast.LENGTH_SHORT ).show();
        }
        progressDialog.setMessage( "Realizando registro en linea.." );
        progressDialog.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,clave2).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Intent i = new Intent(getApplicationContext(),TiendaHome.class);
                    i.putExtra("mail",mail.getText().toString());
                    i.putExtra("clave",clave.getText().toString());
                    startActivity( i );
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText( Registrar.this, "El usuario ya existe", Toast.LENGTH_SHORT ).show();
                    }
                    Toast.makeText( Registrar.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT ).show();
                }
                progressDialog.dismiss();
            }
        } );
    }
}
