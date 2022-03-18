package Particles;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ParticleGenerator {
    public String staticPath;
    public String dynamicPath;
    public static boolean isPeriodic;
    public static boolean isRandom;
    final private static int notSpecified = -1;
    public static int N;
    public static int L;
    public static double rc;
    public static double rMax = notSpecified;
    public static int M;
    final private static int totalParticles = 10000;
    final private static int totalLength = 100;

    private Options getCommandlineOptions() {
        Options options = new Options();
        options.addOption("sp", true, "static path");
        options.addOption("dp", true, "dinamic path");
        options.addOption("m", true, "matrix");
        options.addOption("rc", true, "critic radius");
        options.addOption("n", true, "particles amount");
        options.addOption("l", true, "total length");
        options.addOption("randomize", false, "random");
        options.addOption("r", false, "random");
        options.addOption("periodic", false, "periodic");
        options.addOption("p", false, "periodic");
        options.addOption("h", false, "help");
        options.addOption("help", false, "help");
        return options;
    }

    private static void help() {
        System.out.println("Please remember the valid options are: ");
        System.out.println("-sp <static_file.txt> to specify a static input file,");
        System.out.println("-dp <dynamic_file.txt> to specify a dynamic input file,");
        System.out.println("-m <value> to specify the matrix size,");
        System.out.println("-rc <value> to specify the critical radius,");
        System.out.println("-n <value> to specify the number of particles,");
        System.out.println("-l <value> to specify the area length,");
        System.out.println("-randomize or -r to generate random particles,");
        System.out.println("-p or -periodic to use periodic contour conditions,");
        System.out.println("-h or -help to see the menu.");
    }

    public ParticleGenerator(String[] args) {
        Options options = getCommandlineOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
                help();
                System.exit(1);
            }
            if (commandLine.hasOption("sp")) {
                this.staticPath = commandLine.getOptionValue("sp");
            }
            if (commandLine.hasOption("dp")) {
                this.dynamicPath = commandLine.getOptionValue("dp");
            }
            if (commandLine.hasOption("rc")) {
                rc = Double.parseDouble(commandLine.getOptionValue("rc"));
            } else {
                rc = 1;
            }
            if (commandLine.hasOption("m")) {
                M = Integer.parseInt(commandLine.getOptionValue("m"));
            } else {
                M = notSpecified;
            }
            if (commandLine.hasOption("p") || commandLine.hasOption("periodic")) {
                isPeriodic = true;
            } else {
                isPeriodic = false;
            }
            if (commandLine.hasOption("r") || commandLine.hasOption("randomize")) {
                isRandom = true;
            } else {
                isRandom = false;
            }
            if (commandLine.hasOption("n")) {
                N = Integer.parseInt(commandLine.getOptionValue("n"));
            } else {
                N = notSpecified;
            }
            if (commandLine.hasOption("l")) {
                L = Integer.parseInt(commandLine.getOptionValue("l"));
            } else {
                L = notSpecified;
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage() + ".");
            help();
            System.exit(1);
        }
    }

    public void generate(List<Particle> particles) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(staticPath));
            String line = reader.readLine();
            int id = 1;
            N = N == notSpecified ? Integer.parseInt(line.trim()) : N;
            L = L == notSpecified ? Integer.parseInt(reader.readLine().trim()) : L;
            M = M == notSpecified ? (int) Math.floor(L / rc + 2 * rMax) : M;
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
        N = N == notSpecified ? totalParticles : N;
        L = L == notSpecified ? totalLength : L;
        M = M == notSpecified ? (int) Math.floor(L / rc + 2 * rMax) : M;

        double radius = 0.25;
        double property = 1.0;

        for (int i = 1; i <= N; i++) {
            Particle particle = new Particle(i, radius, property);
            particle.setX((new Random().nextDouble()) * L);
            particle.setY((new Random().nextDouble()) * L);
            particles.add(particle);
        }
        RandomInputFileWriter.writeRandomInput(particles, L, N, 0);
        staticPath = "src/main/resources/random_particles_static_" + N + ".txt";
        dynamicPath = "src/main/resources/random_particles_dynamic_" + N + ".txt";
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
