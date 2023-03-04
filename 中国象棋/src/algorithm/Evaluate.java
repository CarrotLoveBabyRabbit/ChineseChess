package algorithm;

import chesspieces.Chess;
import panel.ChessPanel;

import java.util.Map;

public class Evaluate {
    //只实现了基本分数与位置分数
    public static int KINGSCORE=10000;
    public static int CHARIOTSCORE=1300;
    public static int KNIGHTSCORE=490;
    public static int CANNONSCORE=610;
    public static int ELEPHANTSCORE=200;
    public static int GUARDSCORE=200;
    public static int SOLDIERSCORE=100;

    //棋子基本分数
    public static final int[] chessBaseScore=new int[]{//根据棋子id赋基本分数
            //红方
            KINGSCORE,GUARDSCORE,GUARDSCORE,ELEPHANTSCORE,ELEPHANTSCORE,CHARIOTSCORE,CHARIOTSCORE,KNIGHTSCORE,KNIGHTSCORE,CANNONSCORE,CANNONSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,
            //黑方
            KINGSCORE,GUARDSCORE,GUARDSCORE,ELEPHANTSCORE,ELEPHANTSCORE,CHARIOTSCORE,CHARIOTSCORE,KNIGHTSCORE,KNIGHTSCORE,CANNONSCORE,CANNONSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,SOLDIERSCORE,
    };


    //棋子位置分数
    //马
    public final int[] blackKnightAttach ={
            -60 ,-35 ,-20,-20,-20,-20,-20,-35,-60,
            -35 ,0   ,+20,+20,-70,+20,+20,0  ,-35,
            -35 ,0   ,+20,+20,+20,+20,+20,0  ,-35,
            -35 ,0   ,+20,+20,+56,+20,+20,0  ,-35,
            -35 ,+40 ,+40,+50,+60,+50,+40,+40,-35,

            -30 ,+45,+60,+70,+70,+70,+60,+45,-30,
            -30 ,+50,+60,+75,+75,+75,+60,+50,-30,
            -30 ,+50,+80,+90,+90,+90,+80,+50,-30,
            -30 ,+50,+90,+80,+40,+80,+90,+50,-30,
            -60 ,+10,+20,+20,-20,+20,+20,+10,-60
    };

    //炮
    public  final int[] blackGunAttach ={
            -50 ,-20,-20,-20,-20,-20,-20,-20,-50,
            -20 ,+30,+40,+50,+30,+50,+40,+30,-20,
            -20 ,+30,+40,+50,+60,+50,+40,+30,-20,
            -20 ,+30,+40,+40,+60,+40,+40,+30,-20,
            -20 ,+30,+45,+45,+60,+45,+45,+30,-20,

            -20 ,+20,+20,+20,+51,+20,+20,+20,-20,
            -20 ,+20,+20,+10,+50,+10,+20,+20,-20,
            -20 ,+20,+20,0  ,0  ,0  ,+20,+20,-20,
            -20 ,+20,+20,0  ,0  ,0  ,+20,+20,-20,
            -30 ,+50,+30,+10,-10,+10,+30,+50,-30
    };
    //车
    public  final int[] blackChariotAttach ={
            -60,-20,-20,-20,-20,-20,-20,-20,-60,
            -20,+10,+10,+30,-40,+30,+10,+10,-20,
            -20,+15,+15,+30,+10,+30,+15,+15,-20,
            -20,+30,+30,+30,+40,+30,+30,+30,-20,
            -20,+50,+50,+80,+60,+80,+50,+50,-20,

            -20,+50,+50,+80,+60,+80,+50,+50,-20 ,
            -20,+40,+40,+50,+50,+50,+40,+40,-20 ,
            -20,+40,+40,+50,+50,+50,+40,+40,-20 ,
            -20,+40,+40,+60,+60,+60,+40,+40,-20 ,
            -30,+20,+20,+20,+20,+20,+20,+20,-30
    };
    //卒
    public  final int[] blackSoldierAttach ={

            0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,+15,0  ,0  ,0  ,0
            ,+20,0  ,+45,0  ,+35,0  ,+45,0  ,+20
            ,+80,+100,+120 ,+120,+120,+120,+120,+100,+80
            ,+100,+120,+150 ,+180,+180,+180,+150,+120,+100
            ,+100,+150,+200,+250,+250,+250,+200,+150,+100
            ,+100,+150,+200,+250,+300,+250,+200,+150,+100
            ,+100,+100,+100,+100,+100,+100,+100,+100,+100
    };
    //象
    public  final int[] ElephantAttach ={

            0  ,0  ,15 ,0  ,0  ,0  ,15 ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,-10,0  ,0  ,0  ,40 ,0  ,0  ,0  ,-10
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,-10,0  ,0  ,0  ,-10,0  ,0

            ,0  ,0  ,-10,0  ,0  ,0  ,-10,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,-10,0  ,0  ,0  ,40 ,0  ,0  ,0  ,-10
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,15 ,0  ,0  ,0  ,15 ,0  ,0

    };
    //士
    public  final int[] GuardAttach ={

            0  ,0  ,0  ,5  ,0  ,5  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,5 ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,10 ,0  ,10 ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0

            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,10 ,0  ,10 ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,5 ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,5  ,0  ,5  ,0  ,0  ,0
    };
    //王
    public  final int[] kingAttach ={
            0  ,0  ,0  ,10 ,20 ,10 ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0

            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0
            ,0  ,0  ,0  ,10 ,20 ,10 ,0  ,0  ,0
    };

    public   final int[] redSoldierAttach=exchange(blackSoldierAttach);
    public   final int[] redKnightAttach=exchange(blackKnightAttach);
    public   final int[] redChariotAttach=exchange(blackChariotAttach);
    public   final int[] redGunAttach=exchange(blackGunAttach);

    //棋子位置分数(先手开始，红在下，黑在上）
    public final int[][] chessSiteScore= new int[][]{
            kingAttach,GuardAttach,GuardAttach,ElephantAttach,ElephantAttach,redChariotAttach,redChariotAttach,redKnightAttach,redKnightAttach,redGunAttach,redGunAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,
            kingAttach,GuardAttach,GuardAttach,ElephantAttach,ElephantAttach,blackChariotAttach,blackChariotAttach,blackKnightAttach,blackKnightAttach,blackGunAttach,blackGunAttach,blackChariotAttach,blackSoldierAttach,blackSoldierAttach,blackSoldierAttach,blackSoldierAttach,
    };

    ////棋子位置分数(后手开始，红在上，黑在下）
    public final int[][] chessSiteScoreTurnDown= new int[][]{
            kingAttach,GuardAttach,GuardAttach,ElephantAttach,ElephantAttach,blackChariotAttach,blackChariotAttach,blackKnightAttach,blackKnightAttach,blackGunAttach,blackGunAttach,blackChariotAttach,blackSoldierAttach,blackSoldierAttach,blackSoldierAttach,blackSoldierAttach,
            kingAttach,GuardAttach,GuardAttach,ElephantAttach,ElephantAttach,redChariotAttach,redChariotAttach,redKnightAttach,redKnightAttach,redGunAttach,redGunAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,redSoldierAttach,
    };

    public int eval(ChessPanel chessPanel) {//计算评估函数
        int[][] values = new int[2][2];
        for (Chess chess : chessPanel.getChessArray()) {
            if (!chess.isDead()) {
                if (chess.getPlayer() == 0) {
                    values[0][0] += chessBaseScore[chess.getId()];
                    if (chessPanel.getOrder() == 0) {
                        values[0][1] += chessSiteScore[chess.getId()][chess.getP().x - 1 + (chess.getP().y - 1) * 9];
                    } else {
                        values[0][1] += chessSiteScoreTurnDown[chess.getId()][chess.getP().x - 1 + (chess.getP().y - 1) * 9];
                    }
                } else {
                    values[1][0] += chessBaseScore[chess.getId()];
                    if (chessPanel.getOrder() == 0) {
                        values[1][1] += chessSiteScore[chess.getId()][chess.getP().x - 1 + (chess.getP().y - 1) * 9];
                    } else {
                        values[1][1] += chessSiteScoreTurnDown[chess.getId()][chess.getP().x - 1 + (chess.getP().y - 1) * 9];
                    }
                }
            }
        }
        int sumRed = values[0][0] + values[0][1], sumBlack = values[1][0] + values[1][1];

        return sumRed-sumBlack;
    }



    public static int[] exchange(int[] srcArray) {
        int[] temp = arrayCopy(srcArray);
        for (int srcSite = 48; srcSite < 8 * 16; srcSite++) {
            int row = srcSite / 16;
            int col = srcSite % 16;
            int destSite = (15 - row) * 16 + col;
            int srcSiteTrue=boardMap[srcSite];
            int destSiteTrue=boardMap[destSite];
            int t = temp[srcSiteTrue];
            temp[srcSiteTrue] = temp[destSiteTrue];
            temp[destSiteTrue] = t;
        }
        return temp;
    }

    public static int[] arrayCopy(int[] srcArray) {
        int length = srcArray.length;
        int[] toArray = new int[length];
        System.arraycopy(srcArray, 0, toArray, 0, length);
        return toArray;
    }

    public static final int[] boardMap ={
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,1,2,3,4,5,6,7,8,0,0,0,0,
            0,0,0,9,10,11,12,13,14,15,16,17,0,0,0,0,
            0,0,0,18,19,20,21,22,23,24,25,26,0,0,0,0,
            0,0,0,27,28,29,30,31,32,33,34,35,0,0,0,0,
            0,0,0,36,37,38,39,40,41,42,43,44,0,0,0,0,
            0,0,0,45,46,47,48,49,50,51,52,53,0,0,0,0,
            0,0,0,54,55,56,57,58,59,60,61,62,0,0,0,0,
            0,0,0,63,64,65,66,67,68,69,70,71,0,0,0,0,
            0,0,0,72,73,74,75,76,77,78,79,80,0,0,0,0,
            0,0,0,81,82,83,84,85,86,87,88,89,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
    };

    private int evalPieceControl() {
        return 0;
    }

    private int evalPieceFlexible(int p) {
        // b | s | x | m | j | p | z
        int[] pieceFlexible = new int[]{0, 1, 1, 13, 7, 7, 15};
        return 0;
    }

    private int evalPieceProtect() {
        return 0;
    }

    private int evalPieceFeature() {
        return 0;
    }



}
