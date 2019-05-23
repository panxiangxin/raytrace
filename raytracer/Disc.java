package raytracer;

public class Disc extends Geometry {

    private final Vector position;
    private final Vector normal;
    private final double radius;

    public Disc(double x, double y, double z, double nx, double ny, double nz, double r) {
        position = new Vector(x, y, z);
        normal = new Vector(nx, ny, nz).normalize();
        radius = r;
    }

    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        double vd = normal.dot(inRay.direction);
        if (vd != 0.0) {
            double t = -normal.dot(outRay.origin.set(inRay.origin).sub(position)) / vd;
            if (t > 0.0) {
                outRay.origin.set(inRay.direction).mul(t).add(inRay.origin);
                if (Vector.distanceSq(outRay.origin, position) < radius * radius) {
                    outRay.direction.set(normal);
                    return true;
                }
            }
        }
        return false;
    }
}
