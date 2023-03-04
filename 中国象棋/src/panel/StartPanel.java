package panel;

import gameinterface.MainWin;
import gameinterface.StartWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {//功能扩展区
    Image img;
    StartWin startWin;

    public StartPanel(LayoutManager layout, StartWin startWin) {
        //使用父类的嵌套容器
        super(layout);
        this.startWin=startWin;
        //桌面背景
        img=Toolkit.getDefaultToolkit().getImage("resources/imgs/桌面.jpg");
        //重来按钮
        Icon reIcon=new ImageIcon("resources/imgs/先手开始.png");
        JButton reGame=new RadioButton(reIcon,reIcon.getIconHeight(),100,265);
        reGame.setBorderPainted(false);
        reGame.setContentAreaFilled(false);
        reGame.addActionListener(this);
        reGame.setActionCommand("先手开始");
        //悔棋按钮
        Icon backIcon=new ImageIcon("resources/imgs/后手开始.png");
        JButton back=new RadioButton(backIcon,backIcon.getIconHeight(),100,265);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.addActionListener(this);
        back.setActionCommand("后手开始");

        this.add(reGame);
        this.add(back);


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("先手开始")) {
            startWin.dispose();
            MainWin mainWin=new MainWin();
            mainWin.launch(0);
        }else if(e.getActionCommand().equals("后手开始")){
            startWin.dispose();
            MainWin mainWin=new MainWin();
            mainWin.launch(1);
        }

    }

}

