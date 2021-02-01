package com.swarajdeshmukh.wordsearchapi.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Scope("prototype") //beacause each instance request needs to get a separate instance
public class WordGridService {


    private List<Coordinate> coordinates = new ArrayList<>();

    private enum Direction { //we can add more directions
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }

    private class Coordinate {
        int x;
        int y;
        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
    }


    //get a list of words and put them all in random coordinates
    public char[][] generateGrid(int gridSize, List<String> words) {

        char[][] contents = new char[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinate(i, j));
                contents[i][j] = '_';
            }
        }

        Collections.shuffle(coordinates); // We have define list and we are going one by one that define list is randomised by order

        for (String word : words) {
            for (Coordinate coordinate: coordinates) {
                int x = coordinate.x; //for each random coordinate
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(contents, word, coordinate);
                if(selectedDirection != null) {
                    switch (selectedDirection) {
                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        randomFillGrid(contents); //randomly filling remaining places
        return contents;
    }

    public void displayGrid(char[][] contents) {
            int gridSize = contents[0].length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(contents[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private void randomFillGrid(char[][] contents){
        int gridSize = contents[0].length;
        String allCapLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (contents[i][j] == '_'){
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j] = allCapLetters.charAt(randomIndex);
                }
            }
        }
    }

    private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate){ // size has to fit and them it has to look for each element and look if they are all underscores
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction: directions){ //for each random direction im gonna check if it fits
            if (doesFit(contents, word, coordinate, direction)){
                return direction;
            }
        }
        return null;
    }

    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction){
        int gridSize = contents[0].length;
        int wordLength = word.length();
        switch (direction){
            case HORIZONTAL:
                if (coordinate.y + wordLength > gridSize) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y + i] != '_') return false;
                }
                break;
            case VERTICAL:
                if (coordinate.x + wordLength > gridSize) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y] != '_') return false;
                }
                break;
            case DIAGONAL:
                if (coordinate.y + wordLength > gridSize ||  coordinate.x + wordLength > gridSize ) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y + i] != '_') return false;
                }
                break;
            case HORIZONTAL_INVERSE:
                if (coordinate.y < wordLength ) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y - i] != '_') return false;
                }
                break;
            case VERTICAL_INVERSE:
                if (coordinate.x < wordLength ) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y] != '_') return false;
                }
                break;
            case DIAGONAL_INVERSE:
                if (coordinate.x < wordLength || coordinate.y < wordLength ) return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y - i] != '_') return false;
                }
                break;
        }
        return true;
    }
}
