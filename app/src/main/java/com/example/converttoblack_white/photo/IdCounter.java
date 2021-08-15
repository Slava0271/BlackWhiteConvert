package com.example.converttoblack_white.photo;

public class IdCounter {
    private static long photoId = 1;

    public static long getPhotoId() {
        return photoId;
    }

    public static void incrementPhotoId() {
        IdCounter.photoId = photoId++;
    }
}
