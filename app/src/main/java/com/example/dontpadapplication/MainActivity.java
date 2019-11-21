package com.example.dontpadapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText loginEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText =
                findViewById(R.id.loginEditText);
        senhaEditText =
                findViewById(R.id.senhaEditText);
        mAuth =
                FirebaseAuth.getInstance();
    }

    public void irParaCadastro(View view) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    public void fazerLogin(View view) {
        String login = loginEditText.getEditableText().toString();
        String senha = senhaEditText.getEditableText().toString();

        mAuth.signInWithEmailAndPassword(login,senha)
                .addOnSuccessListener(
                        (authResult) -> {
                            startActivity (new Intent (this, ChatActivity.class));
                        })
                .addOnFailureListener(
                        (exception) -> {
                            exception.printStackTrace();
                            Toast.makeText(this, "Acesso invalido",Toast.LENGTH_SHORT)
                                    .show();
                        });

    }
}
