package com.example.converttoblack_white;

public class FilePath {
    private static String filePath = "";
    private static final String repositoryPath = "/storage/emulated/0/Android/data/com.example.converttoblack_white/files/Pictures";

    public static String getRepositoryPath() {
        return repositoryPath;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        FilePath.filePath = filePath;
    }
}
