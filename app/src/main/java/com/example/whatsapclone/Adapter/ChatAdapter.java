package com.example.whatsapclone.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapclone.Models.MessageModel;
import com.example.whatsapclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    String recId;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view=LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view =LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return  new ReceiverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel=messageModels.get(position);
//        set on click listener on a message
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete ")
                        .setMessage("Are you sure you want to delete")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("Chats").child(senderRoom)
                                        .setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
                return false;
            }
        });


        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder) holder).senderMsg.setText(messageModel.getMessage());

//            set message date received

            Date date= new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("Hh:mm:ss");
            String strDate=simpleDateFormat.format(date);
            ((SenderViewHolder) holder).senderTime.setText(strDate.toString());
        }
        else {
            ((ReceiverViewHolder) holder).receiverMsg.setText(messageModel.getMessage());

//            set message date send

            Date date= new Date(messageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("Hh:mm:ss");
            String strDate=simpleDateFormat.format(date);
            ((ReceiverViewHolder) holder).receiverTime.setText(strDate.toString());


        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class  ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg,receiverTime;


        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg=itemView.findViewById(R.id.receiverText);
            receiverTime=itemView.findViewById(R.id.receiverTime);
        }
    }

    public class  SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }


}
