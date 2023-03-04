package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Soldiers extends Chess{
    private final int initY;
    public Soldiers(int i,int order,int id){
        this.setName("兵");
        this.setId(id);
        if(order==1) {
            initY=4;
            if (i == 0) {
                this.setP(new Point(1, 4));
            } else if (i == 1) {
                this.setP(new Point(3, 4));
            } else if (i == 2) {
                this.setP(new Point(5, 4));
            } else if (i == 3) {
                this.setP(new Point(7, 4));
            } else if (i == 4) {
                this.setP(new Point(9, 4));
            }
        }else {
            initY=7;
            if (i == 0) {
                this.setP(new Point(1, 7));
            } else if (i == 1) {
                this.setP(new Point(3, 7));
            } else if (i == 2) {
                this.setP(new Point(5, 7));
            } else if (i == 3) {
                this.setP(new Point(7, 7));
            } else if (i == 4) {
                this.setP(new Point(9, 7));
            }
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        if(ChessRule.getGrid(preP,p)==1&&ChessRule.inLine(preP,p)) {
            if (ChessRule.crossRiver(initY, p.y)) {//过河卒
                return ChessRule.noBackward(preP.y, p.y, initY);
            } else {
                return ChessRule.onlyForward(preP, p, initY);
            }
        }
        return false;
    }
}
