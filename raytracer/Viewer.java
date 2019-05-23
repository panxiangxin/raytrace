package raytracer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Viewer extends Canvas {

    private BufferedImage image;

    public Viewer(int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public void setRGB(int[] pixels) {
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        if (SwingUtilities.isEventDispatchThread()) {
            repaint();
        } else {
            SwingUtilities.invokeLater(this::repaint);
        }
    }
}
