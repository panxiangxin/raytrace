package raytracer;

public class Camera {

    public final double distance;
    public final double fov;
    private raytracer.Matrix transform;

    public Camera(double distance, double fov) {
        this.distance = distance;
        this.fov = fov;
        transform = new raytracer.Matrix();
    }

    public raytracer.Vector transform(raytracer.Vector v) {
        double x = v.x * transform.e00 + v.y * transform.e10 + v.z * transform.e20;
        double y = v.x * transform.e01 + v.y * transform.e11 + v.z * transform.e21;
        double z = v.x * transform.e02 + v.y * transform.e12 + v.z * transform.e22;
        return v.set(x, y, z);
    }

    public void rotate(double aboutX, double aboutY) {
        transform.rotationAboutX(aboutX).concatenate(transform, new raytracer.Matrix().rotationAboutY(aboutY));
    }
}
