package com.iceval.bookstore.domain.exceptions;

public class BookNotAddedException extends RuntimeException {
    public BookNotAddedException(String msg){
        super(msg);
    }
}
