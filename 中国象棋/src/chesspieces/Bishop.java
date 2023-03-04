package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Bishop extends Chess{
    public Bishop(String s,int order,int id){
        this.setName("相");
        this.setId(id);
        if(order==1) {//玩家后手
            if (s.equals("left")) {
                this.setP(new Point(3, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(7, 1));
            }
        }else{//玩家先手
            if(s.equals("left")) {
                this.setP(new Point(3,10));
            }else if(s.equals("right")){
                this.setP(new Point(7, 10));
            }
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.getGrid(preP,p)==2&&ChessRule.inSlash(preP,p)&&!ChessRule.blockedEye(preP,p,panel)&&!ChessRule.crossRiver(preP.y,p.y);
    }
}
