package birdgame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author mx
 * @date 2020/3/21 - 13:49
 */
public class Bird {

    //小鸟的动作集合
    BufferedImage[] images;
    //小鸟的第一张图片
    BufferedImage image;
    //小鸟的横坐标
    int x;
    //小鸟的纵坐标
    int y;
    //小鸟图片的大小
    int width;
    int height;
    int size;
    //向下的加速度
    int g;
    //点击向上的初速度
    int v0;
    //速度
    double speed;
    //倾角
    double alpha;
    //移动距离
    double s;
    //时间间隔
    double t;
    int index;

    public Bird() throws IOException {
        image = ImageIO.read(getClass().getResource("/resources/0.png"));
        height = image.getHeight();
        width = image.getWidth();
        x = 132;
        y = 280;
        size = 20;
        //初始化位移参数
        g = 4;
        v0 = 20;
        speed = v0;
        s = 0;
        t = 0.25;
        alpha = 0;
        images = new BufferedImage[8];
        for (int i = 0; i < 8; i++) {
            images[i] = ImageIO.read(getClass().getResource("/resources/" + i + ".png"));
        }
        index = 0;
    }

    public void fly() {
        index++;
        image = images[(index / 12) % 8];
    }

    public void step() {
        double v0 = speed;
        s = v0 * t + g * t * t / 2;
        y = y - (int)s;
        speed = v0 - g * t;
        alpha = Math.atan(s / 8);
    }

    //每次点击都将速度置为v0
    public void click() {
        speed = v0;
    }

    //判断是否与地面相撞
    public boolean hit(Ground ground) {
        boolean hit = y + size / 2 > ground.y;
        if (hit) {
            y = ground.y - size / 2;
            alpha = -3.14159265358979323 / 2;
        }
        return hit;
    }

    //判断是否与柱子相撞
    public boolean hit(Column column) {
        if (x > column.x - column.width - size / 2 && x < column.x + column.width + size / 2){
            if (y > column.y - column.gap / 2 + size / 2 && y < column.y + column.gap / 2 - size / 2) {
                return false;
            }
            return true;
        }
        return false;
    }
}
