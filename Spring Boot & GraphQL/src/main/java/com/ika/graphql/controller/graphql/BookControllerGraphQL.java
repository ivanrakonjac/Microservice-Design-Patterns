package com.ika.graphql.controller.graphql;

import com.ika.graphql.dtos.graphql.BookInput;
import com.ika.graphql.entity.Author;
import com.ika.graphql.entity.Book;
import com.ika.graphql.repository.AuthorRepository;
import com.ika.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookControllerGraphQL {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @QueryMapping
    List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    Book bookById(@Argument Long id) {
        log.info("bookById");
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Author not found!"));
    }

    @MutationMapping
    Book addBook(@Argument BookInput book) {

        Author a = authorRepository.findById(book.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("Author not found!"));
        Book b = Book.builder().name(book.getName()).author(a).build();

        bookRepository.save(b);

        return b;
    }

}
