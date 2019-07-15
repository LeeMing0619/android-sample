package com.app.flex.fragment.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.app.flex.MyApplication;
import com.app.flex.R;
import com.bumptech.glide.Glide;

import java.io.File;

import static com.app.flex.Utils.AppUtils.getPathFromURI;

public class TakePhotoDisplayImageActivity extends AppCompatActivity {
    private ImageView display_iv;
    private Button retake_btn, submit_btn;
    String image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_display_image);

        display_iv = findViewById(R.id.display_iv);
        retake_btn = findViewById(R.id.retake_btn);
        submit_btn = findViewById(R.id.submit_btn);

        if (getIntent().getStringExtra("display_uri") != null) {
            Glide.with(this).load(getIntent().getStringExtra("display_uri")).into(display_iv);
            MyApplication.profileDisplayURI = getIntent().getStringExtra("display_uri");
        }
        retake_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("upload") != null && !getIntent().getStringExtra("upload").equals("upload")){
                    Intent i=new Intent(TakePhotoDisplayImageActivity.this, SSNNumberActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    finish();
                }

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
                    if (ContextCompat.checkSelfPermission(TakePhotoDisplayImageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(TakePhotoDisplayImageActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                    } else {
                        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                        Uri uri = FileProvider.getUriForFile(TakePhotoDisplayImageActivity.this, getApplicationContext().getPackageName(), file);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String uri_data = "";
            if (requestCode == 1) {
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                image_uri = file.toString();
                MyApplication.profileURI=image_uri;
                Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName(), file);
                uri_data = uri.toString();

            } else if (requestCode == 2) {
                if (data != null) {
                    image_uri = getPathFromURI(this, data.getData());
                    MyApplication.profileURI=image_uri;
                    uri_data = data.getDataString();
                }
            }
            MyApplication.profileDisplayURI = uri_data;

            Glide.with(this).load(uri_data).into(display_iv);
        }
    }
}
