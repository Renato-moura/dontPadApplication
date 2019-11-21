package com.example.dontpadapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryActivity extends AppCompatActivity {
    EditText tagEditText;
    EditText activityMainTextBox;//other Activity
    Button buttonPicture;

    //checkpoints for Tag and Text
    String tagEditTextSave;
    String textBoxEditSave;

    ImageView imageView;
    Uri imageURI;
    //flag
    boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        tagEditText = findViewById(R.id.tagEditText);
        activityMainTextBox = findViewById(R.id.activityMainTextBox);

        buttonPicture = findViewById(R.id.galleryButtonPicture);// Open Gallery
        imageView = findViewById(R.id.galleryImageView);

        //Extra Buttons (back Buttons)
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Show Gallery
        buttonPicture.setOnClickListener(v -> openGallery());

        Intent imagesGallery = getIntent();

        //Tag
        tagEditText.setText(imagesGallery.getStringExtra("Tag"));

    }//onCreate

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            tagEditTextSave = tagEditText.getText().toString();
            textBoxEditSave = activityMainTextBox.getText().toString();
            Intent textArea = new Intent(GalleryActivity.this, MainActivity.class);
            textArea.putExtra("Text",tagEditTextSave);
            startActivity(textArea);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, Integer.parseInt(
                getString(
                        R.string.PICK_IMAGE_REQUEST
                )
        ));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Integer.parseInt(getString(R.string.PICK_IMAGE_REQUEST)) ){
            if(data.getData() == null){
                Toast.makeText(this, R.string.No_img_on_gallery, Toast.LENGTH_SHORT).show();
            }else{
                imageURI = data.getData();
                imageView.setImageURI(imageURI);
            }

        }else if (resultCode == RESULT_OK && requestCode == Integer.parseInt(getString(R.string.REQ_CODE_CAMERA)) ){
            Bitmap picture = (Bitmap) data.getExtras().get("data");
//            pictureImageView.setImageBitmap(picture);
        }
    }

    public void takePhoto(View view) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getPackageManager()) != null){
            startActivityForResult(
                    camera,
                    Integer.parseInt(getString(R.string.REQ_CODE_CAMERA))
            );
        }else{
            Toast.makeText(
                    this,
                    getString(R.string.no_camera),//defina um texto apropriado
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
}