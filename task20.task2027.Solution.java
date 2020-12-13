package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/* 
Кроссворд
*/

public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };

        detectAllWords(crossword, "home", "same");
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> wordList = new ArrayList<>();
        int[][] directions = new int[][]{ {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };
        int matrixWidth = crossword[0].length;
        int matrixHeight = crossword.length;
        for (String nextWord : words) {
            int wordSize = nextWord.length();
            for (int j = 0; j < matrixHeight; j++) {
                for (int i = 0; i < matrixWidth; i++) {
                    if (nextWord.charAt(0) == (char) crossword[j][i]) {  // если первые буквы не совпадают, то ничего не делаем
                        for (int[] direction : directions) {
                            Word word = shrinkWord(crossword, i, j, direction[0], direction[1], wordSize);
                            if (word != null && nextWord.equals(word.text)) wordList.add(word);
                        }
                    }
                }
            }
        }
        return wordList;
    }

    public static Word shrinkWord(int[][] crossword, int startX, int startY, int dx, int dy, int letterCount) {
        StringBuilder stringBuilder = new StringBuilder();
        int endX = startX + dx * (letterCount - 1);
        int endY = startY + dy * (letterCount - 1);
        if (endX >= 0 && endY >= 0 && endX < crossword[0].length && endY < crossword.length) {
            for (int i = 0; i < letterCount; i++) {
                stringBuilder.append((char) crossword[startY + dy * i][startX + dx * i]);
            }
            Word result = new Word(stringBuilder.toString());
            result.setStartPoint(startX, startY);
            result.setEndPoint(endX, endY);
            return result;
        }
        return null;
    }


    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
