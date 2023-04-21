package com.iceval.bookstore.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookDto {
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 100, message = "Author should be between 1 and 100 characters")
    private String author;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Date should not be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}

