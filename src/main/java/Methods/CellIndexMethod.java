package Methods;

import Particles.Particle;

import java.util.ArrayList;
import java.util.List;

import static Particles.ParticleGenerator.L;

public class CellIndexMethod {
    public static int rc = 1;
    public static int M = 71;
    public static double cellSize = (double) L / M;

    private static List<List<Particle>> cells;

    public static void run(List<Particle> particles) {
        cells = new ArrayList<>();
        for (int i = 0; i < M * M; i++) {
            cells.add(new ArrayList<>());
        }
        populateCells(particles);
        getCellNeighbours(particles);
    }

    private static void populateCells(List<Particle> particles) {

        for (Particle particle : particles) {
            particle.setXCell((int) Math.floor(particle.getX() / cellSize));
            particle.setYCell((int) Math.floor(particle.getY() / cellSize));
            cells.get(particle.getYCell() * M + particle.getXCell()).add(particle);
        }
    }

    private static void getCellNeighbours(List<Particle> particles) {
        for (List<Particle> cell : cells) {
            for (Particle particle : cell) {
                int x = particle.getXCell();
                int y = particle.getYCell();
                int xUp = x + 1;
                int yUp = y + 1;
                int yDown = y - 1;
                getNeighbours(particle, x, yUp); //up
                getNeighbours(particle, xUp, yUp); //up and right
                getNeighbours(particle, xUp, y); //right
                getNeighbours(particle, xUp, yDown); //down and right
            }
        }
    }

    private static void getNeighbours(Particle particle, int x, int y) {
        if (x >= 0 && y >= 0 && x < M && y < M) {
            List<Particle> cell = cells.get(y * M + x);
            for (Particle neighbour : cell) {
                if (!neighbour.equals(particle)) {
                    double dist = particle.getDistanceFrom(neighbour);
                    if (dist < rc) {
                        particle.addNeighbour(neighbour);
                        neighbour.addNeighbour(particle);
                    }
                }
            }
        }
    }
}
