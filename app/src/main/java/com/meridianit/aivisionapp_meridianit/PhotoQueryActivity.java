package com.meridianit.aivisionapp_meridianit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoQueryActivity extends AppCompatActivity {
    private Uri cameraUri;
    private static final int CAMERA = 2;


    @Override
    public void onBackPressed() {
        Log.i("INFO", "Back press does nothing!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoquery);

        Intent intent = getIntent();
        if (intent != null) {
            String uri = intent.getStringExtra("imageURI");
            if (uri != null) {
                ImageView imgView = (ImageView) findViewById(R.id.imageViewPhotoQuery);
                imgView.setImageURI(Uri.parse(uri));
            }
        }


        //listeners on the buttons
        Button btnImageProcessYes = findViewById(R.id.btnImageProcessYes);
        btnImageProcessYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to previous activity
                finish();
            }
        });

        //listeners on the buttons
        Button btnImageProcessNo = findViewById(R.id.btnImageProcessNo);
        btnImageProcessNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraUri = Uri.fromFile(getOutputMediaFile());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(cameraIntent, CAMERA);
            }
        });


    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this.getApplicationContext(), "Make sure made appropriate permsisions", Toast.LENGTH_LONG);
            }

            switch (requestCode) {
                case CAMERA:
                    ImageView imageView = findViewById(R.id.imageViewPhotoQuery);
                    imageView.setImageURI(cameraUri);
                    break;
            }
        }
    }
}