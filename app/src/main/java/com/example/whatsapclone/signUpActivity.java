package com.example.whatsapclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapclone.Models.Users;
import com.example.whatsapclone.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance("https://whatsap-clone-1be7a-default-rtdb.firebaseio.com");



        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.txtUsername.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() && !binding.txtPassword.getText().toString().isEmpty()){
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Users user=new Users(binding.txtUsername.getText().toString(),binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString());
                                        String id=task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(user);

                                        
                                        Toast.makeText(signUpActivity.this,"Sign Up successfull",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(signUpActivity.this,MainActivity.class);
                                        startActivity(intent);

                                    }else {
                                        Toast.makeText(signUpActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });


                }else {
                    Toast.makeText(signUpActivity.this,"Enter Credentials",Toast.LENGTH_SHORT).show();
                }


            }
        });
        binding.txtAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }

}