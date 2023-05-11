package com.ika.graphql.controller.rest;

import com.ika.graphql.entity.Paper;
import com.ika.graphql.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/papers")
@RequiredArgsConstructor
public class PaperController {

    private final PaperService paperService;

    @GetMapping("")
    public List<Paper> getAllPapers(){
        return paperService.getAllPapers();
    }
}