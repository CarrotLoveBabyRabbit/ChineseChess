package panel;

import algorithm.AlphaBetaNode;
import algorithm.SearchModel;
import audio.AudioPlayer;
import chesspieces.Chess;
import chesspieces.ChessFactory;
import gameinterface.EndWin;
import chesspieces.Record;
import gameinterface.MainWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class ChessPanel extends JPanel {//主棋盘
    int order;//0表示先手，1表示后手
//    List<Chess> chessList=new ArrayList<>();
    Chess[] chessArray=new Chess[32];//存储32个棋子
    Chess selected;//存储被点击的棋子
    Chess light;//棋子发光提示
    Point track;//轨迹提示（移动前坐标）
    Image chessboard;//棋盘
    int player=0;//0为红方 1为黑方
    AudioPlayer audioPlayer;//音效播放器
    Stack<Record> repentStack=new Stack<>();//存储上一步双方棋子坐标
    MainWin mainWin;//主界面
    EastPanel eastPanel;//功能区域
    SearchModel searchModel=new SearchModel();//AI alpha-beta搜索模型主程序

    boolean isMax;//true最大值搜索
    boolean isPlayer;//true为玩家 false为AI

    public ChessPanel(){

    }

    public ChessPanel(int order,MainWin mainWin,EastPanel eastPanel) {
        this.mainWin = mainWin;
        this.order = order;
        this.eastPanel = eastPanel;
        initChess();
        eastPanel.countStep = 0;
        isMax = order == 1;//玩家执先手，人机为最小值搜索/  玩家执后手，人机为最大值搜索
        isPlayer=true;

        if(order==1) {//后手开局，人机直接走子
            isPlayer=false;
            System.out.println("====================AI正在计算====================");
            searchModel.responseMoveChess(ChessPanel.this, true);
            reverse(true);
            repaint();
        }

        //鼠标事件
        addMouseListener(new MouseAdapter() {//鼠标按压为玩家选子，移动，吃子
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (isPlayer) {//玩家控子
                    Point p = Chess.getPFromXY(e.getX(), e.getY());
                    //System.out.println("网格为("+p.x+","+p.y+")");
                    if (selected == null) {
                        //首次点击棋子，存储该棋子
                        if (order == 0 && player == 0) {//先手只能走红棋
                            selected = getChessByP(p);
                        }
                        if (order == 1 && player == 1) {//后手只能走黑棋
                            selected = getChessByP(p);
                        }
                        if (selected != null && selected.getPlayer() != player) {
                            selected = null;
                        }
                    } else {
                        //第二次点击(包含三种情况：重新选择，移动，吃子)
                        Chess chess2 = getChessByP(p);
                        if (chess2 == null) {//没点击棋子，即移动
                            if (selected.isAbleMove(p, ChessPanel.this)) {//移动要符合规则
                                move(p, null);
                                reverse(true);
                            } else {
                                System.out.println("不按规则移动");
                            }
                        }
                        if (chess2 != null) {//点击棋子
                            if (chess2.getPlayer() == selected.getPlayer()) {//同阵营重新选择
                                //reSelect()
                                selected = chess2;
                            } else {//不同阵营吃子
                                if (selected.isAbleMove(p, ChessPanel.this)) {
                                    move(p, chess2);
                                    chess2.killChess(chess2);
                                    reverse(true);
                                    if (chess2.getName().equals("将")) {
                                        endGame(0);
                                    } else if (chess2.getName().equals("帅")) {
                                        endGame(1);
                                    }
                                }
                            }
                        }
                    }
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {//鼠标松开AI开始计算
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(!isPlayer){
                    System.out.println("====================AI正在计算====================");
                    searchModel.responseMoveChess(ChessPanel.this, isMax);
                    //System.out.println("评估函数为" + evaluate.eval(ChessPanel.this));
                    reverse(true);
                    repaint();
                }
            }
        });

    }

    public void move(Point p,Chess chess2){//移动
        String audio;
        if(getChessByP(p)==null){
            audio="走棋";
            Record record = new Record(selected, selected.getP(), p);//棋局记录
            repentStack.push(record);
        }else{
            audio="吃子";
            Record record = new Record(selected, selected.getP(), p, chess2, chess2.getP());
            repentStack.push(record);
        }
        track = selected.getP();//起始位置光圈
        selected.setP(p);//移动
        audioPlayer = new AudioPlayer();
        audioPlayer.playAudio("resources/sounds/"+audio+".WAV");//播放音频
        light = selected;//结束位置光圈
        selected=null;
        }

        public void moveByAI(Point p,Chess chess,Chess eaten){//人机走子
            selected=chess;
            String audio;
            if(eaten==null){
                audio="走棋";
                audioPlayer = new AudioPlayer();
                audioPlayer.playAudio("resources/sounds/"+audio+".WAV");//播放音频
                Record record = new Record(selected, selected.getP(), p);
                repentStack.push(record);
            }else if(getChessByP(p).getName().equals("将")) {
                endGame(0);
            }else if (getChessByP(p).getName().equals("帅")) {
                endGame(1);
            }else{
                audio="吃子";
                audioPlayer = new AudioPlayer();
                audioPlayer.playAudio("resources/sounds/"+audio+".WAV");//播放音频
                Record record = new Record(selected, selected.getP(), p, eaten, eaten.getP());
                repentStack.push(record);
                chess.killChess(eaten);
            }
            track = selected.getP();//起始位置光圈
            selected.setP(p);//移动
            light = selected;//结束位置光圈
            selected=null;
        }

    public Chess nextMove(Chess chess,Point p) {//用于AI，遍历下一步时，若吃子，则要将被吃子移除并保存，计算完评估函数后再移回。
        Chess eaten = null;
        if (getChessByP(p) != null) {
            eaten = getChessByP(p);
            eaten.killChess(eaten);
        }
        chess.setP(p);

        return eaten;
    }

    public void backMove(AlphaBetaNode node, Chess eaten){//用于AI，搜索结束后，返回上一步，若有被吃棋子，复活该棋子而不只是移回原位
        node.chess.setP(node.from);
        if (eaten != null) {
            eaten.reviveChess(eaten,node.to);
        }

    }

    public Chess getChessByP(Point p){//根据网格坐标获取棋子对象
        for (Chess chess:chessArray) {
            if(chess.getP().equals(p)){
                return chess;
            }
        }
        return null;
    }

    public void reverse(boolean isCountStep){//攻防交换，并计算步数
        isPlayer= !isPlayer;
        if(player==0){
            player=1;
            if(order==0&&isCountStep) {
                    eastPanel.countStep++;
                } else if(order==1&&!isCountStep){
                    eastPanel.countStep--;
            }
            eastPanel.tipLabel.setText("黑方走");
        }else if(player==1){
            player=0;
            if(order==1&&isCountStep) {
                    eastPanel.countStep++;
                } else if(order==0&&!isCountStep){
                    eastPanel.countStep--;
                }
            eastPanel.tipLabel.setText("红方走");
            }
        eastPanel.stepLabel.setText(eastPanel.countStep+"步");
        }

    public void repentChess(){//悔棋
        int repentTimes=2;//点一次悔棋直接悔两步，即人机走的那一步与玩家走的那一步
        selected=null;
        for (int i = 0; i < repentTimes; i++) {
            if(!repentStack.isEmpty()) {//悔棋栈不为空
                light=null;
                track=null;
                Record record = repentStack.pop();
                record.getChess().setP(record.getStart());
                chessArray[record.getChess().getId()] = record.getChess();
                if (record.getEatenChess() != null) {
                    record.getEatenChess().reviveChess(record.getEatenChess(),record.getEatenStart());
                    chessArray[record.getEatenChess().getId()] = record.getEatenChess();
                }
                player=1-record.getChess().getPlayer();
                reverse(false);
                audioPlayer = new AudioPlayer();
                audioPlayer.playAudio("resources/sounds/"+"走棋"+".WAV");
            }
        }
        repaint();
    }

    public void endGame(int winner){//结束游戏
        audioPlayer=new AudioPlayer();
        audioPlayer.playAudio("resources/sounds/结束.WAV");
        mainWin.dispose();
        EndWin endWin=new EndWin();
        endWin.launch(eastPanel.countStep,winner,this);
    }

    public boolean isGameEnd(){//判断游戏是否结束
        for (Chess chess:chessArray) {
            if((chess.getName().equals("帅")||chess.getName().equals("将"))&&chess.isDead()){
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g){
        //画棋盘
        int boardHeight;//旋转棋盘后会导致棋子错位，所以需要调整棋盘长度，确保棋子落在交线上
        if (order == 0) {
            boardHeight = this.getHeight() + 15;
        } else {
            boardHeight = this.getHeight();
        }
        g.drawImage(chessboard, 0, 0, this.getWidth(), boardHeight, this);
        //画棋子
        for (Chess chess : chessArray) {
            chess.drawPieces(g, this);
        }
        //画交互光效
        drawLight(g);
        drawTrack(g);
    }


    public void drawLight(Graphics g){//画棋子光效
        if(selected!=null){
            light=null;
            //System.out.println(selected.getName());
            selected.drawFeedback(g,this);
        }
        if(light!=null){
            light.drawFeedback(g,this);
        }
    }

    public void drawTrack(Graphics g){//画路径光效
        if(selected!=null){
            track=null;
        }
        if(track!=null) {
            Chess chess=new Chess();
            Point p=chess.pointToXY(track);
            Image light = Toolkit.getDefaultToolkit().getImage("resources/imgs/圆形光晕1.png");
            g.drawImage(light,p.x+11,p.y+9,45,45,this);
        }
    }

    public Chess[] getChessArray() {
        return chessArray;
    }

    public int getOrder() {
        return order;
    }

    public void initChess() {//初始化棋盘
        //初始化棋盘
        if(order==0) {
            chessboard = Toolkit.getDefaultToolkit().getImage("resources/imgs/先手棋盘.png");
        }else{
            chessboard = Toolkit.getDefaultToolkit().getImage("resources/imgs/棋盘.jpg");
        }
        //初始化棋子
        String[] redName={"帅","士","士","相","相","车","车","马","马","炮","炮","兵","兵","兵","兵","兵"};
        String[] blackName={"将","仕","仕","象","象","車","車","馬","馬","黑炮","黑炮","卒","卒","卒","卒","卒"};
        String[] p1={null,"left","right","left","right","left","right","left","right","left","right",null,null,null,null,null};
        int[] p2={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4};
        //创建红方
        for (int i = 0; i < 16; i++) {
            chessArray[i]=ChessFactory.createChess(redName[i],order,p1[i],p2[i],i);
        }
        //创建黑方
        for (int i = 0; i < 16; i++) {
            chessArray[i+16]=ChessFactory.createChess(blackName[i],order,p1[i],p2[i],i+16);
        }
//        chessList.add(new Chariot("left", order));
//        chessList.add(new Chariot("right", order));
//        chessList.add(new Horse("left", order));
//        chessList.add(new Horse("right", order));
//        chessList.add(new Bishop("left", order));
//        chessList.add(new Bishop("right", order));
//        chessList.add(new King(order));
//        chessList.add(new Mandarins("left", order));
//        chessList.add(new Mandarins("right", order));
//        chessList.add(new Cannons("left", order));
//        chessList.add(new Cannons("right", order));
//        for (int i = 0; i < 5; i++) {
//            chessList.add(new Soldiers(i, order));
//        }
//        //黑方
//        chessList.add(new Castle("left", order));
//        chessList.add(new Castle("right", order));
//        chessList.add(new Knight("left", order));
//        chessList.add(new Knight("right", order));
//        chessList.add(new Elephants("left", order));
//        chessList.add(new Elephants("right", order));
//        chessList.add(new Guards("left", order));
//        chessList.add(new Guards("right", order));
//        chessList.add(new Generals(order));
//        chessList.add(new BlackCannons("left", order));
//        chessList.add(new BlackCannons("right", order));
//        for (int i = 0; i < 5; i++) {
//            chessList.add(new Pawns(i, order));
//        }
    }

}
