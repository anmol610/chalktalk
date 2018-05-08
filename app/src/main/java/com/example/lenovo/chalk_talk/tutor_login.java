package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class tutor_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);
    }

    public void sign_up_tutor(View view) {
        EditText email_et = findViewById(R.id.emailt);

        EditText password_et = findViewById(R.id.passwordt);

        String email= email_et.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

        }
        else{
            Toast.makeText(tutor_login.this,"invalid email",Toast.LENGTH_SHORT).show();
            return;
        }

        String password = password_et.getText().toString();

        if(password.length()>= 8 && password.length()< 33)
        {

        }
        else {
            Toast.makeText(tutor_login.this, "password must be between 8-33 character", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progress_bar = new ProgressDialog(tutor_login.this);

        progress_bar.setTitle("please wait");
        progress_bar.setMessage("logging in");
        progress_bar.show();

        FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if(task.isSuccessful())
                {
                    Toast.makeText(tutor_login.this,"done",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(tutor_login.this,Tutor_home_page.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(tutor_login.this,"error try again",Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(listener);
    }

    public void create_account1(View view) {
        Intent i = new Intent(tutor_login.this,tutor_create_account.class);
        startActivity(i);

    }
}
