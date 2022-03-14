package Particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Particles.ParticleGenerator.L;

public class Particle {
    private int id;
    private double x;
    private double y;
    private int xCell;
    private int yCell;
    private double radius;
    private double property;
    private List<Particle> neighbours;

    public Particle(int id, double x, double y, double radius, double property) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.property = property;
        this.neighbours = new ArrayList<>();
    }

    Particle(int id, double radius, double property) {
        this.id = id;
        this.radius = radius;
        this.property = property;
        this.neighbours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getXCell() {
        return xCell;
    }

    public void setXCell(int xCell) {
        this.xCell = xCell;
    }

    public int getYCell() {
        return yCell;
    }

    public void setYCell(int yCell) {
        this.yCell = yCell;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Particle neighbour) {
        this.neighbours.add(neighbour);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double getProperty() {
        return property;
    }

    public void setProperty(double property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", property=" + property +
                ", radius=" + radius;
    }

    public double getDistanceFrom(Particle neighbour) {
        return Math.sqrt(Math.pow(this.x - neighbour.getX(), 2) + Math.pow(this.y - neighbour.getY(), 2))
                - this.radius - neighbour.getRadius();
    }

    public double getPeriodicContourDistanceFrom(Particle neighbour) {
        double distX = Math.abs(this.x - neighbour.getX());
        if (distX > (double) L / 2) {
            distX = L - distX;
        }
        double distY = Math.abs(this.y - neighbour.getY());
        if (distY > (double) L / 2) {
            distY = L - distY;
        }
        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2))
                - this.radius - neighbour.getRadius();
    }
}
