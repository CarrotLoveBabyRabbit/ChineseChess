package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RadioButton extends JButton{//圆形图标按钮
    int biasX;
    int biasY;//网格布局造成的便宜量
    int iconSize;
    Shape shape;//点击反馈
    public RadioButton(Icon icon,int iconSize,int biasX,int biasY) {//
        super(icon);
        this.iconSize=iconSize;
        this.biasX=biasX;
        this.biasY=biasY;
        //按钮扩展为圆形
        Dimension size = getPreferredSize();
        size.width = size.height = iconSize-2;
        setPreferredSize(size);

        //不画背景
        setContentAreaFilled(false);
    }

    //圆的背景
    protected void paintComponent(Graphics g){
        if(getModel().isArmed()){//点击变色
            g.setColor(Color.BLACK);
        }else{
            g.setColor(Color.GRAY);//灰色
        }
        g.fillOval(biasX,biasY,iconSize-1,iconSize-1);

        //画一个标签和焦点图形
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g){//弧形边界
        g.setColor(getForeground());
        g.drawOval(biasX,biasY,iconSize,iconSize);
    }


    public boolean contains(int x,int y){
        if(shape==null||!shape.getBounds().equals(getBounds())){
            shape=new Ellipse2D.Float(biasX,biasY,iconSize,iconSize);
        }
        return shape.contains(x,y);
    }
}
