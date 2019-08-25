package cn.seesjj.game;

import java.awt.*;

/**
 * 炮弹类
 */
public class Shell extends GameObject{

    double degree;

    public Shell(){
        x = 200;
        y = 200;
        width = 10;
        height = 10;
        speed = 3;

        degree = Math.random()*Math.PI*2;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval((int)x,(int)y,width,height);

        //炮弹延任意角度飞
        x += speed*Math.cos(degree);
        y += speed*Math.sin(degree);

        if (x<0||x>Constant.GAME_WITDH - width){
            degree = Math.PI - degree;
        }
        if (y<35||y>Constant.GAME_HIGHT - height){
            degree = - degree;
        }



    }
}
