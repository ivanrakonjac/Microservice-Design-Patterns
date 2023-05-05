package com.ika.graphql.controller;

import com.ika.graphql.entity.Book;
import com.ika.graphql.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
}
