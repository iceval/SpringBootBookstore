package com.iceval.bookstore.bussinesslogic.services;

import com.iceval.bookstore.domain.exceptions.BookNotFoundException;
import com.iceval.bookstore.dataaccess.postgresql.entities.BookEntity;
import com.iceval.bookstore.dataaccess.postgresql.repositories.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBookById(int id){
        Optional<BookEntity> foundBook = bookRepository.findById(id);
        return foundBook.orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public void addBook(BookEntity bookEntity) {
        bookRepository.save(bookEntity);
    }

    @Transactional
    public void updateBook(int id, BookEntity newBookEntity) {
        bookRepository.findById(id)
            .map(book -> {
                book.setAuthor(newBookEntity.getAuthor());
                book.setTitle(newBookEntity.getTitle());
                book.setDate(newBookEntity.getDate());
                return bookRepository.save(book);
            })
            .orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)){
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }

}
