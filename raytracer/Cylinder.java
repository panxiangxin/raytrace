package raytracer;

public class Cylinder extends Geometry {

    private final Vector position;
    private final double radius;
    private final double height;

    public Cylinder(double x, double y, double z, double r, double h) {
        position = new Vector(x, y, z);
        radius = r;
        height = h;
    }

    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        double a = inRay.direction.x * inRay.direction.x + inRay.direction.z * inRay.direction.z;

        if (a != 0.0) {
            double ox = inRay.origin.x - position.x;
            double oy = inRay.origin.y - position.y;
            double oz = inRay.origin.z - position.z;
            double b = 2.0 * (ox * inRay.direction.x + oz * inRay.direction.z);
            double c = ox * ox + oz * oz - radius * radius;
            double descriminant = b * b - 4.0 * a * c;

            if (descriminant >= 0) {
                double d = Math.sqrt(descriminant);
                double inv2A = 1.0 / (2.0 * a);
                double t0 = (-b - d) * inv2A;
                double t1 = (-b + d) * inv2A;
                double t = Double.MAX_VALUE;
                boolean intersects = false;

                if (t0 > 0) {
                    intersects = true;
                    double y0 = inRay.origin.y + inRay.direction.y * t0 - position.y;
                    if (y0 > 0 && y0 < height) {
                        t = Math.min(t, t0);
                    }
                }

                if (t1 > 0) {
                    intersects = true;
                    double y1 = inRay.origin.y + inRay.direction.y * t1 - position.y;
                    if (y1 > 0 && y1 < height) {
                        t = Math.min(t, t1);
                    }
                }

                if (intersects) {
                    outRay.origin.set(inRay.direction).mul(t).add(inRay.origin);
                    double x = outRay.origin.x - position.x;
                    double z = outRay.origin.z - position.z;
                    double scaler = 1.0 / Math.sqrt(x * x + z * z);
                    x *= scaler;
                    z *= scaler;
                    if (x * inRay.direction.x + z * inRay.direction.z > 0.0) {
                        x = -x;
                        z = -z;
                    }
                    outRay.direction.set(x, 0.0, z);
                    return true;
                }
            }
        }
        return false;
    }
}
