package com.b07group2.taamapp;

public class dbException extends RuntimeException {
    String message;

    public dbException(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(this.message);
    }
}
