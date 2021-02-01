package com.swarajdeshmukh.wordsearchapi.controllers;


import com.swarajdeshmukh.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("/")  //path segment mapped to the root
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    @GetMapping("/wordgrid") //Post would make sense rather than Get because this particular api is creating something new
    @CrossOrigin(origins = "http://localhost:1234") //hey API allow request coming from localhost:1234
    public String createWordGrid(@RequestParam int gridSize, @RequestParam String wordList){

        List<String> words = Arrays.asList(wordList.split(","));
        char[][] grid = wordGridService.generateGrid(gridSize,words);
        String gridToString = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString += grid[i][j] + " ";
            }
            gridToString += "\r\n";
        }
        return gridToString;
    }
}
