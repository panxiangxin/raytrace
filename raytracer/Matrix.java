package raytracer;

public class Matrix {

    public double e00;
    public double e01;
    public double e02;
    public double e10;
    public double e11;
    public double e12;
    public double e20;
    public double e21;
    public double e22;

    public Matrix() {
        set(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
    }

    public Matrix(double e00, double e01, double e02, double e10, double e11, double e12, double e20, double e21, double e22) {
        set(e00, e01, e02, e10, e11, e12, e20, e21, e22);
    }

    public Matrix set(double e00, double e01, double e02, double e10, double e11, double e12, double e20, double e21, double e22) {
        this.e00 = e00;
        this.e01 = e01;
        this.e02 = e02;
        this.e10 = e10;
        this.e11 = e11;
        this.e12 = e12;
        this.e20 = e20;
        this.e21 = e21;
        this.e22 = e22;
        return this;
    }

    public Matrix concatenate(Matrix a, Matrix b) {
        double e00 = (a.e00 * b.e00) + (a.e01 * b.e10) + (a.e02 * b.e20);
        double e01 = (a.e00 * b.e01) + (a.e01 * b.e11) + (a.e02 * b.e21);
        double e02 = (a.e00 * b.e02) + (a.e01 * b.e12) + (a.e02 * b.e22);
        double e10 = (a.e10 * b.e00) + (a.e11 * b.e10) + (a.e12 * b.e20);
        double e11 = (a.e10 * b.e01) + (a.e11 * b.e11) + (a.e12 * b.e21);
        double e12 = (a.e10 * b.e02) + (a.e11 * b.e12) + (a.e12 * b.e22);
        double e20 = (a.e20 * b.e00) + (a.e21 * b.e10) + (a.e22 * b.e20);
        double e21 = (a.e20 * b.e01) + (a.e21 * b.e11) + (a.e22 * b.e21);
        double e22 = (a.e20 * b.e02) + (a.e21 * b.e12) + (a.e22 * b.e22);
        return set(e00, e01, e02, e10, e11, e12, e20, e21, e22);
    }

    public Matrix rotationAboutX(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return set(1.0, 0.0, 0.0, 0.0, c, s, 0.0, -s, c);
    }

    public Matrix rotationAboutY(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return set(c, 0.0, -s, 0.0, 1.0, 0.0, s, 0.0, c);
    }
}
