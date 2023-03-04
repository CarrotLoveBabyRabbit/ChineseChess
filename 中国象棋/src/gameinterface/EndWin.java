package gameinterface;

import panel.ChessPanel;
import panel.EastPanel;
import panel.EndChessPanel;
import panel.EndPanel;


import javax.swing.*;
import java.awt.*;

public class EndWin extends JFrame {
    public void launch(int step,int winner,ChessPanel chessPanel){
        this.setSize(810,765);
        //不可改变窗口大小，不然组件显示会有问题。系统缩放为125%
        this.setResizable(false);
        //居中
        this.setLocationRelativeTo(null);
        //退出同时结束进程
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setTitle("中国象棋");
        //棋盘结束面板
        this.add(new EndChessPanel(chessPanel), BorderLayout.CENTER);
        this.setVisible(true);
        //加入结束面板
        EndPanel ep=new EndPanel(new GridLayout(3,1),step,winner,this);
        this.add(ep,BorderLayout.EAST);
    }

}
