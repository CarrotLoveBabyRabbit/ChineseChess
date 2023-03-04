package chesspieces;

public class ChessFactory {
    private ChessFactory(){

    }

    public static Chess createChess(String name,int order,String s,int i,int id) {
        return switch (name) {
            case "帅" -> new King(order,id);
            case "士" -> new Mandarins(s, order,id);
            case "相" -> new Bishop(s, order,id);
            case "马" -> new Horse(s, order,id);
            case "车" -> new Chariot(s, order,id);
            case "炮" -> new Cannons(s, order,id);
            case "兵" -> new Soldiers(i, order,id);
            case "将" -> new Generals(order,id);
            case "仕" -> new Guards(s, order,id);
            case "象" -> new Elephants(s, order,id);
            case "馬" -> new Knight(s, order,id);
            case "車" -> new Castle(s, order,id);
            case "黑炮" -> new BlackCannons(s, order,id);
            case "卒" -> new Pawns(i, order,id);
            default -> null;
        };
    }
}
