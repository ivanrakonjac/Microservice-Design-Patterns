package com.ika.graphql.service;

import com.ika.graphql.entity.Author;
import com.ika.graphql.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Optional<Author> findById(long id){ return authorRepository.findById(id); }
}
