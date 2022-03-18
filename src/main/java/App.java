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
        if(ParticleGenerator.isRandom){
            generator.generateRandom(particles);
        }else{
            generator.generate(particles);
        }
        generator.placeParticles(particles);
        long timeCIM = CIM(particles);
        BF(particles);
        FileWriter.writeOutput(particles, 100.0, 100, timeCIM, 1.0);
    }

    private static long CIM(List<Particle> particles) {
        long startTime = System.nanoTime();
        CellIndexMethod.run(particles);
        long endTime = System.nanoTime();
        int neighbours = 0;
        System.out.printf("CIM: %d ms\n", (endTime - startTime) / 1000000);
        for(Particle p : particles){
            if (p.getNeighbours().size() != 0) {
                System.out.printf("particle %d has %d neighbours\n", p.getId(), p.getNeighbours().size());
                neighbours += p.getNeighbours().size();
                System.out.printf("[%d %s]\n", p.getId(), p.printNeighboursIds());
            }
        }
        return (endTime - startTime);
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
