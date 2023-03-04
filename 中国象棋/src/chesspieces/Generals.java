package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Generals extends Chess{
    public Generals(int order,int id){
        this.setName("将");
        this.setId(id);
        if(order==1) {
            this.setP(new Point(5, 10));
        }else{
            this.setP(new Point(5, 1));
        }
        this.setPlayer(1);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return (ChessRule.inPalace(preP,p)&&ChessRule.inLine(preP,p)&&ChessRule.getGrid(preP,p)==1)||ChessRule.generalsMeet(preP,p,panel);
    }
}
