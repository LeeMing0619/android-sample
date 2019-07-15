package com.app.flex.fragment.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.flex.R;

import java.io.File;

public class TakePhotoInfoActivity extends AppCompatActivity {
    RelativeLayout image_pick_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_info);

        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());

        image_pick_rv = findViewById(R.id.image_pick_rv);
        image_pick_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (ContextCompat.checkSelfPermission(TakePhotoInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(TakePhotoInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                    } else {
                        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        Uri uri = FileProvider.getUriForFile(TakePhotoInfoActivity.this, getApplicationContext().getPackageName(), file);
                        m_intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        m_intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(m_intent, 1);
                    }


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String uri_data = "";
            if (requestCode == 1) {

                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                //Uri of camera image
                Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName(), file);
                uri_data = uri.toString();
            } else if (requestCode == 2) {
                if (data != null) {
                    uri_data = data.getDataString();
                }
            }

            Intent intent_retake = new Intent(TakePhotoInfoActivity.this, TakePhotoDisplayImageActivity.class);
            intent_retake.putExtra("uri", uri_data);
            intent_retake.putExtra("upload","take_photo");
            startActivity(intent_retake);
        }
    }
}

