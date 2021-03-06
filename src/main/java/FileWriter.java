import Particles.Particle;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    public static void writeOutput(List<Particle> particles, Double L, Integer quantity, Long time, Double rc){
        Path fileStatic = Paths.get("graphic/StaticOutput");
        Path fileNeighbour = Paths.get("graphic/NeighbourOutput");

        //static output
        List<String> lines = new ArrayList<>();
        lines.add(quantity.toString());
        lines.add(L.toString());
        for(Particle p : particles){
            StringBuilder m = new StringBuilder(p.getId()-1 + " " + p.getRadius() + " " + p.getX() + " " + p.getY());
            lines.add(m.toString());
        }
        try {
            Files.write(fileStatic, lines, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }

        //neighbour output
        lines = new ArrayList<>();
        lines.add(time.toString());
        lines.add(rc.toString());

        for(Particle p : particles) {
            StringBuilder m = new StringBuilder(p.getId()-1 + " ");
            //if (p.getNeighbours().size() != 0) {
                    m.append(p.printNeighboursIds() + " ");
                lines.add(m.toString());
            //}
        }

        try {
            Files.write(fileNeighbour, lines, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
