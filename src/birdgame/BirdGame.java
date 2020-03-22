package birdgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * @author mx
 * @date 2020/3/21 - 16:26
 */
public class BirdGame extends JPanel {

    BufferedImage backgroundImg;
    BufferedImage startImg;
    BufferedImage endImg;
    Ground ground;
    Column column1, column2;
    Bird bird;
    int score;
    int state;

    // 状态常量
    public static final int START = 0; //开始
    public static final int RUNNING = 1; //运行
    public static final int GAME_OVER = 2; //结束

    public BirdGame() throws Exception {
        // 初始化背景图片
        backgroundImg = ImageIO.read(getClass().getResource("/resources/bg.png"));

        // 初始化开始、结束图片
        startImg = ImageIO.read(getClass().getResource("/resources/start.png"));
        endImg = ImageIO.read(getClass().getResource("/resources/gameover.png"));

        // 初始化地面、柱子、小鸟
        ground = new Ground();
        column1 = new Column(1);
        column2 = new Column(2);
        bird = new Bird();

        // 初始化分数
        score = 0;

        // 初始化状态
        state = START;
    }

    public void paint(Graphics g) {

        g.drawImage(backgroundImg, 0, 0, null);
        g.drawImage(ground.image, ground.x, ground.y, null);
        g.drawImage(column1.image, column1.x - column1.width / 2, column1.y - column1.height / 2, null);
        g.drawImage(column2.image, column2.x - column2.width / 2, column2.y - column2.height / 2, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        g.drawImage(bird.image, bird.x - bird.size / 2, bird.y - bird.size / 2, null);
        g2.rotate(bird.alpha, bird.x, bird.y);

        //绘制分数
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(f);
        g.drawString("" + score, 40, 60);
        g.setColor(Color.WHITE);
        g.drawString("" + score, 40 - 3, 60 - 3);
        switch (state) {
            case START:
                g.drawImage(startImg, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(endImg, 0, 0, null);
                break;
        }
    }
    public void action() throws Exception {
        // 鼠标监听器
        MouseListener l = new MouseAdapter() {
            // 鼠标按下事件
            public void mousePressed(MouseEvent e) {
                try {
                    switch (state) {
                        case START:
                            // 在开始状态，按下鼠标则转为运行状态。
                            state = RUNNING;
                            break;
                        case RUNNING:
                            // 在运行状态，按下鼠标则小鸟向上飞行。
                            bird.click();
                            break;
                        case GAME_OVER:
                            // 在结束状态，按下鼠标则重置数据，再次转为开始态。
                            column1 = new Column(1);
                            column2 = new Column(2);
                            bird = new Bird();
                            score = 0;
                            state = START;
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        addMouseListener(l);
        while(true) {
            switch (state) {
                case START:
                    bird.fly();
                    ground.step();
                    break;
                case RUNNING:
                    ground.step();
                    bird.fly();
                    column1.step();
                    column2.step();
                    bird.fly();
                    bird.step();
                    // 计算分数
                    if (bird.x == column1.x || bird.x == column2.x) {
                        score++;
                    }
                    // 检测是否发生碰撞
                    if (bird.hit(ground) || bird.hit(column1) || bird.hit(column2)) {
                        state = GAME_OVER;
                    }
                    break;
            }
            repaint();
            Thread.sleep(1000 / 60);
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        BirdGame game = new BirdGame();
        frame.add(game);
        frame.setSize(440, 670);
        //设置窗口在桌面位置
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //使图形界面可见
        frame.setVisible(true);
        game.action();

    }

}
