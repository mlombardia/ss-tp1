import Methods.BruteForceAlgorithm;
import Methods.CellIndexMethod;
import Particles.Particle;
import Particles.ParticleGenerator;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ParticleGenerator generator = new ParticleGenerator(args);
        List<Particle> particles = new ArrayList<>();
        generator.generate(particles);
        generator.placeParticles(particles);
        CIM(particles);
        BF(particles);
    }

    private static void CIM(List<Particle> particles) {
        long startTime = System.nanoTime();
        CellIndexMethod.run(particles);
        long endTime = System.nanoTime();
        System.out.printf("CIM: %d ms\n", (endTime - startTime) / 1000000);
        particles.forEach(particle -> {
            if (particle.getNeighbours().size() != 0) {
                System.out.printf("particle %d has %d neighbours\n", particle.getId(), particle.getNeighbours().size());
                System.out.printf("[%d %s]\n", particle.getId(), particle.printNeighboursIds());
            }
        });
    }

    private static void BF(List<Particle> particles) {
        long startTime = System.nanoTime();
        BruteForceAlgorithm.run(particles);
        long endTime = System.nanoTime();
        System.out.printf("Brute Force %d ms\n", (endTime - startTime) / 1000000);
        particles.forEach(particle -> {
            if (particle.getNeighbours().size() != 0) {
                System.out.printf("particle %d has %d neighbours\n", particle.getId(), particle.getNeighbours().size());
                System.out.printf("[%d %s]\n", particle.getId(), particle.printNeighboursIds());
            }
        });
    }
}
