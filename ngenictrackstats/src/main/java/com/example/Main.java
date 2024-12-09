package com.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        App app = new App();
        app.LoadTracks(new File(folderPath));
    }

    public static String folderPath = "./Data";
}