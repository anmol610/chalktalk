package com.example.lenovo.chalk_talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.chalk_talk.dataModel.createaccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.FirebaseAppHelper;

public class tutor_create_account extends AppCompatActivity {
    EditText name, dob , qualification, address, emailId, password, confirmPassword;
    RadioGroup gender;
    String names, dobs, qualifications, saddress, genders, emailIds, passwords, confirmPasswords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_create_account);
        name = findViewById(R.id.name_et);
        dob = findViewById(R.id.dob_et);
        qualification = findViewById(R.id.Qualification_et);
        address = findViewById(R.id.Address_et);
        gender = findViewById(R.id.gender_btn);
        emailId = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        confirmPassword = findViewById(R.id.cpassword_et);



    }

    public void register_tutor(View view) {

        names = name.getText().toString();
        dobs = dob.getText().toString();
        qualifications = qualification.getText().toString();
        saddress = address.getText().toString();
        genders =((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        emailIds = emailId.getText().toString();
        passwords = password.getText().toString();
        confirmPasswords = confirmPassword.getText().toString();


        if (Patterns.EMAIL_ADDRESS.matcher(emailIds).matches()) {

        } else {
            Toast.makeText(tutor_create_account.this, "invalid email", Toast.LENGTH_SHORT).show();
            return;
        }


        if (passwords.length() >= 8 && passwords.length() < 33) {

        } else {
            Toast.makeText(tutor_create_account.this, "password must be between 8-33 character", Toast.LENGTH_SHORT).show();
            return;
        }

        if (saddress.length() >= 5) {

        } else {
            Toast.makeText(tutor_create_account.this, "Enter address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (names.length() >= 5) {

        } else {
            Toast.makeText(tutor_create_account.this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dobs.length() >= 2) {

        } else {
            Toast.makeText(tutor_create_account.this, "Enter dob", Toast.LENGTH_SHORT).show();
            return;
        }
        if (qualifications.length() >= 5) {

        } else {
            Toast.makeText(tutor_create_account.this, "Enter qualification", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPasswords.equals(passwords)) {

        } else {
            Toast.makeText(tutor_create_account.this, "confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (genders.length() >= 5) {

        } else {
            Toast.makeText(tutor_create_account.this, "select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progress_bar = new ProgressDialog(tutor_create_account.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Create account");
        progress_bar.show();

        final FirebaseAuth f_auth = FirebaseAuth.getInstance();

        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();

                if (task.isSuccessful()) {

                    Toast.makeText(tutor_create_account.this, "done", Toast.LENGTH_SHORT).show();
                    emailIds.replace(".","");
                    FirebaseUser user = f_auth.getCurrentUser();
                    UserProfileChangeRequest name = new UserProfileChangeRequest.Builder().setDisplayName(names).build();
                    user.updateProfile(name);
                     createaccount data = new createaccount(names, dobs, qualifications, saddress, genders);
                     FirebaseDatabase database = FirebaseDatabase.getInstance();
                     String emaill=emailIds.replace(".","");
                    database.getReference().child("Tutor").child(emaill).setValue(data);
                    Intent i = new Intent(tutor_create_account.this, Tutor_home_page.class);
                    startActivity(i);
                    finish();
                } else {
                    System.out.print(task.getException());
                    Toast.makeText(tutor_create_account.this, "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        };

        f_auth.createUserWithEmailAndPassword(emailIds, passwords).addOnCompleteListener(listener);


    }
}
