package panel;

import chesspieces.Chess;

import javax.swing.*;
import java.awt.*;

public class EndChessPanel extends JPanel {
    Chess[] chessArray;
    int order;
    Image chessboard;
    Chess selected;
    Chess light;
    Point track;

    public EndChessPanel(ChessPanel chessPanel){//结束界面棋局展示
        chessArray=chessPanel.getChessArray();
        order=chessPanel.getOrder();
        chessboard=chessPanel.chessboard;
        selected=chessPanel.selected;
        light=chessPanel.light;
        track=chessPanel.track;
        repaint();
    }

    public void paint(Graphics g){
        //画棋盘
        int boardHeight;//旋转棋盘后会导致棋子错位，所以需要调整棋盘长度，确保棋子落在交线上
        if (order == 0) {
            boardHeight = this.getHeight() + 15;
        } else {
            boardHeight = this.getHeight();
        }
        g.drawImage(chessboard, 0, 0, this.getWidth(), boardHeight, this);

        //画棋子
        for (Chess chess : chessArray) {
            chess.drawPieces(g, this);
        }

        drawLight(g);
        drawTrack(g);
    }

    public void drawLight(Graphics g){//画棋子光效
        if(selected!=null){
            light=null;
            //System.out.println(selected.getName());
            selected.drawFeedback(g,this);
        }
        if(light!=null){
            light.drawFeedback(g,this);
        }
    }

    public void drawTrack(Graphics g){//画路径光效
        if(selected!=null){
            track=null;
        }
        if(track!=null) {
            Chess chess=new Chess();
            Point p=chess.pointToXY(track);
            Image light = Toolkit.getDefaultToolkit().getImage("resources/imgs/圆形光晕1.png");
            g.drawImage(light,p.x+11,p.y+9,45,45,this);
        }
    }

}
