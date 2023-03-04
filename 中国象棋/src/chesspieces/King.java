package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class King extends Chess {
    public King(int order,int id){
        this.setName("帅");

        this.setId(id);
        if(order==1) {
            this.setP(new Point(5, 1));
        }else{
            this.setP(new Point(5, 10));
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return (ChessRule.inPalace(preP,p)&&ChessRule.inLine(preP,p)&&ChessRule.getGrid(preP,p)==1)||ChessRule.generalsMeet(preP,p,panel);
    }
}
