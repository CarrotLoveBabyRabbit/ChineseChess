package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Guards extends Chess{
    public Guards(String s,int order,int id){
        this.setName("仕");
        this.setId(id);
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(4, 10));
            } else if (s.equals("right")) {
                this.setP(new Point(6, 10));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(4, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(6, 1));
            }
        }
        this.setPlayer(1);
    }

    @Override
    public boolean isAbleMove(Point p,ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.getGrid(preP,p)==1&&ChessRule.inPalace(preP,p)&&ChessRule.inSlash(preP,p);
    }
}
