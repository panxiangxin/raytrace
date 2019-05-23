package raytracer;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public final List<Light> lights = new ArrayList<>();
    public final List<Geometry> primitives = new ArrayList<>();

    public final Color ambient = new Color(0x202020);

    public Scene() {
        lights.add(createLight(100, 300, -125, 0xFFFFAA));
        lights.add(createLight(-250, 190, 150, 0xAAAAFF));

        primitives.add(createPlane(0, 1, 0, -30));
        primitives.add(createSphere(-80, -10, 30, 20, 0xFF8811));
        primitives.add(createSphere(-40, -10, -40, 10, 0xFF8811));
        primitives.add(createSphere(-30, -0, 0, 30, 0xFF8811));
        primitives.add(createSphere(30, -0, 0, 30, 0xFF8811));
        primitives.add(createSphere(80, -10, -30, 20, 0xFF8811));
        primitives.add(createSphere(0, 30, 60, 20, 0xFF8811));
        primitives.add(createBox(-20, -30, 40, 20, 10, 80, 0x88FF11));
        primitives.add(createBox(-40, -30, 140, 40, 50, 180, 0x88FF11));
        primitives.add(createBox(-80, -30, 100, -50, -10, 140, 0x88FF11));
        primitives.add(createCylinder(80, -30, -30, 40, 10, 0x1188FF));
        primitives.add(createCylinder(-90, -30, 80, 10, 123, 0x1188FF));
        primitives.add(createCylinder(90, -30, 80, 10, 123, 0x1188FF));
        primitives.add(createCylinder(-90, -30, -80, 10, 123, 0x1188FF));
        primitives.add(createCylinder(90, -30, -80, 10, 123, 0x1188FF));
        primitives.add(createDisc(80, -20, -30, 0, 1, 0, 40, 0x1188FF));
        primitives.add(createDisc(-90, 93, 80, 0, 1, 0, 10, 0x1188FF));
        primitives.add(createDisc(90, 93, 80, 0, 1, 0, 10, 0x1188FF));
        primitives.add(createDisc(-90, 93, -80, 0, 1, 0, 10, 0x1188FF));
        primitives.add(createDisc(90, 93, -80, 0, 1, 0, 10, 0x1188FF));
    }

    public Geometry intersect(final Ray inRay, final Ray outRay) {
        Geometry result = null;
        Ray tempRay = new Ray();
        double closest = Double.POSITIVE_INFINITY;
        for (Geometry primitive : primitives) {
            if (primitive.intersect(inRay, tempRay)) {
                double x = tempRay.origin.x - inRay.origin.x;
                double y = tempRay.origin.y - inRay.origin.y;
                double z = tempRay.origin.z - inRay.origin.z;
                double distanceSquared = (x * x) + (y * y) + (z * z);
                if (distanceSquared < closest) {
                    closest = distanceSquared;
                    result = primitive;
                    outRay.set(tempRay);
                }
            }
        }
        return result;
    }

    public boolean isShadowed(Vector toLight, Vector hitPoint) {
        Ray inRay = new Ray();
        Ray outRay = new Ray();
        inRay.direction.set(toLight).normalize();
        inRay.origin.set(inRay.direction).mul(Tracer.TOLERANCE).add(hitPoint);
        double distanceSquared = toLight.lengthSquared();
        for (Geometry primitive : primitives) {
            if (primitive.intersect(inRay, outRay)) {
                if (outRay.origin.sub(inRay.origin).lengthSquared() < distanceSquared) {
                    return true;
                }
            }
        }
        return false;
    }

    private Light createLight(double x, double y, double z, int color) {
        return new Light(x, y, z, new Color(color));
    }

    private Geometry createPlane(double x, double y, double z, double distance) {
        Geometry geometry = new Plane(x, y, z, distance);
        geometry.setMaterial(new CheckeredMaterial(new Color(0.1, 0.1, 0.1), Color.WHITE, Color.BLACK, Color.WHITE));
        return geometry;
    }

    private Geometry createSphere(double x, double y, double z, double radius, int color) {
        Geometry geometry = new Sphere(x, y, z, radius);
        geometry.setMaterial(new Material(getAmbient(color), new Color(color), Color.WHITE));
        return geometry;
    }

    private Geometry createBox(double minx, double miny, double minz, double maxx, double maxy, double maxz, int color) {
        Geometry geometry = new Box(minx, miny, minz, maxx, maxy, maxz);
        geometry.setMaterial(new Material(getAmbient(color), new Color(color), Color.WHITE));
        return geometry;
    }

    private Geometry createCylinder(double x, double y, double z, double radius, double height, int color) {
        Geometry geometry = new Cylinder(x, y, z, radius, height);
        geometry.setMaterial(new Material(getAmbient(color), new Color(color), Color.WHITE));
        return geometry;
    }

    private Geometry createDisc(double x, double y, double z, double nx, double ny, double nz, double radius, int color) {
        Geometry geometry = new Disc(x, y, z, nx, ny, nz, radius);
        geometry.setMaterial(new Material(getAmbient(color), new Color(color), Color.WHITE));
        return geometry;
    }

    private Color getAmbient(int color) {
        return new Color(color >> 1 & 0x7f7f7f);
    }

    private class CheckeredMaterial extends Material {

        public final Color diffuseColorAlt;

        public CheckeredMaterial(Color ambientColor, Color diffuseColor1, Color diffuseColor2, Color specularColor) {
            super(ambientColor, diffuseColor1, specularColor);
            diffuseColorAlt = diffuseColor2;
        }

        @Override
        public Color computeDiffuse(Ray r) {
            return (Math.floor(r.origin.z * 0.025) + Math.floor(r.origin.x * 0.025)) % 2 != 0 ? diffuse : diffuseColorAlt;
        }
    }
}
