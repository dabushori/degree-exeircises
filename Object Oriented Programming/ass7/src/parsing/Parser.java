// ID - 212945760

package parsing;

import biuoop.DrawSurface;
import game.levels.Background;
import game.levels.GameLevel;
import game.objects.Sprite;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the Parser class which will be responsible of generic parsing methods.
 *
 * @author Ori Dabush.
 */
public class Parser {

    /**
     * A method to get a list of sections with given names.
     *
     * @param lines the lines of the original file.
     * @param name  the name of the section (for example BLOCKS).
     * @return a list of sections with the given names.
     */
    public static List<List<String>> getSections(List<String> lines, String name) {
        String start = "START_" + name, end = "END_" + name;
        List<String> copy = new LinkedList<>(lines);
        List<List<String>> sections = new LinkedList<>();

        while (true) {
            // if only one of them exists
            if ((!copy.contains(start) && copy.contains(end)) || (copy.contains(start) && !copy.contains(end))) {
                return null;
            }

            if (!copy.contains(start) && !copy.contains(end)) {
                break;
            }

            List<String> sec = new LinkedList<>();
            int first = copy.indexOf(start), last = copy.indexOf(end);
            for (int i = first + 1; i < last; i++) {
                sec.add(copy.get(i));
            }
            for (int i = first; i <= last; i++) {
                copy.remove(0);
            }

            sections.add(sec);
        }
        return sections;
    }

    /**
     * A method to get a section with a given name.
     *
     * @param lines the lines of the original file.
     * @param name  the section's name.
     * @return the section.
     */
    public static List<String> getSection(List<String> lines, String name) {
        String start = "START_" + name, end = "END_" + name;
        List<String> section = new LinkedList<>();
        int first = lines.indexOf(start), last = lines.indexOf(end);
        if (first == -1 || last == -1) {
            return null;
        }
        for (int i = first + 1; i < last; i++) {
            if (!lines.get(i).startsWith("#") && !lines.get(i).equals("")) {
                section.add(lines.get(i));
            }
        }
        return section;
    }

    /**
     * A method to get a list of the lines of a file using a given reader.
     *
     * @param reader the given reader.
     * @return a list of the lines of the file.
     */
    public static List<String> getLines(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> lines = new LinkedList<>();
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (!line.equals("") && !line.startsWith("#")) {
                    lines.add(line);
                }
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("cant open file");
        }
        return lines;
    }

    /**
     * A method to parse a color out of a string.
     *
     * @param line the string.
     * @return the color that was parsed.
     */
    public static Color parseColor(String line) {
        line = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        if (line.startsWith("RGB(")) {
            line = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            int r = Integer.parseInt(line.substring(0, line.indexOf(",")));
            line = line.substring(line.indexOf(",") + 1);
            int g = Integer.parseInt(line.substring(0, line.indexOf(",")));
            line = line.substring(line.indexOf(",") + 1);
            int b = Integer.parseInt(line);
            return new Color(r, g, b);
        }
        switch (line) {
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "gray":
                return Color.GRAY;
            case "lightGray":
                return Color.LIGHT_GRAY;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "red":
                return Color.RED;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                return null;
        }
    }

    /**
     * A method to parse a background out of a line.
     *
     * @param line the line.
     * @return the background as a Sprite object.
     */
    public static Sprite parseBackground(String line) {
        if (line.startsWith("color")) {
            Color color = parseColor(line);
            return new Background() {
                @Override
                public void drawOn(DrawSurface d) {
                    d.setColor(color);
                    d.fillRectangle(0, 0, GameLevel.WIDTH, GameLevel.HEIGHT);
                }
            };
        }
        line = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
        Image img = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
        try {
            img = ImageIO.read(is);
        } catch (Exception e) {
            throw new RuntimeException("can't open background");
        }
        Image finalImg = img;
        return new Background() {
            @Override
            public void drawOn(DrawSurface d) {
                d.drawImage(0, 0, finalImg);
            }
        };
    }
}
