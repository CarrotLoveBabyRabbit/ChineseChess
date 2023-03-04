package chesspieces;

import panel.ChessPanel;
import java.awt.*;

public class Elephants extends Chess {
    public Elephants(String s, int order,int id) {
        this.setName("象");
        this.setId(id);
        if (order == 1) {//玩家先手
            if (s.equals("left")) {
                this.setP(new Point(3, 10));
            } else if (s.equals("right")) {
                this.setP(new Point(7, 10));
            }
        } else {//玩家后手
            if (s.equals("left")) {
                this.setP(new Point(3, 1));
            } else if (s.equals("right")) {
                this.setP(new Point(7, 1));
            }
        }
        this.setPlayer(1);
    }

    @Override
    public boolean isAbleMove(Point p, ChessPanel panel) {
        Point preP=this.getP();
        return ChessRule.getGrid(preP,p)==2&&ChessRule.inSlash(preP,p)&&!ChessRule.blockedEye(preP,p,panel)&&!ChessRule.crossRiver(preP.y,p.y);
    }
}
