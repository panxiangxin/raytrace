package raytracer;

public class Color {

    private static final double ONE_OVER_256 = 1.0 / 256.0;

    public static final Color WHITE = new Color(1.0, 1.0, 1.0);
    public static final Color BLACK = new Color(0.0, 0.0, 0.0);

    public final double red;
    public final double grn;
    public final double blu;

    public Color(int rgb) {
        this(ONE_OVER_256 * (0xFF & rgb >> 0x10),
             ONE_OVER_256 * (0xFF & rgb >> 0x08),
             ONE_OVER_256 * (0xFF & rgb));
    }

    public Color(double red, double grn, double blu) {
        this.red = Math.max(0.0, Math.min(1.0, red));
        this.grn = Math.max(0.0, Math.min(1.0, grn));
        this.blu = Math.max(0.0, Math.min(1.0, blu));
    }
}
