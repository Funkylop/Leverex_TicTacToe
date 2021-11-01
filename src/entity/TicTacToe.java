package entity;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private final char[][] field = new char[3][3];
    private final char MOTION_X = 'X';
    private final char MOTION_O = 'O';
    private final char EMPTY_CELL = '-';

    public TicTacToe() {

    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose game mode: \n 1. Versus computer \n 2. Versus player \n 3. Exit");
        int modeCode = scanner.nextInt();
        while (true) {
            if (modeCode == 1 || modeCode == 2) {
                game(modeCode, scanner);
                break;
            } else if (modeCode == 3) {
                return;
            } else {
                System.out.println("You entered an incorrect value, try another value!");
                modeCode = scanner.nextInt();
            }
        }
    }

    private void game(int modeCode, Scanner scanner) {
        System.out.println("Choose sign for person 1: \n 1. O \n 2. X");
        char firstPlayerSign, secondPlayerSign;
        int signCode = scanner.nextInt();
        while(true) {
            if (signCode == 1) {
                firstPlayerSign = MOTION_O;
                secondPlayerSign = MOTION_X;
                break;
            } else if (signCode == 2) {
                firstPlayerSign = MOTION_X;
                secondPlayerSign = MOTION_O;
                break;
            } else {
                System.out.println("You entered an incorrect value, try another value!");
                signCode = scanner.nextInt();
            }
        }
        System.out.println("Sign of player 1 is " + firstPlayerSign + ", sign of player 2 is " + secondPlayerSign);
        int countOfMove = 0;
        createGameField();
        while (true) {
            printField();
            personMove(firstPlayerSign);
            countOfMove++;
            if (checkWin(firstPlayerSign)) {
                System.out.println("First player win!");
                printField();
                break;
            }
            printField();
            if (modeCode == 1) {
                compMove(secondPlayerSign);
            } else {
                personMove(secondPlayerSign);
            }
            countOfMove++;
            if (checkWin(secondPlayerSign)) {
                if (modeCode == 1) {
                    System.out.println("Computer win!");
                } else {
                    System.out.println("Second player win!");
                }
                printField();
                break;
            }
            if (countOfMove == Math.pow(field.length, 2)) {
                printField();
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    private boolean checkLine(int x, int y, char sign, int xRatio, int yRatio) {
        for (int i = 0; i < field.length; i++)
        {
         if (field[x + i * xRatio][y + i * yRatio] != sign) return false;
        }
        return true;
    }

    private boolean checkWin(char sign) {
        for(int i = 0; i < field.length; i++) {
            if (checkLine(0, i, sign, 1, 0)) return true;
            if (checkLine(i, 0, sign, 0, 1)) return true;
        }
        if (checkLine(0, 0, sign, 1, 1)) return true;
        if (checkLine(0, field.length - 1, sign, 1, -1)) return true;
        return false;
    }

    private void personMove(char sign) {
        Scanner scanner = new Scanner(System.in);
        int x, y;
        System.out.println("Choose X and Y (1..3). Make your move!");
        do {
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (isValidMove(x, y));
        field[x][y] = sign;
    }

    private void compMove(char sign) {
        Random random = new Random();
        int x, y;
        System.out.println("Computer's move!");
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        } while (isValidMove(x, y));
        field[x][y] = sign;
    }

    private boolean isValidMove(int x, int y) {
        if (x < 0 || y < 0 || x > field.length - 1 || y > field.length - 1){
            return true;
        }
        return field[x][y] != EMPTY_CELL;
    }

    private void createGameField() {
        for (char[] cell : field) {
            Arrays.fill(cell, EMPTY_CELL);
        }
    }

    private void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
}
