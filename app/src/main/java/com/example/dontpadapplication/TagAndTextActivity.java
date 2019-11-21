package com.example.dontpadapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TagAndTextActivity extends AppCompatActivity {

    EditText tagEditText;
    EditText mainActivityTextBox;
    Button buttonText;
    Button buttonPicture;

    //checkpoints for Tag and Text
    String tagEditTextSave;
    String textBoxEditSave;

    Intent imagesGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_and_text);

        tagEditText = findViewById(R.id.tagEditText);
        mainActivityTextBox = findViewById(R.id.activityMainTextBox);

        buttonText = findViewById(R.id.activityMainButtonText);
        buttonPicture = findViewById(R.id.activityMainButtonPicture);

//        Intent tag_and_text = getIntent();
//        tagEditText.setText(tag_and_text.getStringExtra("Tag"));
//        mainActivityTextBox.setText(tag_and_text.getStringExtra(("Text")));

        //Gallery Activity
        buttonPicture.setOnClickListener(view -> {
            tagEditTextSave = tagEditText.getText().toString();
            textBoxEditSave = mainActivityTextBox.getText().toString();

            imagesGallery = new Intent(TagAndTextActivity.this, GalleryActivity.class);
            imagesGallery.putExtra("Image",tagEditTextSave);
            startActivity(imagesGallery);
        });

        buttonText.setOnClickListener(view ->
                Toast.makeText(
                        TagAndTextActivity.this,
                        getString(R.string.current_page),
                        Toast.LENGTH_SHORT
                ).show());
    }

}