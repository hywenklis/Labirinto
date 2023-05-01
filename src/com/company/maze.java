package com.company;

public class maze {
    private static final char VERTICAL_WALL = '|';
    private static final char HORIZONTAL_WALL = '-';
    private static final char INTERNAL_WALL = '@';
    private static final char EMPTY = ' ';
    private static final char SIZE = 10;
    private static final char START = 'I';
    private static final char DESTINY = 'D';
    private static final char PATH = '.';
    private static final char NO_WAY_OUT = 'x';
    private static final double PROBABILITY = 0.7;
    private static int startLine;
    private static int startColumn;
    private static char[][] board;

    public static int generateNumber(int max, int min){
        int value = (int)Math.round(Math.random() * (max - min));
        return min + value;
    }

    public static boolean searchPath(int currentLine, int currentColumn){
        int nextLine;
        int nextColumn;
        boolean found = false;

        // tenta subir
        nextLine = currentLine -1;
        nextColumn = currentColumn;
        found = tryPath(nextLine, nextColumn);

        //tenta descer
        if (!found){
            nextLine = currentLine +1;
            nextColumn = currentColumn;
            found = tryPath(nextLine, nextColumn);
        }

        //tenta à esquerda
        if (!found){
            nextLine = currentLine;
            nextColumn = currentColumn -1;
            found = tryPath(nextLine, nextColumn);
        }

        //tenta à direita
        if (!found){
            nextLine = currentLine;
            nextColumn = currentColumn +1;
            found = tryPath(nextLine, nextColumn);
        }
        return found;
    }

    private static boolean tryPath(int nextLine, int nextColumn) {
        boolean found = false;
        if (board[nextLine][nextColumn] == DESTINY){
            found = true;
        }else if (emptyPosition(nextLine, nextColumn)){
            //marcar
            board[nextLine][nextColumn] = PATH;
            printout();
            found = searchPath(nextLine, nextColumn);
            if (!found){
                board[nextLine][nextColumn] = NO_WAY_OUT;
                printout();
            }
        }
        return found;
    }

    private static boolean emptyPosition(int line, int column) {
        boolean empty = false;
        if (line >= 0 && line < SIZE && column < SIZE){
            empty = (board[line][column] == EMPTY);
        }
        return empty;
    }

    public static void initializeMatrix() {
        int i, j;
        for (i = 0; i < SIZE; i++) {
            board[i][0] = VERTICAL_WALL;
            board[i][SIZE - 1] = VERTICAL_WALL;
            board[0][i] = HORIZONTAL_WALL;
            board[SIZE - 1][i] = HORIZONTAL_WALL;
        }
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                if (Math.random() > PROBABILITY){
                    board[i][j] = INTERNAL_WALL;
                } else {
                    board[i][j] = EMPTY;
                }
            }
        }
        startLine = generateNumber(1, SIZE /2-1);
        startColumn = generateNumber(1, SIZE / 2-1);
        board[startLine][startColumn] = START;

        int destinyLine = generateNumber(SIZE / 2, SIZE - 2);
        int destinyColumn = generateNumber(SIZE / 2, SIZE - 2);
        board[destinyLine][destinyColumn] = DESTINY;
    }


    public static void printout() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] Arg) {
        board = new char[SIZE][SIZE];
        initializeMatrix();
        printout();

        System.out.println("\n- Procurando solução -\n");
            boolean found = searchPath(startLine, startColumn);
            if(found){
                System.out.println("ACHOU CAMINHO");
            }else{
                System.out.println("NÂO ACHOU");
            }
    }
}