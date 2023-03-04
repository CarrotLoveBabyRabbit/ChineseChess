package algorithm;

import chesspieces.Chess;
import panel.ChessPanel;

import java.awt.*;
import java.util.ArrayList;

public class SearchModel {
    private static final int DEPTH = 3;
    private ChessPanel chessPanel;
    private int nodeCount=0;//叶子节点数

    private AlphaBetaNode search(ChessPanel chessPanel,boolean isMax) {//对每一个根节点进行alpha-beta搜索，返回最佳节点
        this.chessPanel = chessPanel;
        long startTime = System.currentTimeMillis();
        AlphaBetaNode best = null;
        //生成所有着法
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(isMax);
        for (AlphaBetaNode node : moves) {
            //移动
            Chess eaten=chessPanel.nextMove(node.chess, node.to);
            node.setEaten(eaten);
            //计算根节点评估函数
            node.value = alphaBeta(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, !isMax);
//            System.out.println(node.chess.getName()+node.value);
            //最佳根节点评估函数
            if(isMax) {
                if (best == null || node.value >= best.value)
                    best = node;
                //System.out.println("进行最大值搜索"+"该着法为"+best.chess.getName()+"移动到"+best.to+"评估函数值为"+best.value);
            }else{
                if (best == null || node.value <= best.value)
                    best = node;
                //System.out.println("进行最小值搜索"+"该着法为"+best.chess.getName()+"移动到"+best.to+"评估函数值为"+best.value);
            }
            //返回
            chessPanel.backMove(node,eaten);
        }
        long finishTime = System.currentTimeMillis();
        System.out.println(best.toString());
        System.out.println("耗时："+(finishTime - startTime)+"毫秒\t 分数:"+best.value+"\t叶子节点："+nodeCount);

        return best;
    }


    private int alphaBeta(int depth, int alpha, int beta, boolean isMax) {//alpha-beta搜索  返回某根节点的最佳评估函数值
        /* Return evaluation if reaching leaf node or any side won.*/
        if (depth == 0 ||chessPanel.isGameEnd())
            return new Evaluate().eval(chessPanel);
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(isMax);

        nodeCount++;//每搜索一次即遍历一个叶子节点

        for (final AlphaBetaNode node : moves) {
            //移动
            Chess eaten=chessPanel.nextMove(node.chess, node.to);

            if (isMax) {
                alpha = Math.max(alpha, alphaBeta(depth - 1, alpha, beta, false));
            } else {
                beta = Math.min(beta, alphaBeta(depth - 1, alpha, beta, true));
            }
            //返回
            chessPanel.backMove(node,eaten);

            /* Cut-off */
            if (beta <= alpha) break;
        }
        return isMax ? alpha : beta;
    }

    private ArrayList<AlphaBetaNode> generateMovesForAll(boolean isMax) {//生成当前局面黑方或红方的所有着法
        ArrayList<AlphaBetaNode> moves = new ArrayList<>();
        for (Chess chess : chessPanel.getChessArray()) {
            if (isMax && chess.getPlayer() == 1) continue;//最大值搜索，则生成红子所有着法
            if (!isMax && chess.getPlayer() == 0) continue;//最小值搜索，生成黑子所有着法
            for (Point nxn : pointByRule(chess,chessPanel,isMax)) {
                moves.add(new AlphaBetaNode(chess, chess.getP(), nxn));//每个着法为博弈树的一个子节点
          //      System.out.println("可移动棋子为" + chess.getName() + "从" + chess.getP() + "移动到" +nxn);
            }
        }
        return moves;
    }

    private Point[] pointByRule(Chess chess,ChessPanel chessPanel,boolean isMax){//返回某个棋子所有符合规则的可移动到的网格坐标数组
        ArrayList<Point> points=new ArrayList<>();
        for (int x = 1; x < 10; x++) {
            for (int y = 1; y < 11; y++) {
                Point point=new Point(x,y);
                if(chessPanel.getChessByP(point)!=null) {
                    if (chessPanel.getChessByP(point).getPlayer() == 1 && isMax) {//红棋可以走黑棋所在位置(吃子），但是不能走红棋所在位置
                        points.add(new Point(x, y));
                    }else if (chessPanel.getChessByP(point).getPlayer() == 0 &&!isMax){//黑棋可以走红棋所在位置(吃子)，但是不能走红棋所在位置
                        points.add(new Point(x, y));
                    }
                } else{
                    points.add(new Point(x, y));
                }
            }
        }
        points.removeIf(point -> !chess.isAbleMove(point, chessPanel));
        Point[] pointArray=new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            pointArray[i]=points.get(i);
        }
        return pointArray;
    }

    public void responseMoveChess(ChessPanel chessPanel,boolean isMax) {
        //人机走子
        SearchModel searchModel = new SearchModel();
        AlphaBetaNode result = searchModel.search(chessPanel,isMax);
        chessPanel.moveByAI(result.to,result.chess,result.eaten);
    }
}
