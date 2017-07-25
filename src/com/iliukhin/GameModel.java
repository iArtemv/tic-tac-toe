package com.iliukhin;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameModel {
    public static int SIZE = 3;
    public static int DOTS_TO_WIN = 3;
    public static final char DOT_EMPTY = '*';
    public static char DOT_USER;
    public static char DOT_PC;
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();
    public static int iWin = 0;
    public static int jWin = 0;

//    public static void main(String[] args) {
//        initMap();
//        printMap();
//        System.out.println("Размер поля: " + SIZE + " X " + SIZE);
//        System.out.println();
//        System.out.println("Чтобы выиграть нужно набрать: " + DOTS_TO_WIN + " подряд");
//        System.out.println();
//        selectSymble();
//        while (true) {
//            if (DOT_USER == 'X') {
//                humanTurn();
//                printMap();
//                if (checkWin(DOT_USER)) {
//                    System.out.println("Победил человек");
//                    break;
//                }
//                if (isMapFull()) {
//                    System.out.println("Ничья");
//                    break;
//                }
//                aiTurn();
//                printMap();
//                if (checkWin(DOT_PC)) {
//                    System.out.println("Победил Искуственный Интеллект");
//                    break;
//                }
//                if (isMapFull()) {
//                    System.out.println("Ничья");
//                    break;
//                }
//            } else {
//                aiTurn();
//                printMap();
//                if (checkWin(DOT_PC)) {
//                    System.out.println("Победил Искуственный Интеллект");
//                    break;
//                }
//                if (isMapFull()) {
//                    System.out.println("Ничья");
//                    break;
//                }
//                humanTurn();
//                printMap();
//                if (checkWin(DOT_USER)) {
//                    System.out.println("Победил человек");
//                    break;
//                }
//                if (isMapFull()) {
//                    System.out.println("Ничья");
//                    break;
//                }
//            }
//
//        }
//        System.out.println("Игра закончена");
//    }

    public GameModel() {
        initMap();
    }

    public static void selectSymble() {
        do {
            System.out.println("Выберете X или 0 для игры: ");
            DOT_USER = sc.next().toCharArray()[0];
        } while (DOT_USER != 'X' & DOT_USER != '0');
        if (DOT_USER == 'X') DOT_PC = '0';
        if (DOT_USER == '0') DOT_PC = 'X';
    }

    public static boolean checkWin(char symb) {

        int diag1 = 0;
        int diag2 = 0;
        for (int i = 0; i < SIZE; i++) {
            int row = 0;
            int column = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == symb) row++;
                if (map[j][i] == symb) column++;
                if (i == j && map[i][j] == symb) diag1++;
                if (i + j == SIZE - 1 && map[i][j] == symb) diag2++;
                if (row == DOTS_TO_WIN || column == DOTS_TO_WIN || diag1 == DOTS_TO_WIN || diag2 == DOTS_TO_WIN)
                    return true;
            }
        }

        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    public static void aiTurn() {
        int x = 0, y = 0, n, xCenter, yCenter;

        xCenter = (int) ((SIZE - 1) / 2);
        yCenter = (int) ((SIZE - 1) / 2);

        do {
            if (DOT_PC == 'X') {
                //Проверяем центральную ячейку, если она свободна, занимаем
                //Считаем размер таблицы нечетным
                n = 0;
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (isCellValid(i, j)) n++;
                    }
                }
                if (n == SIZE * SIZE) {
                    x = xCenter;
                    y = yCenter;
                    break;
                }

                //Проверяем, если пользователь сходил в недиагональную ячейку
                n = 0;
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (i % 2 == 0 & j % 2 == 0 & isCellValid(i, j)) {
                            if (map[i][j] == DOT_USER) n++;
                        }
                    }
                }
                if (n > 0 & !probeblyWin(DOT_USER)) {
                    int it = 0;
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map.length; j++) {
                            if (isCellValid(i, j) & it == 0) {
                                x = i;
                                y = j;
                                it++;
                            }
                        }
                    }
                    if (it > 0) break;
                }

                if (probeblyWin(DOT_PC)) {
                    x = jWin;
                    y = iWin;
                    if (isCellValid(x, y)) break;
                }
                if (probeblyWin(DOT_USER)) {
                    x = jWin;
                    y = iWin;
                    if (isCellValid(x, y)) break;
                }

                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);

            } else {
                if (isCellValid(xCenter, yCenter)) {
                    //Проверяем центральную ячейку, если она свободна, занимаем
                    //Считаем размер таблицы нечетным
                    n = 0;
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map[i].length; j++) {
                            if (isCellValid(i, j)) n++;
                        }
                    }
                    if (n == SIZE * SIZE - 1) {
                        x = xCenter;
                        y = yCenter;
                        break;
                    }

                }
                //Проверяем, если пользователь сходил в недиагональную ячейку
                n = 0;
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (i % 2 == 0 & j % 2 == 0 & isCellValid(i, j)) {
                            if (map[i][j] == DOT_USER) n++;
                        }
                    }
                }
                if (n > 0 & !probeblyWin(DOT_USER)) {
                    int it = 0;
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map.length; j++) {
                            if (isCellValid(i, j) & it == 0) {
                                x = i;
                                y = j;
                                it++;
                            }
                        }
                    }
                    if (it > 0) break;
                }

                if (probeblyWin(DOT_PC)) {
                    x = iWin;
                    y = jWin;
                    if (isCellValid(x, y)) break;
                }
                if (probeblyWin(DOT_USER)) {
                    x = iWin;
                    y = jWin;
                    if (isCellValid(x, y)) break;
                }

                System.out.println("Rendom");
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            }

        } while (!isCellValid(x, y));
        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        map[x][y] = DOT_PC;
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_USER;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (map[x][y] == DOT_EMPTY) {
            return true;
        }
        return false;
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean probeblyWin(char symb) {
        int n;
        int[] winArray = new int[DOTS_TO_WIN];

        //Проверяем по горизонтали
        for (int i = 0; i < map.length; i++) {
            n = 0;
            for (int j = 0; j < map[i].length; j++) {
                if (n < DOTS_TO_WIN) {
                    if (map[i][j] == symb) {
                        winArray[n] = 1;
                    } else {
                        winArray[n] = 0;
                        iWin = i;
                        jWin = j;
                    }
                    n++;
                }
            }
            Arrays.sort(winArray);
            if (winArray[1] == 1) {
                return true;
            }
        }

        //Проверяем по вертикали
        winArray = new int[DOTS_TO_WIN];
        iWin = 0;
        jWin = 0;
        for (int i = 0; i < map.length; i++) {
            n = 0;
            for (int j = 0; j < map[i].length; j++) {
                if (n < DOTS_TO_WIN) {
                    if (map[j][i] == symb) {
                        winArray[n] = 1;
                    } else {
                        winArray[n] = 0;
                        iWin = j;
                        jWin = i;
                    }
                    n++;
                }
            }
            Arrays.sort(winArray);
            if (winArray[1] == 1) {
                return true;
            }
        }

        //Проверяем диагональ
        n = 0;
        winArray = new int[DOTS_TO_WIN];
        iWin = 0;
        jWin = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == j) {
                    if (n < DOTS_TO_WIN) {
                        if (map[i][j] == symb) {
                            winArray[n] = 1;
                        } else {
                            winArray[n] = 0;
                            iWin = i;
                            jWin = j;
                        }
                        n++;
                    }

                }
            }
        }

        Arrays.sort(winArray);
        if (winArray[1] == 1) {
            return true;
        }

        //Проверяем обратную диагональ
        n = 0;
        winArray = new int[DOTS_TO_WIN];
        iWin = 0;
        jWin = 0;
        for (
                int i = 0;
                i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i + j == winArray.length - 1) {
                    if (n < DOTS_TO_WIN) {
                        if (map[i][j] == symb) {
                            winArray[n] = 1;
                        } else {
                            winArray[n] = 0;
                            iWin = i;
                            jWin = j;
                        }
                        n++;
                    }
                }
            }
        }
        Arrays.sort(winArray);
        if (winArray[1] == 1) {
            return true;
        }
        return false;
    }

    public static void setDotUser(char dotUser) {
        DOT_USER = dotUser;
    }
}
