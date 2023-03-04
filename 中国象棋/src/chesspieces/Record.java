package chesspieces;

import java.awt.*;

public class Record {
    private Chess chess;
    private Point start;
    private Point end;
    private Chess eatenChess;
    private Point eatenStart;

    public Record(){

    }

    public Record(Chess chess, Point start, Point end) {
        this.chess = chess;
        this.start = start;
        this.end = end;
    }

    public Record(Chess chess, Point start, Point end, Chess eatenChess,Point eatenStart) {
        this.chess = chess;
        this.start = start;
        this.end = end;
        this.eatenChess = eatenChess;
        this.eatenStart = eatenStart;
    }

    public Chess getChess() {
        return chess;
    }

    public void setChess(Chess chess) {
        this.chess = chess;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Chess getEatenChess() {
        return eatenChess;
    }

    public void setEatenChess(Chess eatenChess) {
        this.eatenChess = eatenChess;
    }

    public Point getEatenStart() {
        return eatenStart;
    }

    public void setEatenStart(Point eatenStart) {
        this.eatenStart = eatenStart;
    }
}
