package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Chariot extends Chess{
    public Chariot(String s,int order,int id){
        this.setName("车");
        this.setId(id);
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(1, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(9, 1));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(1, 10));
            } else if (s.equals("right")) {
                this.setP(new Point(9, 10));
            }
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.inLine(preP,p)&&ChessRule.countBlocked(preP,p,panel,1)==0;
    }
}
