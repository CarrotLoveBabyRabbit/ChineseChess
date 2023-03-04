package gameinterface;

import panel.StartPanel;

import javax.swing.*;
import java.awt.*;

public class StartWin extends JFrame {
    public void launch(){
        this.setSize(810,765);
        //不可改变窗口大小，不然组件显示会有问题。系统缩放为125%
        this.setResizable(false);
        //居中
        this.setLocationRelativeTo(null);
        //退出同时结束进程
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setTitle("中国象棋");
        //加入开始面板
        this.add(new StartPanel(new GridLayout(1,2),this));
        this.setVisible(true);
    }

    public static void main(String[] args){
        StartWin startWin=new StartWin();
        startWin.launch();
    }
}
