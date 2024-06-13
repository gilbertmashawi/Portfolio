package com.example.whatsapclone.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapclone.Adapter.UsersAdapter;
import com.example.whatsapclone.Models.Users;
import com.example.whatsapclone.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class chatFragment extends Fragment {



    public chatFragment() {
        // Required empty public constructor
    }

    FragmentChatBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding=FragmentChatBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.chartRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.chartRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    users.setUserid(dataSnapshot.getKey());
                    if(!users.getUserid().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().keepSynced(true);



        return binding.getRoot();
    }
}