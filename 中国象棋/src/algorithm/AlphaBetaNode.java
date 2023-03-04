package algorithm;

import chesspieces.Chess;

import java.awt.*;

public class AlphaBetaNode {
    public Chess chess;
    public Point from;
    public Point to;
    public int value;
    public Chess eaten;

    public AlphaBetaNode(Chess chess, Point from, Point to) {
        this.chess = chess;
        this.from = from;
        this.to = to;
    }

    public void setEaten(Chess eaten) {
        this.eaten = eaten;
    }

    public String toString(){
        return "原位置:" + from.y + "行" + from.x + "列\t  原棋子：" + chess.getName() + "\t 目标位置：" + to.y + "行" + to.x + "列\t 被吃棋子：" + (eaten != null ? eaten.getName() : "无 \t");
    }
}
