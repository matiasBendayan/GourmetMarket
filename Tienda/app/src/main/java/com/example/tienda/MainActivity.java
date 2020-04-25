package com.example.tienda;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    EditText mail,clave2;
    FirebaseAuth.AuthStateListener mAuthListerner;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        TextView olvideContransena = (TextView) findViewById( R.id.LoginForgottenPasswordButton );
        olvideContransena.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                startActivity( i );
            }
        } );
        mail =  findViewById( R.id.mail );
        clave2 =  findViewById( R.id.clave );
        progressDialog = new ProgressDialog( this );
        Button btnIngresar =  this.findViewById( R.id.buttonIngresar);
        btnIngresar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingresar(mail.getText().toString(),clave2.getText().toString());
            }
        } );
        TextView edtRegistrar = this.findViewById( R.id.buttonRegistrar );
        edtRegistrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();
            }
        } );
        mAuthListerner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText( MainActivity.this, "Secion iniciada con el email"+user.getEmail(), Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(getApplicationContext(),TiendaHome.class);
                    startActivity( i );
                }else{
                    Toast.makeText( MainActivity.this, "Secion cerrada", Toast.LENGTH_SHORT ).show();
                }
            }
        };
    }

    public void Ingresar(String email,String clave){
        if(email.length()==0){
            Toast.makeText( MainActivity.this, "Se debe ingresar email", Toast.LENGTH_SHORT ).show();
        }else if(clave.length()==0){
            Toast.makeText( MainActivity.this, "Se debe ingresar contrasena", Toast.LENGTH_SHORT ).show();
        }else{
        progressDialog.setMessage( "Iniciando Sesion.." );
        progressDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,clave).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText( MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(getApplicationContext(),TiendaHome.class);
                    i.putExtra("clave",clave2.getText().toString());
                    startActivity( i );
                }else{
                    Toast.makeText( MainActivity.this, "Usuario o contrasena incorrecta", Toast.LENGTH_SHORT ).show();
                }
                progressDialog.dismiss();
            }
        } );}
    }
    public void Registrar(){
        Intent i = new Intent(this,Registrar.class);
        startActivity( i );
    }
}
