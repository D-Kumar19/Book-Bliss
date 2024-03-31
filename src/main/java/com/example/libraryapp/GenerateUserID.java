package com.example.libraryapp;

import java.util.Map;
import android.annotation.SuppressLint;

public class GenerateUserID {
    public String checkUniqueID(Map<String, String> previousIDs) {
        String userID = generateUniqueID();
        while (previousIDs.containsKey(userID)) {
            userID = generateUniqueID();
        }
        return userID;
    }

    @SuppressLint("DefaultLocale")
    public String generateUniqueID() {
        return String.format("%07d", (int) (Math.random() * 10000000));
    }
}