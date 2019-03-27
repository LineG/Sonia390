package com.fsck.k9.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.fsck.k9.R;
import com.fsck.k9.fragment.MessageListFragment;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateTag extends AppCompatActivity {
    private ConstraintLayout createTagLayout;
    private int mDefaultColor;
    private Button colorPickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);
        Intent intent = getIntent();
        String messageId = intent.getStringExtra("messageId");
        String accountId = intent.getStringExtra("accountId");
        Log.d("email_id", messageId);
        Log.d("account_id", accountId);

        createTagLayout = (ConstraintLayout) findViewById(R.id.create_tag_layout);
        mDefaultColor = ContextCompat.getColor(CreateTag.this, R.color.colorPrimary);
        colorPickerButton = (Button) findViewById(R.id.color_picker);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                //BLAH BLAH CANT BE EMPTY
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                createTagLayout.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }
}
