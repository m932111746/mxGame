package birdgame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author mx
 * @date 2020/3/21 - 13:10
 */
public class Ground {

    //地面图片
    BufferedImage image;
    //图片的横坐标
    int x;
    //图片的纵坐标
    int y;
    //图片的宽度
    int width;
    //图片的高度
    int height;

    //地面的构造方法
    public Ground() throws IOException {
        image = ImageIO.read(getClass().getResource("/resources/ground.png"));
        this.width = image.getWidth();
        this.height = image.getHeight();
        x = 0;
        y = 500;
    }

    //地面移动
    public void step() {
        x--;
        if (x < -109) {
            x = 0;
        }
    }
}
