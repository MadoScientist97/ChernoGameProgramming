package sh.madoscientist.rainCh;

import sh.madoscientist.rainCh.graphics.Screen;
import sh.madoscientist.rainCh.io.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static int width = 300;
    public static int height = (width /16) * 9;
    public static int scale = 3;

    private Thread thread;
    private JFrame frame;
    private boolean running = false;
    private int movementSpeed = 1;
    private Screen screen;
    private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Keyboard key;
    public  Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);
        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();
        addKeyListener(key);
    }
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames  = 0;
        int updates = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Hello Rain |  " + updates + " Ticks, " + frames + " fps");
                frame.setTitle("Hello Rain |  " + updates + " Ticks, " + frames + " fps" );
                updates = 0;
                frames = 0;
            }
        }
    }

    int x=0, y=0;
  public void update() {
      // y++;
      key.update();
      if (key.up && key.left) {
          x -= movementSpeed;
          y -= movementSpeed;
      }
      else if (key.up && key.right) {
          x += movementSpeed;
          y -= movementSpeed;
      }
      else if (key.down && key.left) {
          x -= movementSpeed;
          y += movementSpeed;
      }
      else if (key.down && key.right) {
          x += movementSpeed;
          y += movementSpeed;
      }
      else if (key.up)
          y -= movementSpeed;
      else if (key.down)
          y += movementSpeed;
      else if (key.left)
          x -= movementSpeed;
      else if (key.right)
          x += movementSpeed;
  }

  public void render() {
      BufferStrategy bs = getBufferStrategy();
      if (bs == null) {
          createBufferStrategy(3);
          return;
      }

      screen.clear();
      screen.render(x, y);

      System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
      Graphics g = bs.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0, 0, getWidth(),getHeight());
      g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
      g.dispose();
      bs.show();
  }
    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle("Hello Rain");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
        game.requestFocus();
    }
}
