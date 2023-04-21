package com.iceval.bookstore;

import com.iceval.bookstore.bussinesslogic.services.BookService;
import com.iceval.bookstore.dataaccess.postgresql.entities.BookEntity;
import com.iceval.bookstore.dataaccess.postgresql.repositories.BookRepository;
import com.iceval.bookstore.domain.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookstoreServiceTests {

	@InjectMocks
	private BookService bookService;
	@Mock
	private BookRepository bookRepository;

	private int getRandomInt() {
		return new Random().ints(1, 10).findFirst().getAsInt();
	}
	@Test
	void getBooks_ShouldReturnBooks() {
		// arrange
		when(bookRepository.findAll()).thenReturn(List.of(new BookEntity(), new BookEntity()));

		// act

		// assert
		assertThat(bookService.getBooks()).hasSize(2);
		verify(bookRepository, times(1)).findAll();
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void getBookById_ShouldReturnBook() {
		// arrange
		var expectedBook = new BookEntity();
		expectedBook.setAuthor("Толстой");
		expectedBook.setTitle("Война и мир");
		expectedBook.setDate(LocalDate.of(2023, 4, 21));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(expectedBook));

		// act
		final var book = bookService.getBookById(getRandomInt());

		// assert
		assertThat(book).usingRecursiveComparison().isEqualTo(expectedBook);
		verify(bookRepository, times(1)).findById(anyInt());
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void getBookById_ShouldNotFound() {
		// arrange
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

		// act

		// assert
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBookById(getRandomInt()));
		verify(bookRepository, times(1)).findById(anyInt());
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void addBook_ShouldAddBook() {
		// arrange
		var bookToAdd = new BookEntity();
		bookToAdd.setAuthor("Толстой");
		bookToAdd.setTitle("Война и мир");
		bookToAdd.setDate(LocalDate.of(2023, 4, 21));

		// act
		bookService.addBook(bookToAdd);

		// assert
		verify(bookRepository, times(1)).save(any(BookEntity.class));
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void updateBook_ShouldUpdateBook() {
		// arrange
		var book = new BookEntity();
		book.setAuthor("Достоевский");
		book.setTitle("Преступление и наказание");
		book.setDate(LocalDate.of(2022, 3, 11));
		var bookToUpdate = new BookEntity();
		bookToUpdate.setAuthor("Толстой");
		bookToUpdate.setTitle("Война и мир");
		bookToUpdate.setDate(LocalDate.of(2023, 4, 21));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		when(bookRepository.save(any(BookEntity.class))).thenReturn(book);

		// act
		bookService.updateBook(getRandomInt() ,bookToUpdate);

		// assert
		assertThat(book).usingRecursiveComparison().isEqualTo(bookToUpdate);
		verify(bookRepository, times(1)).findById(anyInt());
		verify(bookRepository, times(1)).save(any(BookEntity.class));
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void updateBook_ShouldNotFound() {
		// arrange
		var bookToUpdate = new BookEntity();
		bookToUpdate.setAuthor("Толстой");
		bookToUpdate.setTitle("Война и мир");
		bookToUpdate.setDate(LocalDate.of(2023, 4, 21));
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

		// act

		// assert
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.updateBook(getRandomInt(),bookToUpdate));
		verify(bookRepository, times(1)).findById(anyInt());
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void deleteBook_ShouldDeleteBook() {
		// arrange
		when(bookRepository.existsById(anyInt())).thenReturn(true);
		doNothing().when(bookRepository).deleteById(anyInt());

		// act
		bookService.deleteBook(getRandomInt());

		// assert
		verify(bookRepository, times(1)).existsById(anyInt());
		verify(bookRepository, times(1)).deleteById(anyInt());
		verifyNoMoreInteractions(bookRepository);
	}

	@Test
	void deleteBook_ShouldNotFound() {
		// arrange
		when(bookRepository.existsById(anyInt())).thenReturn(false);

		// act

		// assert
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(getRandomInt()));
		verify(bookRepository, times(1)).existsById(anyInt());
		verify(bookRepository, times(0)).deleteById(anyInt());
		verifyNoMoreInteractions(bookRepository);
	}

}
