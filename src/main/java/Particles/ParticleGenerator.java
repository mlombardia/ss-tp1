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
    public static int M;
    public ParticleGenerator(String[] args) {
        this.staticPath = args[0];
        this.dynamicPath = args[1];
        rc = args[2].equals("-rc")? Double.parseDouble(args[3]) : (args[4].equals("-rc")? Double.parseDouble(args[5]) : 1.0);
        M = args[2].equals("-m")? Integer.parseInt(args[3]) : (args[4].equals("-m")? Integer.parseInt(args[5]) : 1);
        isPeriodic = args[2].equals("-periodic") || args[4].equals("-periodic") || args[6].equals("-periodic") || args[7].equals("-periodic");
        isRandom = args[2].equals("-r") || args[4].equals("-r") || args[6].equals("-r") || args[7].equals("-r");
    }

    public void generate(List<Particle> particles) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(staticPath));
            String line = reader.readLine();
            int id = 1;
            N = Integer.parseInt(line.trim());
            L = Integer.parseInt(reader.readLine().trim());
            line = reader.readLine();

            while (line != null) {
                line = line.trim().replaceAll("\\s+", " ");
                String[] ans = line.split(" ");
                Particle particle = new Particle(id++, Double.parseDouble(ans[0]), Double.parseDouble(ans[1]));
                particles.add(particle);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.print("Could not open file: ");
            e.printStackTrace();
        }
    }

    public void generateRandom(List<Particle> particles){
        N = 100;
        L = 100;
        double radius = 0.25;
        double property = 1.0;

        for (int i = 1;i <= N; i++){
            Particle particle = new Particle(i, radius, property);
            particle.setX((new Random().nextDouble())*L);
            particle.setY((new Random().nextDouble())*L);
            particles.add(particle);
        }
        RandomInputFileWriter.writeRandomInput(particles, L, N, 0);
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
