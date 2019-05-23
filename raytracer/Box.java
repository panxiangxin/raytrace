package raytracer;

public class Box extends raytracer.Geometry {

    private final raytracer.Vector minimum;
    private final raytracer.Vector maximum;

    public Box(double minx, double miny, double minz, double maxx, double maxy, double maxz) {
        minimum = new raytracer.Vector(minx, miny, minz);
        maximum = new raytracer.Vector(maxx, maxy, maxz);
    }

    @Override
    public boolean intersect(raytracer.Ray inRay, raytracer.Ray outRay) {
        double tNear = -Double.MAX_VALUE;
        double tFar = Double.MAX_VALUE;

        // Deal with the X pair of planes...
        if (inRay.direction.x == 0.0) {
            if (inRay.origin.x < minimum.x || inRay.origin.x > maximum.x) {
                return false;
            }
        } else {
            double rec = 1.0 / inRay.direction.x;
            double t1 = (minimum.x - inRay.origin.x) * rec;
            double t2 = (maximum.x - inRay.origin.x) * rec;

            if (t1 < t2) {
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            } else {
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t1 : tFar;
            }

            if (tNear > tFar || tFar < 0.0) {
                return false;
            }
        }

        // Deal with the Y pair of planes...
        if (inRay.direction.y == 0.0) {
            if (inRay.origin.y < minimum.y || inRay.origin.y > maximum.y) {
                return false;
            }
        } else {
            double rec = 1.0 / inRay.direction.y;
            double t1 = (minimum.y - inRay.origin.y) * rec;
            double t2 = (maximum.y - inRay.origin.y) * rec;

            if (t1 < t2) {
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            } else {
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t1 : tFar;
            }

            if (tNear > tFar || tFar < 0.0) {
                return false;
            }
        }

        // Deal with the Z pair of planes...
        if (inRay.direction.z == 0.0) {
            if (inRay.origin.z < minimum.z || inRay.origin.z > maximum.z) {
                return false;
            }
        } else {
            double rec = 1.0 / inRay.direction.z;
            double t1 = (minimum.z - inRay.origin.z) * rec;
            double t2 = (maximum.z - inRay.origin.z) * rec;

            if (t1 < t2) {
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            } else {
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t1 : tFar;
            }

            if (tNear > tFar || tFar < 0.0) {
                return false;
            }
        }

        // Survived all tests...
        outRay.origin.set(inRay.direction).mul(tNear).add(inRay.origin);

        if (outRay.origin.x <= (minimum.x + raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(-1.0, 0.0, 0.0);
        } else if (outRay.origin.x >= (maximum.x - raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(1.0, 0.0, 0.0);
        } else if (outRay.origin.y <= (minimum.y + raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(0.0, -1.0, 0.0);
        } else if (outRay.origin.y >= (maximum.y - raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(0.0, 1.0, 0.0);
        } else if (outRay.origin.z <= (minimum.z + raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(0.0, 0.0, -1.0);
        } else if (outRay.origin.z >= (maximum.z - raytracer.Tracer.TOLERANCE)) {
            outRay.direction.set(0.0, 0.0, 1.0);
        }

        return true;
    }
}

