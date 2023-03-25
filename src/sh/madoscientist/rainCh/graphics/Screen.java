package sh.madoscientist.rainCh.graphics;

import java.util.Arrays;
import java.util.Random;

public class Screen {
    private int width, height;
    public int[] pixels;
    public final int TILE_MAP_SIZE = 64;
    public final int TILE_MAP_MASK = TILE_MAP_SIZE - 1;
    private Random random = new Random();
    public int[] tiles = new int[TILE_MAP_SIZE * TILE_MAP_SIZE];

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
        for (int i = 0; i < TILE_MAP_SIZE * TILE_MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xFFFFFF);
        }
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }
    public void render(int xOffset, int yOffset) {
        for (int y = 0; y < height; y++ ) {
            int yy = y + yOffset;
//            if (yy < 0 || yy >= height) break;
            for (int x = 0; x<width; x++) {
                int xx = x + xOffset;
//                if (xx < 0 || xx >= width) break;
                int tileIndex = ((xx >> 4) & TILE_MAP_MASK) + (((yy >> 4) & TILE_MAP_MASK) * TILE_MAP_SIZE);
                pixels[x + (y * width)] = Sprite.grass.pixels[(xx & 15) + (yy & 15) * Sprite.grass.SIZE];
            }
        }
    }
}
