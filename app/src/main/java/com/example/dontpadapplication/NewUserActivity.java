package com.example.dontpadapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class NewUserActivity extends AppCompatActivity {

    private EditText loginNovoUsuarioEditText;
    private EditText senhaNovoUsuarioEditText;
    private FirebaseAuth mAuth;
    private StorageReference pictureStorageReference;
//    private static final int REQ_CODE_CAMERA = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        loginNovoUsuarioEditText =
                findViewById(R.id.loginNovoUsuarioEditText);
        senhaNovoUsuarioEditText =
                findViewById(R.id.senhaNovoUsuarioEditText);
        mAuth =
                FirebaseAuth.getInstance();
    }

    public void criarNovoUsuario(View view) {
        String login = loginNovoUsuarioEditText.getText().toString();
        String senha = senhaNovoUsuarioEditText.getText().toString();
        //Task - add on firebase
        Task<AuthResult> task =
                mAuth.createUserWithEmailAndPassword(login, senha);
        //observer
        task.addOnSuccessListener(authResult -> {
            //notify user on screen
            Toast.makeText
                    (NewUserActivity.this, getString(android.R.string.ok), Toast.LENGTH_SHORT).show();
            finish();
        });
        task.addOnFailureListener(e -> {
            e.printStackTrace();
            //notify user on screen
            Toast.makeText
                    (NewUserActivity.this, "Falha no cadastro", Toast.LENGTH_SHORT).show();
        });
    }


    private void uploadPicture (Bitmap picture){
        pictureStorageReference = FirebaseStorage.getInstance().
                getReference(
                        String.format(
                                Locale.getDefault(),
                                "images/%s/profilePic.jpg",
                                loginNovoUsuarioEditText.getText().toString().replace ( "@", "")
                        )
                );
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //fluxo de saída de dados - vetor de bytes
        picture.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); //Coletar img e comprimir em formato especifico
        byte[] bytes = byteArrayOutputStream.toByteArray();
        pictureStorageReference.putBytes(bytes); //upload acontece aqui
    }

}
