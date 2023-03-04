package chesspieces;

import java.awt.*;
import panel.ChessPanel;

public class Cannons extends Chess{
    public Cannons(String s,int order,int id){
        this.setId(id);
        this.setName("炮");
        if(order==1) {
            if (s.equals("left")) {
                this.setP(new Point(2, 3));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 3));
            }
        }else{
            if (s.equals("left")) {
                this.setP(new Point(2, 8));
            } else if (s.equals("right")) {
                this.setP(new Point(8, 8));
            }
        }
        this.setPlayer(0);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        //中间棋子数要算上被吃那个，即不能直接吃那一个
        return ChessRule.inLine(preP,p)&&(ChessRule.countBlocked(preP,p,panel,0)==0||ChessRule.shell(preP,p,panel));
    }
}
