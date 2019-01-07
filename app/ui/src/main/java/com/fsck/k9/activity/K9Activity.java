package com.fsck.k9.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;

import android.view.View;
import com.fsck.k9.activity.K9ActivityCommon.K9ActivityMagic;
import com.fsck.k9.activity.misc.SwipeGestureDetector.OnSwipeGestureListener;
import com.fsck.k9.ui.R;
import com.fsck.k9.ui.permissions.PermissionRationaleDialogFragment;
import timber.log.Timber;


public abstract class K9Activity extends AppCompatActivity implements K9ActivityMagic {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS  = 1;
    public static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 2;
    private static final String FRAGMENT_TAG_RATIONALE = "rationale";


    private K9ActivityCommon mBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mBase = K9ActivityCommon.newInstance(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        mBase.preDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setupGestureDetector(OnSwipeGestureListener listener) {
        mBase.setupGestureDetector(listener);
    }

    protected void setLayout(@LayoutRes int layoutResId) {
        setContentView(layoutResId);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            throw new IllegalArgumentException("K9 layouts must provide a toolbar with id='toolbar'.");
        }
        setSupportActionBar(toolbar);
    }

    protected void setLayout(View view) {
        setContentView(view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            throw new IllegalArgumentException("K9 layouts must provide a toolbar with id='toolbar'.");
        }
        setSupportActionBar(toolbar);
    }

    public boolean hasPermission(Permission permission) {
        return ContextCompat.checkSelfPermission(this, permission.permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissionOrShowRationale(Permission permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.permission)) {
            PermissionRationaleDialogFragment dialogFragment =
                    PermissionRationaleDialogFragment.newInstance(permission);

            dialogFragment.show(getSupportFragmentManager(), FRAGMENT_TAG_RATIONALE);
        } else {
            requestPermission(permission);
        }
    }

    public void requestPermission(Permission permission) {
        Timber.i("Requesting permission: " + permission.permission);
        ActivityCompat.requestPermissions(this, new String[] { permission.permission }, permission.requestCode);
    }


    public enum Permission {
        READ_CONTACTS(
                Manifest.permission.READ_CONTACTS,
                PERMISSIONS_REQUEST_READ_CONTACTS,
                R.string.permission_contacts_rationale_title,
                R.string.permission_contacts_rationale_message
        ),
        WRITE_CONTACTS(
                Manifest.permission.WRITE_CONTACTS,
                PERMISSIONS_REQUEST_WRITE_CONTACTS,
                R.string.permission_contacts_rationale_title,
                R.string.permission_contacts_rationale_message
        );


        public final String permission;
        public final int requestCode;
        public final int rationaleTitle;
        public final int rationaleMessage;

        Permission(String permission, int requestCode, @StringRes int rationaleTitle, @StringRes int rationaleMessage) {
            this.permission = permission;
            this.requestCode = requestCode;
            this.rationaleTitle = rationaleTitle;
            this.rationaleMessage = rationaleMessage;
        }
    }
}
