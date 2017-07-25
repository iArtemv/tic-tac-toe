package com.iliukhin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameField extends JPanel{
    private int width;
    private int height;
    public final int SIZE = 3;
    private int cellWidth;
    private int cellHeight;
    //private char[][] map;
    public GameModel gm;


    public GameField(GameModel gm){
        //new char[SIZE][SIZE];
        this.gm = gm;
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                //System.out.println(e.getX() + " " + e.getY());
                //System.out.println(cellHeight + " " + cellWidth);

                int clX = e.getX() / cellWidth;
                int clY = e.getY() / cellHeight;

                if (!gm.isCellValid(clX, clY)) {
                    return;
                }

                //System.out.println(clX + " " + clY);
                GameModel.DOT_USER = 'X';
                GameModel.DOT_PC = '0';
                gm.map[clX][clY] = GameModel.DOT_USER;
                if (gm.checkWin(GameModel.DOT_USER)) {
                    System.out.println("Победил человек");
                    repaint();
                    JOptionPane.showMessageDialog(null,"Победил человек");
                    return;
                }
                if (gm.isMapFull()) {
                    System.out.println("Ничья");
                    repaint();
                    JOptionPane.showMessageDialog(null,"Ничья");
                    return;
                }
                gm.aiTurn();
                if (gm.checkWin(GameModel.DOT_PC)) {
                    System.out.println("Победил Искуственный Интеллект");
                    repaint();
                    JOptionPane.showMessageDialog(null,"Победил Искуственный Интеллект");
                    return;
                }
                if (gm.isMapFull()) {
                    System.out.println("Ничья");
                    repaint();
                    JOptionPane.showMessageDialog(null,"Ничья");
                    return;
                }
                repaint();
            }
        }); //MouseAdapter позволять выбрать событие мышки
    }


    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        width = getWidth();
        height = getHeight();
        g.fillRect(0,0, width,height);
        cellHeight = height/SIZE;
        cellWidth = width/SIZE;
        g.setColor(Color.BLACK);
        for(int i=0;i<SIZE+1;i++){
            g.drawLine(0,i*cellHeight, width, i*cellHeight); //горизонтальная линия
            g.drawLine(i*cellWidth, 0, i*cellWidth, height); //вертикальная линия
        }
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                if(gm.map[i][j] == '0'){
                    g.setColor(Color.RED);
                    g.fillOval(i*cellWidth + 10, j*cellHeight + 10, cellWidth - 20, cellHeight - 20); //мы преобразуем координаты клетки в координаты поля
                }
                if(gm.map[i][j] == 'X'){
                    g.setColor(Color.BLUE);
                    g.drawLine((i)*cellWidth,(j)*cellHeight,(i+1)*cellWidth,(j+1)*cellHeight);
                    g.drawLine((i)*cellWidth,(j+1)*cellHeight,(i+1)*cellWidth,(j)*cellHeight);
                }
            }
        }

    }
}
