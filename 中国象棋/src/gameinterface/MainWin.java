package gameinterface;

import panel.ChessPanel;
import panel.EastPanel;

import javax.swing.*;
import java.awt.*;

public class MainWin extends JFrame {
    private ChessPanel cp;

    public ChessPanel getCp() {
        return cp;
    }

    public void launch(int order){
        this.setSize(810,765);
        //不可改变窗口大小，不然组件显示会有问题。系统缩放为125%
        this.setResizable(false);
        //居中
        this.setLocationRelativeTo(null);
        //退出同时结束进程
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setTitle("中国象棋");
        //设置区域布局
        this.setLayout(new BorderLayout());
        //按钮与显示面板
        EastPanel ep = new EastPanel(new GridLayout(4, 1), this);
        this.add(ep, BorderLayout.EAST);
        //棋盘面板
        cp=new ChessPanel(order,this, ep);
        this.add(cp, BorderLayout.CENTER);
        //显示可见
        this.setVisible(true);
    }

}
