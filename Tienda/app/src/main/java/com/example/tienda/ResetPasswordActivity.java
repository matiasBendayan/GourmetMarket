package com.example.tienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button reset;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reset_password );
        mAuth = FirebaseAuth.getInstance();
        email = findViewById( R.id.editTextEmail );
        mDialog = new ProgressDialog( this );
        reset = findViewById( R.id.btnResset );
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!email.getText().toString().isEmpty()){
                   mDialog.setMessage( "Espere un momento..." );
                   mDialog.show();
                resetPassword();
               }else{
                   Toast.makeText(ResetPasswordActivity.this,"Debe ingresar el email",Toast.LENGTH_SHORT);
               }
            }
        } );
    }
    public void resetPassword(){
        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if(task.isSuccessful()){
                 Toast.makeText(ResetPasswordActivity.this,"Se envio el correo de reestablecer contrasena",Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(ResetPasswordActivity.this,"No se pudo enviar el correo de reestablecer contrasena",Toast.LENGTH_SHORT).show(   );
             }
                mDialog.dismiss();
            }
        } );
    }
}
