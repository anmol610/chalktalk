package com.example.lenovo.chalk_talk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

/**
 * Created by charanghumman on 10/05/18.
 */

public class ModuleOptionDialog extends Dialog {

    private TextView add_study_material , add_quiz_material ;

    public ModuleOptionDialog(@NonNull final Context context, int themeResId , final String course_id) {
        super(context, themeResId);

        setContentView(R.layout.module_option_layout);

        add_study_material = findViewById(R.id.add_study_material);

        add_quiz_material = findViewById(R.id.add_quiz_material);

        add_study_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context , AddModuleNameActivity.class);

                i.putExtra("course_id" , course_id);

                context.startActivity(i);

                dismiss();

            }
        });

        add_quiz_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context , AddQuizNameActivity.class);

                i.putExtra("course_id" , course_id);

                context.startActivity(i);

                dismiss();

            }
        });
    }
}
