package raytracer;

public class Sphere extends Geometry {

    private final Vector position;
    private final double radius;

    public Sphere(double x, double y, double z, double r) {
        position = new Vector(x, y, z);
        radius = r;
    }

    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        double b = 2.0 * inRay.direction.dot(inRay.origin.x - position.x, inRay.origin.y - position.y, inRay.origin.z - position.z);
        double discriminant = b * b - 4.0 * (Vector.distanceSq(inRay.origin, position) - radius * radius);
        if (discriminant > 0.0) {
            double d = Math.sqrt(discriminant);
            double t = Math.min(-b - d, -b + d) * 0.5;
            if (t > 0.0) {
                outRay.origin.set(inRay.direction).mul(t).add(inRay.origin);
                outRay.direction.set(outRay.origin).sub(position).mul(1.0 / radius);
                return true;
            }
        }
        return false;
    }
}
