package cn.seesjj.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 飞机游戏的主窗口
 */
public class MyGameFrame extends Frame {


    Image bj = GameUtil.getImage("images/bj.jpg");
    Image planeImg = GameUtil.getImage("images/plane.png");

    Plane plane = new Plane(planeImg,250,250);
    Shell[] shells = new Shell[50];
    Explode bao;
    Date startTime = new Date();
    Date endTime;
    int period; //游戏持续的时间
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();

        g.drawImage(bj,0,0,null);
        plane.drawSelf(g);  //画飞机

        for (int i= 0;i<shells.length;i++){
            shells[i].draw(g);

            //飞机和炮弹的碰撞检测
            boolean peng = shells[i].getRect().intersects(plane.getRect());
//            System.out.println(peng);
            if (peng){
                plane.live = false;

                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);
                    endTime = new Date();
                    period = (int)((endTime.getTime()-startTime.getTime())/1000);

                }

                bao.draw(g);
            }

            if (!plane.live){

                g.setColor(Color.red);
                Font f = new Font("宋体",Font.BOLD,50);
                g.setFont(f);
                g.drawString("时间："+period+"秒",130,250);

            }
        }
        g.setColor(c);
    }

    class PaintTread extends Thread{
        @Override
        public void run() {
            while (true){
                repaint();  //重画

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //增加键盘监听内部类
    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }

    /**
     * 初始化窗口
     */
    public void launchFrame(){
        this.setTitle("JK'FlyGame");
        this.setVisible(true);
        this.setSize(Constant.GAME_WITDH,Constant.GAME_HIGHT);
        this.setLocation(300,300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintTread().start();   //启动重画窗口的线程
        addKeyListener(new KeyMonitor());   //给窗口增加键盘监听


        //初始化50个炮弹
        for (int i= 0;i<shells.length;i++){
            shells[i] = new Shell();
        }
    }

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WITDH,Constant.GAME_HIGHT);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();
    }

    private Image offScreenImage = null;


}


