package com.mayurmotinge.abhyudaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mayurmotinge.abhyudaya.R;
import com.mayurmotinge.abhyudaya.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DATE = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_MESSAGE_SENT = 2;

    private List<Object> chatList;
    private static String currentUserId;

    public ChatAdapter(List<Object> chatList, String currentUserId) {
        this.chatList = chatList;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = chatList.get(position);
        if (item instanceof String) {
            return TYPE_DATE;
        } else if (item instanceof ChatMessage) {
            ChatMessage message = (ChatMessage) item;
            return message.getSenderId().equals(currentUserId) ? TYPE_MESSAGE_SENT : TYPE_MESSAGE_RECEIVED;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_date, parent, false);
            return new DateViewHolder(view);
        } else {
            View view;
            if (viewType == TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false);
            }
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DATE) {
            ((DateViewHolder) holder).bind((String) chatList.get(position));
        } else {
            ((MessageViewHolder) holder).bind((ChatMessage) chatList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.tvItemChatDate);
        }

        public void bind(String date) {
            dateText.setText(date);
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderText, timestampText;
        ImageView ivAttachedImage, ivAttachedVideo;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.tvSender);
            timestampText = itemView.findViewById(R.id.textTimestamp);
            ivAttachedImage = itemView.findViewById(R.id.ivAttachedImage);
            ivAttachedVideo = itemView.findViewById(R.id.ivAttachedVideo);
        }

        public void bind(ChatMessage message) {
            senderText.setText(message.getSender().equals(currentUserId) ? "You" : message.getSender());
            timestampText.setText(message.getCreatedAt());

            if (message.getAttachmentType().equals("image")) {
                ivAttachedImage.setVisibility(View.VISIBLE);
                ivAttachedVideo.setVisibility(View.GONE);
                Glide.with(itemView.getContext()).load(message.getAttachmentUrl()).into(ivAttachedImage);
            } else if (message.getAttachmentType().equals("video")) {
                ivAttachedImage.setVisibility(View.GONE);
                ivAttachedVideo.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(message.getAttachmentUrl()).into(ivAttachedVideo);
            } else {
                ivAttachedImage.setVisibility(View.GONE);
                ivAttachedVideo.setVisibility(View.GONE);
            }
        }
    }
}
