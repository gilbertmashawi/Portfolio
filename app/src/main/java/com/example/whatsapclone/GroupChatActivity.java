package com.example.whatsapclone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapclone.Adapter.ChatAdapter;
import com.example.whatsapclone.Models.MessageModel;
import com.example.whatsapclone.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backArrow.setOnClickListener(v -> {
            Intent intent=new Intent(GroupChatActivity.this,MainActivity.class);
            startActivity(intent);

        });


        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels=new ArrayList<>();

        final  String senderId= FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Group Chart");


        final ChatAdapter adapter=new ChatAdapter(messageModels,this);
        binding.chatrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatrecyclerview.setLayoutManager(layoutManager);

//        show the messages in the Group chat
        database.getReference().child("Group Chat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();

                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    MessageModel model=dataSnapshot.getValue(MessageModel.class);
                                    messageModels.add(model);

                                }
                                adapter.notifyDataSetChanged();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


        binding.send.setOnClickListener(v -> {
            final String message=binding.enterMessage.getText().toString();
            final MessageModel model=new MessageModel(senderId,message);
            model.setTimestamp(new Date().getTime());

            binding.enterMessage.setText("");
            database.getReference().child("Group Chat")
                    .push()
                    .setValue(model)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(GroupChatActivity.this,"Message Send",Toast.LENGTH_SHORT).show();
                        }
                    });

        });

    }


}