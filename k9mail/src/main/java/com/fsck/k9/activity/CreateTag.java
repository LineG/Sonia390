package com.fsck.k9.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    AmbilWarnaDialog colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);

        Button colorPickerButton1;
        Button colorPickerButton2;
        Button colorPickerButton3;
        Button save1;
        Button save2;
        Button save3;
        Button remove1;
        Button remove2;
        Button remove3;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String messageIdTemp = intent.getStringExtra("messageId");
        if (messageIdTemp == null) {
            messageIdTemp = "test";
        }
        final String messageId = messageIdTemp;
        String emailTemp = intent.getStringExtra("email");
        if (emailTemp != null) {
            emailTemp = emailTemp.replace(".", "^");
        }
        else {
            emailTemp = "390soen@gmailTest";
        }

        final String email = emailTemp;

        tagColor = ContextCompat.getColor(CreateTag.this, R.color.colorPrimary);

        colorPickerButton1 = (Button) findViewById(R.id.color_picker1);
        colorPickerButton2 = (Button) findViewById(R.id.color_picker2);
        colorPickerButton3 = (Button) findViewById(R.id.color_picker3);
        save1 = (Button) findViewById(R.id.save_tag1);
        save2 = (Button) findViewById(R.id.save_tag2);
        save3 = (Button) findViewById(R.id.save_tag3);
        remove1 = (Button) findViewById(R.id.remove_1);
        remove2 = (Button) findViewById(R.id.remove_2);
        remove3 = (Button) findViewById(R.id.remove_3);


        tag1Name = (EditText) findViewById(R.id.tag1_name);
        tag2Name = (EditText) findViewById(R.id.tag2_name);
        tag3Name = (EditText) findViewById(R.id.tag3_name);

        tag1 = (TextView) findViewById(R.id.tag_1);
        tag2 = (TextView) findViewById(R.id.tag_2);
        tag3 = (TextView) findViewById(R.id.tag_3);

        final DatabaseReference tagsDb = FirebaseDatabase.getInstance().getReference().child(email).child(messageId);

        retrieveTags(email,messageId);

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

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag1NameText = tag1Name.getText().toString();
                Map userInfo = new HashMap<>();

                Tag tag1 = new Tag(tag1NameText, tag1Color);
                userInfo.put("tag1", tag1);
                Toast.makeText(CreateTag.this, "Added tag1", Toast.LENGTH_LONG).show();

                tagsDb.updateChildren(userInfo);
            }
        });

        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag2NameText = tag2Name.getText().toString();
                Map userInfo = new HashMap<>();

                Tag tag2 = new Tag(tag2NameText, tag2Color);
                userInfo.put("tag2", tag2);
                Toast.makeText(CreateTag.this, "Added tag2", Toast.LENGTH_LONG).show();

                tagsDb.updateChildren(userInfo);
            }
        });

        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag3NameText = tag3Name.getText().toString();
                Map userInfo = new HashMap<>();

                Tag tag3 = new Tag(tag3NameText, tag3Color);
                userInfo.put("tag3", tag3);
                Toast.makeText(CreateTag.this, "Added tag3", Toast.LENGTH_LONG).show();

                tagsDb.updateChildren(userInfo);
            }
        });

        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsDb.child("tag1").removeValue();
                tag1.setText("Tag 1");
                tag1.setBackgroundColor(0);
                Toast.makeText(CreateTag.this, "Removed tag1", Toast.LENGTH_LONG).show();

            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsDb.child("tag2").removeValue();
                tag2.setText("Tag 2");
                tag2.setBackgroundColor(0);
                Toast.makeText(CreateTag.this, "Removed tag2", Toast.LENGTH_LONG).show();

            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagsDb.child("tag3").removeValue();
                tag3.setText("Tag 3");
                tag3.setBackgroundColor(0);
                Toast.makeText(CreateTag.this, "Removed tag3", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void openColorPicker(final int tagNumber) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tagColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                //CANT BE EMPTY
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                tagColor = color;
                switch (tagNumber) {
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
                        break;
                    }
                }
            }
        });
        colorPicker.show();
    }

    public void retrieveTags(final String email, final String messageId){
        DatabaseReference tag1Db = FirebaseDatabase.getInstance().getReference().child(email).child(messageId);

        tag1Db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("tag1").exists()){
                        String tag1Name = dataSnapshot.child("tag1").child("name").getValue().toString();
                        String tag1ColorString = dataSnapshot.child("tag1").child("color").getValue().toString();
                        int tag1Color = Integer.parseInt(tag1ColorString);
                        tag1.setBackgroundColor(tag1Color);
                        tag1.setText(tag1Name);
                    }
                    if(dataSnapshot.child("tag2").exists()){
                        String tag2Name = dataSnapshot.child("tag2").child("name").getValue().toString();
                        String tag2ColorString = dataSnapshot.child("tag2").child("color").getValue().toString();
                        int tag2Color = Integer.parseInt(tag2ColorString);
                        tag2.setBackgroundColor(tag2Color);
                        tag2.setText(tag2Name);
                    }
                    if(dataSnapshot.child("tag3").exists()){
                        String tag3Name = dataSnapshot.child("tag3").child("name").getValue().toString();
                        String tag3ColorString = dataSnapshot.child("tag3").child("color").getValue().toString();
                        int tag3Color = Integer.parseInt(tag3ColorString);
                        tag3.setBackgroundColor(tag3Color);
                        tag3.setText(tag3Name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Can't be empty
            }
        });
    }
}
