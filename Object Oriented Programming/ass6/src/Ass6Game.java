// ID - 212945760

import game.animation.AnimationRunner;
import game.levels.LevelInformation;
import game.levels.level3.BlackAndWhite;
import game.levels.level4.Matrix;
import game.levels.level2.Emoji;
import game.levels.level1.DirectHit;
import game.operation.GameFlow;

import java.util.LinkedList;
import java.util.List;

/**
 * The gameOperation.Ass3Game class, which initializes a game and runs it.
 *
 * @author Ori Dabush
 */
public class Ass6Game {

    /**
     * The main method, which operates the gameOperation.trying.Ass5Game class.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        AnimationRunner runner = new AnimationRunner();
        GameFlow game = new GameFlow(runner);
        List<LevelInformation> levels = new LinkedList<>();
        levels.add(new DirectHit());
        levels.add(new Emoji());
        levels.add(new BlackAndWhite());
        levels.add(new Matrix());

        List<Integer> levelsFlow = new LinkedList<>();
        for (String str : args) {
            try {
                int num = Integer.parseInt(str);
                if (1 <= num && num <= levels.size()) {
                    levelsFlow.add(num);
                }
            } catch (Exception e) {
                System.out.print("");
            }
        }
        if (levelsFlow.size() == 0) {
            levelsFlow.add(1);
            levelsFlow.add(2);
            levelsFlow.add(3);
            levelsFlow.add(4);
        }

        List<LevelInformation> currentGameLevels = new LinkedList<>();
        for (int levelNumber : levelsFlow) {
            currentGameLevels.add(levels.get(levelNumber - 1));
        }

        game.runLevels(currentGameLevels);
    }
}
