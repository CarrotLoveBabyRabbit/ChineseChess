package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Mandarins extends Chess{
    public Mandarins(String s,int order,int id){
        this.setName("士");
        this.setId(id);
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(4, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(6, 1));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(4, 10));
            } else if (s.equals("right")) {
                this.setP(new Point(6, 10));
            }
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.getGrid(preP,p)==1&&ChessRule.inPalace(preP,p)&&ChessRule.inSlash(preP,p);
    }
}
