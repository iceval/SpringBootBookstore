package com.iceval.bookstore.api.controllers;

import com.iceval.bookstore.api.models.BookDto;
import com.iceval.bookstore.domain.exceptions.BookErrorResponse;
import com.iceval.bookstore.domain.exceptions.BookNotAddedException;
import com.iceval.bookstore.domain.exceptions.BookNotFoundException;
import com.iceval.bookstore.bussinesslogic.services.BookService;
import com.iceval.bookstore.dataaccess.postgresql.entities.BookEntity;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Api(description = "Bookstore")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<BookDto> getBooks(){
        return bookService.getBooks().stream().map(this::convertToBookDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") int id){
        return convertToBookDto(bookService.getBookById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addBook(@RequestBody @Valid BookDto bookDto,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new BookNotAddedException(errorMsg.toString());
        }

        bookService.addBook(convertToBookEntity(bookDto));

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDto bookDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new BookNotAddedException(errorMsg.toString());
        }

        bookService.updateBook(id, convertToBookEntity(bookDto));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotFoundException e) {
        BookErrorResponse response = new BookErrorResponse("Book with this id wasn't found!");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(BookNotAddedException e) {
        BookErrorResponse response = new BookErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private BookEntity convertToBookEntity(BookDto personDTO) {
        return modelMapper.map(personDTO, BookEntity.class);
    }

    private BookDto convertToBookDto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }
}
