package chesspieces;

import panel.ChessPanel;

import java.awt.*;

public class BlackCannons extends Chess {
    public BlackCannons(String s,int order,int id){
        this.setName("黑炮");
        this.setId(id);
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(2, 8));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 8));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(2, 3));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 3));
            }
        }
        this.setPlayer(1);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.inLine(preP,p)&&(ChessRule.countBlocked(preP,p,panel,0)==0||ChessRule.shell(preP,p,panel));
    }
}
