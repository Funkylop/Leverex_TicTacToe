package entity;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private char[][] field;
    private static final char EMPTY_CELL = '-';

    public TicTacToe() {
    }

    public final void menu() {
        final int modeVsPlayerCode = 1;
        final int modeVsCompCode = 2;
        final int exitCode = 3;
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Choose game mode:\s
                 1. Versus computer\s
                 2. Versus player\s
                 3. Exit""");
        int modeCode = scanner.nextInt();
        while (true) {
            if (modeCode == modeVsCompCode || modeCode == modeVsPlayerCode) {
                game(modeCode, scanner);
                break;
            } else if (modeCode == exitCode) {
                return;
            } else {
                System.out.println("You entered an incorrect value, try another value!");
                modeCode = scanner.nextInt();
            }
        }
    }
    private char[] chooseSign(final Scanner scanner) {
        System.out.println("Choose sign for player 1: \n 1. O \n 2. X");
        char[] playersSign = new char[2];
        int signCode = scanner.nextInt();
        while (true) {
            char signX = 'X';
            char signO = 'O';
            if (signCode == 1) {
                playersSign[0] = signO;
                playersSign[1] = signX;
                break;
            } else if (signCode == 2) {
                playersSign[0] = signX;
                playersSign[1] = signO;
                break;
            } else {
                System.out.println("You entered an incorrect value, try another value!");
                signCode = scanner.nextInt();
            }
        }
        System.out.println("Sign of player 1 is " + playersSign[0] + ", sign of player 2 is " + playersSign[1]);
        return playersSign;
    }

    private void game(final int modeCode, final Scanner scanner) {
        char[] playersSign = chooseSign(scanner);
        char firstPlayerSign = playersSign[0];
        char secondPlayerSign = playersSign[1];
        int countOfMove = 0;
        setFieldSize();
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

    private boolean checkLine(final int x, final int y, final char sign, final int xRatio, final int yRatio) {
        for (int i = 0; i < field.length; i++) {
            if (field[x + i * xRatio][y + i * yRatio] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin(final char sign) {
        for (int i = 0; i < field.length; i++) {
            if (checkLine(0, i, sign, 1, 0)) {
                return true;
            }
            if (checkLine(i, 0, sign, 0, 1)) {
                return true;
            }
        }
        if (checkLine(0, 0, sign, 1, 1)) {
            return true;
        }
        if (checkLine(0, field.length - 1, sign, 1, -1)) {
            return true;
        }
        return false;
    }

    private void personMove(final char sign) {
        Scanner scanner = new Scanner(System.in);
        int x;
        int y;
        System.out.println("Choose X and Y (1..3). Make your move!");
        do {
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (isValidMove(x, y));
        field[x][y] = sign;
    }

    private void compMove(final char sign) {
        Random random = new Random();
        int x;
        int y;
        System.out.println("Computer's move!");
        do {
            x = random.nextInt(field.length);
            y = random.nextInt(field.length);
        } while (isValidMove(x, y));
        field[x][y] = sign;
    }

    private boolean isValidMove(final int x, final int y) {
        if (x < 0 || y < 0
                || x > field.length - 1
                || y > field.length - 1) {
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
        for (char[] chars : field) {
            for (char cell : chars) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void setFieldSize() {
        int lineSize = 3;
        field = new char[lineSize][lineSize];
    }
}
