package Particles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParticleGenerator {
    public String staticPath;
    public String dynamicPath;
    public static boolean isPeriodic;
    public static boolean isRandom;
    public static int N;
    public static int L;
    public static double rc;
    public static double rMax = -1;
    public static int M;

    public ParticleGenerator(String[] args) {
        this.staticPath = args[0];
        this.dynamicPath = args[1];
        //todo -parser
        try {
            rc = args[2].equals("-rc") ? Double.parseDouble(args[3]) : (args[4].equals("-rc") ? Double.parseDouble(args[5]) : 1.0);
        }catch (RuntimeException e){
            rc = 1;
            System.out.println("No -rc conditions established");
        }
        try{
            M = args[2].equals("-m") ? Integer.parseInt(args[3]) : (args[4].equals("-m") ? Integer.parseInt(args[5]) : -1);
        }catch (RuntimeException e){
            M = -1;
            System.out.println("No -m conditions established");
        }
        try{
            isPeriodic = args[2].equals("-periodic") || args[4].equals("-periodic") || args[6].equals("-periodic") || args[7].equals("-periodic");
        }catch (RuntimeException e){
            isPeriodic = false;
            System.out.println("No -periodic conditions established");
        }
        try{
            isRandom = args[2].equals("-r") || args[4].equals("-r") || args[6].equals("-r") || args[7].equals("-r");
        }catch (RuntimeException e){
            isRandom = false;
            System.out.println("No -random conditions established");
        }
    }

    public void generate(List<Particle> particles) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(staticPath));
            String line = reader.readLine();
            int id = 1;
            N = Integer.parseInt(line.trim());
            L = Integer.parseInt(reader.readLine().trim());
            if (M == -1) {
                M = (int) Math.floor(L / rc + 2 * rMax);
            }
            line = reader.readLine();

            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");
                String[] ans = line.split(" ");
                double r = Double.parseDouble(ans[0]);
                if (r > rMax) {
                    rMax = r;
                }
                Particle particle = new Particle(id++, r, Double.parseDouble(ans[1]));
                particles.add(particle);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.print("Could not open file: ");
            e.printStackTrace();
        }
    }

    public void generateRandom(List<Particle> particles) {
        N = 1000;
        L = 100;
        if (M == -1) {
            M = (int) Math.floor(L / rc + 2 * rMax);
        }
        double radius = 0.25;
        double property = 1.0;

        for (int i = 1; i <= N; i++) {
            Particle particle = new Particle(i, radius, property);
            particle.setX((new Random().nextDouble()) * L);
            particle.setY((new Random().nextDouble()) * L);
            particles.add(particle);
        }
        RandomInputFileWriter.writeRandomInput(particles, L, N, 0);
       staticPath="src/main/resources/random_particles_static_" + N + ".txt";
       dynamicPath="src/main/resources/random_particles_dynamic_" + N + ".txt";
    }

    public void placeParticles(List<Particle> particles) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(dynamicPath));
            reader.readLine();
            String line = reader.readLine();
            int id = 0;
            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");
                String[] ans = line.split(" ");
                particles.get(id).setX(Double.parseDouble(ans[0]));
                particles.get(id++).setY(Double.parseDouble(ans[1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.print("Could not open file: ");
            e.printStackTrace();
        }
    }
}
