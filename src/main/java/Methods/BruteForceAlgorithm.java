package Methods;

import Particles.Particle;

import java.util.List;

import static Methods.CellIndexMethod.periodic;
import static Methods.CellIndexMethod.rc;

public class BruteForceAlgorithm {

    public static void run(List<Particle> particles) {
        for (Particle particleA : particles) {
            for (Particle particleB : particles) {
                if (!particleA.equals(particleB) && !particleA.getNeighbours().contains(particleB)){
                    double dist;
                    if (!periodic) {
                        dist = particleA.getDistanceFrom(particleB);
                    }else{
                        dist = particleA.getPeriodicContourDistanceFrom(particleB);
                    }
                    if (dist < rc){
                        particleA.addNeighbour(particleB);
                        particleB.addNeighbour(particleA);
                    }

                }
            }
        }
    }

}
