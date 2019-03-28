package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Tag;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateTag extends AppCompatActivity {
    
    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    public EditText tag1Name;
    public EditText tag2Name;
    public EditText tag3Name;
    private int tagColor;
    private int tag1Color;
    private int tag2Color;
    private int tag3Color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);

        Button colorPickerButton1;
        Button colorPickerButton2;
        Button colorPickerButton3;
        Button save;

        Intent intent = getIntent();
        final String messageId = intent.getStringExtra("messageId");
        final String accountId = intent.getStringExtra("accountId");
        Log.d("email_id", messageId);
        Log.d("account_id", accountId);

        tagColor = ContextCompat.getColor(CreateTag.this, R.color.colorPrimary);

        colorPickerButton1 = (Button) findViewById(R.id.color_picker1);
        colorPickerButton2 = (Button) findViewById(R.id.color_picker2);
        colorPickerButton3 = (Button) findViewById(R.id.color_picker3);
        save = (Button) findViewById(R.id.save_tags);

        tag1Name = (EditText) findViewById(R.id.tag1_name);
        tag2Name = (EditText) findViewById(R.id.tag2_name);
        tag3Name = (EditText) findViewById(R.id.tag3_name);

        tag1 = (TextView) findViewById(R.id.tag_1);
        tag2 = (TextView) findViewById(R.id.tag_2);
        tag3 = (TextView) findViewById(R.id.tag_3);

        Log.d("tagName", tag1Name.getText().toString());





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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag1NameText;
                String tag2NameText;
                String tag3NameText;

                Log.d("tagName", tag1Name.getText().toString());

                tag1NameText = tag1Name.getText().toString();
                tag2NameText = tag2Name.getText().toString();
                tag3NameText = tag3Name.getText().toString();

                DatabaseReference tagsDb = FirebaseDatabase.getInstance().getReference().child(accountId).child(messageId);

                Map userInfo = new HashMap<>();

                Boolean uniqueName = ((!tag1NameText.equals(tag2NameText)) &&
                                     (!tag1NameText.equals(tag3NameText)) &&
                                     (!tag2NameText.equals(tag3NameText)));

                Boolean uniqueColor = ((tag1Color != tag2Color) &&
                                      (tag1Color != tag3Color) &&
                                      (tag2Color != tag3Color));

                if(tag1NameText != null && uniqueName && uniqueColor) {
                    Tag tag1 = new Tag(tag1NameText, tag1Color);
                    userInfo.put("tag1", tag1);
                }
                else {
                    //Toast if not created
                    if(!uniqueName) {
                        Toast.makeText(CreateTag.this, "Name already exists. Change name for tag 1",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(CreateTag.this, "Colour already exists. Chose another color for tag 1",
                                Toast.LENGTH_LONG).show();

                }

                if(tag2NameText != null && uniqueName && uniqueColor) {
                    Tag tag2 = new Tag(tag2NameText, tag2Color);
                    userInfo.put("tag2", tag2);
                }
                else {
                    //Toast if not created
                    if(!uniqueName) {
                        Toast.makeText(CreateTag.this, "Name already exists. Change name for tag 2",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(CreateTag.this, "Colour already exists. Chose another color for tag 2",
                                Toast.LENGTH_LONG).show();
                }

                if(tag3NameText != null  && uniqueName && uniqueColor) {
                    Tag tag3 = new Tag(tag3NameText, tag3Color);
                    userInfo.put("tag3", tag3);
                }
                else {
                    //Toast if not created
                    if(!uniqueName) {
                        Toast.makeText(CreateTag.this, "Name already exists. Change name for tag 31111",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(CreateTag.this, "Colour already exists. Chose another color for tag 3",
                                Toast.LENGTH_LONG).show();
                }


                tagsDb.updateChildren(userInfo);
            }
        });

    }



    public void openColorPicker(final int tagNumber) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tagColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                //CANT BE EMPTY
                Log.d("Color Picker", "Cancelled");
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                tagColor = color;
                switch (tagNumber) { // We don't want the same color to be applied to all the tag
                    case 1: {
                        tag1Color = tagColor;
                        tag1.setBackgroundColor(tagColor);
                        break;
                    }
                    case 2: {
                        tag2Color = tagColor;
                        tag2.setBackgroundColor(tagColor);
                        break;
                    }
                    case 3: {
                        tag3Color = tagColor;
                        tag3.setBackgroundColor(tagColor);
                        break;
                    }
                    default: {
                        Log.d("ERROR", "Tag was not created");
                        break;
                    }
                }
            }
        });
        colorPicker.show();


    }
}
