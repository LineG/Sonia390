package com.fsck.k9.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.fsck.k9.R;
import com.fsck.k9.fragment.MessageListFragment;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateTag extends AppCompatActivity {

    private Button colorPickerButton1, colorPickerButton2, colorPickerButton3;
    private TextView tag1, tag2, tag3;
    private EditText tag1Name, tag2Name, tag3Name;
    private int tagColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);
        Intent intent = getIntent();
        String messageId = intent.getStringExtra("messageId");
        String accountId = intent.getStringExtra("accountId");
        Log.d("email_id", messageId);
        Log.d("account_id", accountId);

        tagColor = ContextCompat.getColor(CreateTag.this, R.color.colorPrimary);

        colorPickerButton1 = (Button) findViewById(R.id.color_picker1);
        colorPickerButton2 = (Button) findViewById(R.id.color_picker2);
        colorPickerButton3 = (Button) findViewById(R.id.color_picker3);

        tag1Name = (EditText) findViewById(R.id.tag1_name);
        tag2Name = (EditText) findViewById(R.id.tag2_name);
        tag3Name = (EditText) findViewById(R.id.tag3_name);

        tag1 = (TextView) findViewById(R.id.tag_1);
        tag2 = (TextView) findViewById(R.id.tag_2);
        tag3 = (TextView) findViewById(R.id.tag_3);




        colorPickerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(1);
            }
        });

        colorPickerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(2);
            }
        });

        colorPickerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker(3);
            }
        });

    }



    public void openColorPicker(final int tagNumber) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tagColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                //BLAH BLAH CANT BE EMPTY
                Log.d("Color Picker", "Cancelled");
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                tagColor = color;
                switch (tagNumber) {
                    case 1: {
                        tag1.setBackgroundColor(tagColor);
                        break;
                    }
                    case 2: {
                        tag2.setBackgroundColor(tagColor);
                        break;
                    }
                    case 3: {
                        tag1.setBackgroundColor(tagColor);
                        break;
                    }
                }
            }
        });
        colorPicker.show();
    }
}
