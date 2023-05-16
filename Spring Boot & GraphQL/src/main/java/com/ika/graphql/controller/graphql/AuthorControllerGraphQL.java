package com.ika.graphql.controller.graphql;

import com.ika.graphql.entity.Author;
import com.ika.graphql.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthorControllerGraphQL {

    private final AuthorRepository authorRepository;

    @QueryMapping
    List<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    Optional<Author> authorById(@Argument Long id) {
        log.info("authorById");
        return authorRepository.findById(id);
    }

}
