package com.example.lenovo.chalk_talk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class show_pdf extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);



        get_pdf_url();
    }

    private void get_pdf_url()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("pdf_urls").child(getIntent().getStringExtra("course_id")+"_"+getIntent().getStringExtra("module_id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Upload pdf_url = dataSnapshot.getValue(Upload.class);

                String myPdfUrl = pdf_url.url;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(myPdfUrl), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent newIntent = Intent.createChooser(intent, "Open File");
                try {
                    startActivity(newIntent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}