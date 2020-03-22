package birdgame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Queue;
import java.util.Random;

/**
 * @author mx
 * @date 2020/3/21 - 13:18
 */
public class Column {

    //柱子位置
    int x;
    int y;
    //柱子宽度
    int width;
    //柱子高度
    int height;
    //柱子间距
    int gap;
    //柱子距离
    int distance;
    BufferedImage image;
    Random random = new Random();

    public Column(int n) throws IOException {
        image = ImageIO.read(getClass().getResource("/resources/column.png"));
        this.width = image.getWidth();
        this.height = image.getHeight();
        gap = 144;
        distance = 245;
        x = 550 + distance * (n - 1);
        y = random.nextInt(218) + 132;
    }

    public void step() {
        x--;
        if (x < -width / 2) {
            x = 2 * distance - width / 2;
            y = random.nextInt(218) + 132;
        }
    }
}
