package sh.madoscientist.rainCh.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private String path;
    public final int SIZE;
    public int[] pixels;
    public static SpriteSheet tiles = new SpriteSheet("res/textures/spritesheet.png", 256);
    public SpriteSheet(String path, int size) {
        this.path = path;
        this.SIZE = size;
        pixels = new int[SIZE*SIZE];
    }

    private void loadSpriteSheet () {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0,0,w, h,pixels,0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("The Path Var is probably not initialized.");
        }
    }

}
