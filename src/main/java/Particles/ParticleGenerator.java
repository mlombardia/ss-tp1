package Particles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ParticleGenerator {
    public static String staticPath = "src/main/resources/Static100.txt";
    public static String dynamicPath = "src/main/resources/Dynamic100.txt";
    public static int N;
    public static int L;
    public ParticleGenerator() {
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