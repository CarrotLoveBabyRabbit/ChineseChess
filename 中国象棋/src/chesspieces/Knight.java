package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Knight extends Chess {
    public Knight(String s,int order,int id){
        this.setName("馬");
        this.setId(id);
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(2, 10));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 10));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(2, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 1));
            }
        }
        this.setPlayer(1);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.stumble(preP,p,panel);
    }
}
