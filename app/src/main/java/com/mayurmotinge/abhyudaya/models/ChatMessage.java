package com.mayurmotinge.abhyudaya.models;

public class ChatMessage {
    private String message;
    private String sender;
    private String senderId;
    private String createdAt;
    private String attachmentType;  // "image", "video", "text"
    private String attachmentUrl;

    public ChatMessage(String message, String sender, String senderId, String createdAt, String attachmentType, String attachmentUrl) {
        this.message = message;
        this.sender = sender;
        this.senderId = senderId;
        this.createdAt = createdAt;
        this.attachmentType = attachmentType;
        this.attachmentUrl = attachmentUrl;
    }

    public String getMessage() { return message; }
    public String getSender() { return sender; }
    public String getSenderId() { return senderId; }
    public String getCreatedAt() { return createdAt; }
    public String getAttachmentType() { return attachmentType; }
    public String getAttachmentUrl() { return attachmentUrl; }
}
