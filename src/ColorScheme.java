import java.awt.*;
import java.util.HashMap;

public class ColorScheme {
    public HashMap<Integer, Color> colors = new HashMap<>(12);

    public ColorScheme() {
        colors.put(0, Color.LIGHT_GRAY);
        colors.put(2, Color.GRAY);
        colors.put(4, Color.DARK_GRAY);
        colors.put(8, Color.MAGENTA);
        colors.put(16, Color.BLUE);
        colors.put(32, Color.RED);
        colors.put(64, Color.ORANGE);
        colors.put(128, Color.GREEN);
        colors.put(256, Color.PINK);
        colors.put(512, Color.LIGHT_GRAY);
        colors.put(1024, Color.LIGHT_GRAY);
        colors.put(2048, Color.LIGHT_GRAY);
    }
}
