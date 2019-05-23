package raytracer;

public class Ray {

    public final Vector origin;
    public final Vector direction;

    public Ray() {
        origin = new Vector();
        direction = new Vector();
    }

    public Ray set(Vector o, Vector d) {
        origin.set(o);
        direction.set(d);
        return this;
    }

    public Ray set(Ray r) {
        return set(r.origin, r.direction);
    }
}
