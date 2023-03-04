package chesspieces;

import javax.swing.*;
import java.awt.*;

import panel.ChessPanel;

public class Chess{
    private String name;
    private int x, y;//棋子像素坐标
    private Point p;//棋子网格坐标
    private int id;//棋子唯一标识码

    private static final int MARGIN = 5;//边界距离
    private static final int BREAK = 75;//棋子间间隔
    private final int SIZE = 70;//棋子大小
    private int player;//黑1红0
    private boolean isDead=false;//true为子被吃

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public Point getP() {
        return p;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  boolean isAbleMove(Point p, ChessPanel panel){
        return false;
    }

    public void pointToXY() {//坐标点转化为像素值
        x = BREAK * (p.x - 1) - MARGIN;
        y = BREAK * (p.y - 1) - MARGIN;
        if (p.x - 1 == 0) {
            x = 0;
        }
        if (p.y - 1 == 0) {
            y = 0;
        }
        if (p.x - 1 == 8) {
            x = 590;
        }
        if (p.y - 1 == 9) {
            y = 663;
        }
    }

    public Point pointToXY(Point p) {//坐标点转化为像素值
        x = BREAK * (p.x - 1) - MARGIN;
        y = BREAK * (p.y - 1) - MARGIN;
        if (p.x - 1 == 0) {
            x = 0;
        }
        if (p.y - 1 == 0) {
            y = 0;
        }
        if (p.x - 1 == 8) {
            x = 590;
        }
        if (p.y - 1 == 9) {
            y = 663;
        }
        return new Point(x,y);
    }

    public static Point getPFromXY(int x,int y){//像素值转化为坐标点
        Point p=new Point();
        p.x=(x+ MARGIN)/BREAK+1;
        p.y=(y+ MARGIN)/BREAK+1;
        return p;
    }

    public void killChess(Chess chess){//吃子把被吃子移除棋盘
        chess.setP(new Point(1000, 1000));
        chess.isDead=true;
    }

    public void reviveChess(Chess chess,Point point){//把被吃的棋子复活（用于悔棋或AI）
        chess.setP(point);
        chess.isDead=false;
    }

    public void drawPieces(Graphics g, JPanel panel) {//画单个棋子
        Image chesspieces = Toolkit.getDefaultToolkit().getImage("resources/imgs/" + name + ".png");
        pointToXY();
        //伪立体效果
        g.fillOval(x+1,y+3,SIZE+2,SIZE+2);
        g.setColor(Color.DARK_GRAY);
        g.drawImage(chesspieces, x, y, SIZE, SIZE, panel);
    }



    public void drawFeedback(Graphics g,JPanel panel){//画棋子点击反馈
        //棋子放大
        Image chesspieces = Toolkit.getDefaultToolkit().getImage("resources/imgs/" + name + ".png");
        pointToXY();
        g.drawImage(chesspieces, x-3, y-3, SIZE+6, SIZE+6, panel);
        //棋子发光
        Image light = Toolkit.getDefaultToolkit().getImage("resources/imgs/圆形光晕1.png");
        g.drawImage(light,x-7,y-8,SIZE+16,SIZE+16,panel);
    }

}
