package com.example.dontpadapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import static java.lang.Integer.parseInt;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mensagemRecyclerView;
//    private ChatAdapter adapter;
    private List<TagContent> mensagens;
    private FirebaseUser fireUser;
    private CollectionReference mMsgsReference;
//    private static final int REQ_CODE_CAMERA = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mensagemRecyclerView = findViewById(R.id.mensagensRecyclerView);
        mensagens = new ArrayList<>();

//        adapter = new ChatAdapter(this, mensagens);

//        mensagemRecyclerView.setAdapter(adapter);
        //LayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        mensagemRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupFirebase() {
        fireUser = FirebaseAuth.getInstance().getCurrentUser();
        mMsgsReference =
                FirebaseFirestore.getInstance().collection("mensagens");
        getRemoteMsgs();
    }

    private void getRemoteMsgs() {
        //atualizar Lista de mensagens
        mMsgsReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            //limpar Lista com estrutura de mensagens
            mensagens.clear();
            //repassar estrutura anterior de mensagens
            for (DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                TagContent m = docSnap.toObject(TagContent.class);
                mensagens.add(m);
            }
//                Collections.sort(mensagens);
//                adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();
    }

    private void esconderTeclado(View v) {
        InputMethodManager ims =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ims.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public void editTAG(View view) {
        Intent intent = new Intent(ChatActivity.this, TagAndTextActivity.class);
        startActivity(intent);
    }


}//ChatActivity



////VIEWHOLDER
//class ChatViewHolder extends RecyclerView.ViewHolder{
//    TextView tag;
//    TextView text;
//    ImageView imageView;
//
//    public ChatViewHolder(@NonNull View itemView) {
//        super(itemView); //raiz da estrutura XML
//        this.tag =
//                itemView.findViewById(R.id.tag_name);
//        this.text =
//                itemView.findViewById(R.id.textField);
//        this.imageView =
//                itemView.findViewById(R.id.image);
//    }
//}
//
//
////ADAPTER
//class ChatAdapter extends RecyclerView.Adapter <ChatViewHolder>{
//
//    private Context context;
//    private List<TagContent> mensagens;
//
//    public ChatAdapter(Context context, List<TagContent> mensagem) {
//        this.context = context;
//        this.mensagens = mensagem;
//    }
//
//    @NonNull
//    @Override
//    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View raiz = inflater.inflate(R.layout.list_item, parent,false);
//        return new ChatViewHolder(raiz);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
//        TagContent m = mensagens.get(position);//mensagens na lista
//        holder.dataNomeTextView.setText(
//                context.getString(
//                        R.string.data_nome,
//                        DateHelper.format(m.getData()),
//                        m.getEmail()
//                )
//        );
//        holder.mensagemTextView.setText(m.getTexto());
//
//        //Imagens armazenadas
//        StorageReference pictureStorageReference =
//                FirebaseStorage.getInstance().getReference(
//                        String.format(
//                                Locale.getDefault(),
//                                "images/%s/profilePic.jpg",
//                                m.getEmail().replace("@", "")
//                        )
//                );
//    }
//
//    @Override
//    public int getItemCount() {
//        return mensagens.size();
//    }
//
//}//Adapter