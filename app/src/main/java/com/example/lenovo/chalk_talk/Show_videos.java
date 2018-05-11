package com.example.lenovo.chalk_talk;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Show_videos extends AppCompatActivity {
    private VideoView videoView ;
    MediaController mediac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_videos);
        videoView = findViewById(R.id.show);
        mediac = new MediaController(this);

        get_video_from_firebase();
    }

    private void get_video_from_firebase()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("video_urls").child(getIntent().getStringExtra("course_id")+"_"+getIntent().getStringExtra("module_id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Upload video_url = dataSnapshot.getValue(Upload.class);

                String str = video_url.getUrl();
                Uri uri = Uri.parse(str);

                videoView.setVideoURI(uri);
                videoView.setMediaController(mediac);
                mediac.setAnchorView(videoView);
                videoView.setVisibility(View.VISIBLE);
                videoView.requestFocus();
                videoView.start();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}