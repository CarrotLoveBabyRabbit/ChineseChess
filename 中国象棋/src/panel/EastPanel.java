package panel;

import gameinterface.MainWin;
import gameinterface.StartWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastPanel extends JPanel implements ActionListener {//功能扩展区
    Image img;
    int countStep;
    JLabel stepLabel=new JLabel("0步");//展示步数的label
    JLabel tipLabel=new JLabel("红方走");//提示当前走方
    MainWin mainWin;

    public EastPanel(LayoutManager layout,MainWin mainWin) {
        //使用父类的嵌套容器
        super(layout);
        this.mainWin=mainWin;
        //桌面背景
        img=Toolkit.getDefaultToolkit().getImage("resources/imgs/桌面.jpg");
        //重来按钮
        Icon reIcon=new ImageIcon("resources/imgs/重来.png");
        JButton reGame=new RadioButton(reIcon,reIcon.getIconHeight(),0,20);
        reGame.setBorderPainted(false);
        reGame.setContentAreaFilled(false);
        reGame.addActionListener(this);
        reGame.setActionCommand("重来");
        //悔棋按钮
        Icon backIcon=new ImageIcon("resources/imgs/悔棋.png");
        JButton back=new RadioButton(backIcon,backIcon.getIconHeight(),0,20);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.addActionListener(this);
        back.setActionCommand("悔棋");
        //显示步数
        Font font=new Font("楷体",Font.PLAIN,40);
        stepLabel.setFont(font);
        stepLabel.setForeground(Color.lightGray);
        stepLabel.setHorizontalAlignment(JLabel.CENTER);
        stepLabel.setVerticalAlignment(JLabel.CENTER);
        //提示走方
        tipLabel.setFont(font);
        tipLabel.setForeground(Color.lightGray);
        tipLabel.setHorizontalAlignment(JLabel.CENTER);
        tipLabel.setVerticalAlignment(JLabel.CENTER);

        this.add(reGame);
        this.add(back);
        this.add(tipLabel);
        this.add(stepLabel);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("重来")) {
            mainWin.dispose();
            StartWin startWin=new StartWin();
            startWin.launch();
        }else if(e.getActionCommand().equals("悔棋")){
            mainWin.getCp().repentChess();
        }

    }

}
