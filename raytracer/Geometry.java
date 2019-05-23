package raytracer;

public abstract class Geometry {

    private Material material;

    protected Geometry() {
        setMaterial(Material.DEFAULT);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material m) {
        material = (m != null) ? m : Material.DEFAULT;
    }

    public abstract boolean intersect(Ray inRay, Ray outRay);
}
