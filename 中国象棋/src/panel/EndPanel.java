package panel;


import gameinterface.EndWin;
import gameinterface.StartWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel implements ActionListener {//功能扩展区
    Image img;
    int step;//步数
    String winnerName;//胜者
    EndWin endWin;

    public EndPanel(LayoutManager layout, int step, int winner, EndWin endWin) {
        //使用父类的嵌套容器
        super(layout);
        this.endWin=endWin;
        this.step=step;
        if(winner==0){
            this.winnerName="红方";
        }else{
            this.winnerName="黑方";
        }
        JLabel stepLabel=new JLabel(step+"步绝杀");//展示步数的label
        JLabel tipLabel=new JLabel(winnerName+"胜利");//提示胜者
        //桌面背景
        img=Toolkit.getDefaultToolkit().getImage("resources/imgs/桌面.jpg");

        //显示步数
        Font font=new Font("楷体",Font.BOLD,30);
        stepLabel.setFont(font);
        stepLabel.setForeground(Color.lightGray);
        stepLabel.setHorizontalAlignment(JLabel.CENTER);
        stepLabel.setVerticalAlignment(JLabel.CENTER);
        //提示走方
        tipLabel.setFont(font);
        tipLabel.setForeground(Color.lightGray);
        tipLabel.setHorizontalAlignment(JLabel.CENTER);
        tipLabel.setVerticalAlignment(JLabel.CENTER);
        //重来按钮
        Icon reIcon=new ImageIcon("resources/imgs/重来.png");
        JButton reGame=new RadioButton(reIcon,reIcon.getIconHeight(),0,50);
        reGame.setBorderPainted(false);
        reGame.setContentAreaFilled(false);
        reGame.addActionListener(this);
        reGame.setActionCommand("重来");

        this.add(tipLabel);
        this.add(stepLabel);
        this.add(reGame);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("重来")) {
            endWin.dispose();
            StartWin startWin=new StartWin();
            startWin.launch();
        }

    }

}

