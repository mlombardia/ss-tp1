import Methods.BruteForceAlgorithm;
import Methods.CellIndexMethod;
import Particles.Particle;
import Particles.ParticleGenerator;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ParticleGenerator generator = new ParticleGenerator(args);
        CIM(generator);
        BF(generator);
    }

    private static void CIM(ParticleGenerator generator) {
        List<Particle> particles = new ArrayList<>();
        generator.generate(particles);
        generator.placeParticles(particles);
        long startTime = System.nanoTime();
        CellIndexMethod.run(particles);
        long endTime = System.nanoTime();
        System.out.printf("CIM: %d ms\n", (endTime - startTime) / 1000000);
        particles.forEach(particle -> {
            if (particle.getNeighbours().size() != 0)
                System.out.printf("particle %d has %d neighbours\n", particle.getId(), particle.getNeighbours().size());
        });
    }

    private static void BF(ParticleGenerator generator) {
        List<Particle> particles = new ArrayList<>();
        generator.generate(particles);
        generator.placeParticles(particles);
        long startTime = System.nanoTime();
        BruteForceAlgorithm.run(particles);
        long endTime = System.nanoTime();

        System.out.printf("Brute Force %d ms\n", (endTime - startTime) / 1000000);
        particles.forEach(particle -> {
            if (particle.getNeighbours().size() != 0)
                System.out.printf("particle %d has %d neighbours\n", particle.getId(), particle.getNeighbours().size());
        });
    }
}
