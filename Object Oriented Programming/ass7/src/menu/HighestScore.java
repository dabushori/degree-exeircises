// ID - 212945760

package menu;

import biuoop.DrawSurface;
import game.animation.Animation;
import game.levels.level2.EmojiDesign;
import game.objects.Sprite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This is the HighestScore class, which is responsible for updating the highest score and creating the high-score
 * screen animation.
 *
 * @author Ori Dabush
 */
public class HighestScore implements Animation {
    /**
     * A method to parse the score (as a number) from a given line.
     *
     * @param line the given line.
     * @return the score (as a number).
     */
    private static int parseScore(String line) {
//        "The highest score so far is: XXX"
        int scoreIndex = line.indexOf(':') + 2;
        return Integer.parseInt(line.substring(scoreIndex));
    }

    /**
     * A method to get the highest score line (the first line of the highscores file.txt).
     *
     * @return the first line of the highscores file.txt.
     */
    public static String getHighestScore() {
        File file = new File("highscores.txt");
        if (!file.exists()) {
            return "No games played yet.";
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            return line;
        } catch (Exception e) {
            throw new RuntimeException("high-score reading failed");
        }
    }

    /**
     * A method to update the saved highest score.
     *
     * @param currentScore the current game's score.
     */
    public static void updateHighestScore(int currentScore) {
        File file = new File("highscores.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException("high-score updating failed");
            }
        }

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int highestScore = currentScore;
            if (line != null) {
                highestScore = Math.max(highestScore, parseScore(line));
            }

            writer = new BufferedWriter(new FileWriter(file));
            writer.write("The highest score so far is: " + highestScore);

        } catch (Exception e) {
            throw new RuntimeException("high-score updating failed");
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException("stream closing failed");
            }
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        Sprite background = new EmojiDesign();
        background.drawOn(d);
        d.drawText(50, 100, getHighestScore(), 50);
        d.drawText(50, 150, "press space to continue.", 35);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}