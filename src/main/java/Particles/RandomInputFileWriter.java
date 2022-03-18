package Particles;

import Particles.Particle;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RandomInputFileWriter {

    private static String format(Double number){
        int eFactor = 0;
        while (number.intValue() > 10){
            number /= 10;
            eFactor++;
        }

        String formatted = new DecimalFormat("#.#######").format(number);
        return String.format("%se+0%d", formatted.replace(",","."), eFactor);
    }

    public static void writeRandomInput(List<Particle> particles, Integer L, Integer quantity, Integer time){

        //static output
        List<String> lines = new ArrayList<>();
        lines.add(quantity.toString());
        lines.add(L.toString());
        for(Particle p : particles){
            StringBuilder m = new StringBuilder(p.getRadius() + " " + p.getProperty());
            lines.add(m.toString());
        }
        try {
            Files.write(Paths.get("src/main/resources/random_particles_static_" + quantity + ".txt"), lines, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }

        //dynamic output
        lines = new ArrayList<>();
        lines.add(time.toString());

        for(Particle p : particles) {
            StringBuilder m = new StringBuilder();
            m.append(format(p.getX())).append(" ").append(format(p.getY()));
            lines.add(m.toString());
        }

        try {
            Files.write(Paths.get("src/main/resources/random_particles_dynamic_" + quantity + ".txt"), lines, Charset.forName("UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
