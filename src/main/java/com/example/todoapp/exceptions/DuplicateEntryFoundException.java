package com.example.todoapp.exceptions;

public class DuplicateEntryFoundException extends RuntimeException {
    public DuplicateEntryFoundException(String s) {
        super(s);
    }
}
