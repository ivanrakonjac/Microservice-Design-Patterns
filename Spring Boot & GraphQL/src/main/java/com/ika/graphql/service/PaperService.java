package com.ika.graphql.service;

import com.ika.graphql.entity.Paper;
import com.ika.graphql.repository.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;

    public List<Paper> getAllPapers(){
        return paperRepository.findAll();
    }

}
