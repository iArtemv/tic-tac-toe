package com.iliukhin;

import sun.awt.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame{
    public char dotUser;
    private GameModel gm;


    public TicTacToe(GameModel gm){
        this.gm = gm;
        setTitle("t-t-t");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300,300);
//        setLayout(BorderLayout);
        String[] items = {"X", "0"};

        JPanel jComboPanel = new JPanel();
        jComboPanel.setLayout(new FlowLayout());
        JComboBox editComboUser = new JComboBox(items);

        jComboPanel.add(editComboUser);
        editComboUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                dotUser = box.getSelectedItem().toString().charAt(0);
            }
        });

        JPanel jBottomPanel = new JPanel();
        jBottomPanel.setLayout(new FlowLayout());
        JButton jButtonStart = new JButton("Start new game");
        JButton jButtonEnd = new JButton("Exit");
        jBottomPanel.add(editComboUser);
        jBottomPanel.add(jButtonStart);
        jBottomPanel.add(jButtonEnd);

        jButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                GameModel gm = new GameModel();
                dispose();
                TicTacToe window = new TicTacToe(gm);
            }
        });
        jButtonEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0); //выход из программы без ошибок
            }
        });
        add(jBottomPanel, BorderLayout.SOUTH); //указываем какой элемент и куда
//        add(jComboPanel, BorderLayout.NORTH);

        GameField gf = new GameField(gm);
        add(gf, BorderLayout.CENTER);

        setVisible(true);
    }
}
