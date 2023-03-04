package chesspieces;

import gameinterface.MainWin;
import panel.ChessPanel;
import panel.EastPanel;

import java.awt.*;

public class ChessRule {
    protected static boolean inPalace(Point preP,Point p){//九宫格内
        if(p.x < 4 || p.x > 6) {//对x的限制
            return false;
        }
        if(preP.y>7&&p.y<=7){//对y的限制
            return false;
        }else return preP.y >= 4 || p.y < 4;
    }

    protected static boolean inLine(Point preP,Point p){
        return p.y == preP.y || p.x == preP.x;
    }//同一条直线上

    protected static boolean inSlash(Point preP,Point p) {//在斜线上
        return Math.abs(p.y - preP.y) == Math.abs(p.x - preP.x);
    }

    protected static int getGrid(Point preP,Point p){//计算棋子与目标点之间的格子数，包括直斜线
        if (preP.x-p.x==0){
            return Math.abs(preP.y-p.y);
        }
        if (preP.y-p.y==0){
            return Math.abs(preP.x-p.x);
        }
        if (inSlash(preP,p)){
            return Math.abs(preP.x-p.x);
        }
        return 0;
    }

    protected static boolean blockedEye(Point preP,Point p, ChessPanel chessPanel){//堵象眼
        Point eye=new Point();
        eye.x=(preP.x+p.x)/2; eye.y=(preP.y+p.y)/2;
        return chessPanel.getChessByP(eye)!=null;
    }

    protected static boolean crossRiver(int yPreP,int yP){//过河
        if(yPreP<6&&yP>=6){
            return true;
        }
        return yPreP > 5 && yP <= 5;
    }

    protected static boolean stumble(Point preP,Point p,ChessPanel chessPanel) {//蹩马腿 走日字
        Point stone=new Point();
        if(Math.abs(preP.x - p.x) == 1 && Math.abs(preP.y - p.y) == 2){//往上下跳
            if(preP.y< p.y){//往下跳
                stone.y= preP.y+1;
            }else{//往上跳
                stone.y= preP.y-1;
            }
            stone.x= preP.x;
            if(chessPanel.getChessByP(stone)==null)
                return true;
        }
        if(Math.abs(preP.x - p.x) == 2 && Math.abs(preP.y - p.y) == 1){//往左右跳
            if(preP.x< p.x){//往右跳
                stone.x= preP.x+1;
            }else{//往左跳
                stone.x= preP.x-1;
            }
            stone.y= preP.y;
            return chessPanel.getChessByP(stone) == null;
        }
        return false;
    }

    protected static boolean noBackward(int preY,int y,int initY){//不后退
        if(initY==4&&preY<=y){
            return true;
        }else return initY == 7 && preY >= y;
    }

    protected static boolean onlyForward(Point preP,Point p,int initY){//只前进
        if(preP.x==p.x) {
            if (initY == 4 && preP.y <= p.y ) {
                return true;
            } else return initY == 7 && preP.y >= p.y;
        }
        return false;
    }

    protected static int countBlocked(Point preP,Point p,ChessPanel panel,int base){//计算目标点与棋子间的棋子数 base:0算上被吃的子 1不算上被吃的子
        int count=-1;//目标点和棋子不在一条直线
        Point tp=new Point(preP);
        if(tp.y==p.y) {
            count=0;
            while (p.x > tp.x+base) {
                    if (panel.getChessByP(new Point(tp.x + 1, tp.y)) != null) {
                        count++;
                    }
                    tp.x = tp.x + 1;
            }
            while (p.x < tp.x-base) {
                    if (panel.getChessByP(new Point(tp.x - 1, tp.y)) != null) {
                        count++;
                    }
                    tp.x = tp.x - 1;
            }
        }else if(tp.x==p.x){
            count=0;
            while (p.y > tp.y+base) {
                if (panel.getChessByP(new Point(tp.x,tp.y + 1)) != null) {
                    count++;
                }
                tp.y = tp.y + 1;
            }
            while (p.y < tp.y-base) {
                if (panel.getChessByP(new Point(tp.x,tp.y - 1 )) != null) {
                    count++;
                }
                tp.y = tp.y - 1;
            }
        }
        return count;
    }

    protected static boolean shell(Point preP,Point p,ChessPanel chessPanel){//炮弹飞跃
        //计算中间有几个棋时，不能算上被吃的那个，即base=1
        return countBlocked(preP, p, chessPanel, 1) == 1 && chessPanel.getChessByP(p) != null;
    }

    protected static boolean generalsMeet(Point preP,Point p,ChessPanel chessPanel){//将军碰面
        if(chessPanel.getChessByP(p)!=null){
            return (chessPanel.getChessByP(p).getName().equals("将") || chessPanel.getChessByP(p).getName().equals("帅")) && countBlocked(preP, p, chessPanel, 1)==0;
        }return false;
    }

}

//class StumbleTest {
//    public static void main(String[] args) {
//        // 创建桩程序
//        ChessPanel chessPanel = new ChessPanel() {
//            @Override
//            public Chess getChessByP(Point p) {
//                if (p.equals(new Point(3, 4))) {
//                    return new Chess();
//                } else {
//                    return null;
//                }
//            }
//        };
//
//        // 调用驱动程序
//        Point preP = new Point(3, 3);
//        Point p = new Point(2, 5);
//        boolean result = ChessRule.stumble(preP, p, chessPanel);
//
//        // 打印结果
//        System.out.println("Result: " + result);
//    }
//}




