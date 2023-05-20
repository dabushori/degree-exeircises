// ID - 212945760

import game.animation.AnimationRunner;
import game.levels.LevelInformation;
import game.operation.GameFlow;
import menu.Menu;
import menu.MenuAnimation;
import menu.tasks.ShowHiScoresTask;
import menu.tasks.Task;
import parsing.LevelParser;
import parsing.Parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * This is the Ass7Game class, which is responsible for operating the game.
 *
 * @author Ori Dabush.
 */
public class Ass7Game {
    /**
     * This is the main function which operate the level that its path is given, or a default level if no path is given.
     *
     * @param args the level's path.
     */
    public static void main(String[] args) {
        AnimationRunner runner = new AnimationRunner();

        Menu<Task<Void>> menu = new MenuAnimation<>("Menu Title", runner.getKeyboardSensor());

        Task<Void> highScore = new ShowHiScoresTask(runner);

        menu.addSelection("h", "press h to show highscores", highScore);

        Task<Void> game = new Task<Void>() {
            @Override
            public Void run() {
                GameFlow game = new GameFlow(runner);
                List<LevelInformation> levels = null;
                String pathToLevels = null;

                if (args.length == 0 || (args.length == 1 && args[0].equals("${args}"))) {
                    pathToLevels = "definitions\\hard_level_definitions.txt";
                } else {
                    pathToLevels = args[0];
                }

                Reader reader = null;
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(pathToLevels);
                try {
                    reader = new InputStreamReader(is);
                    List<String> lines = Parser.getLines(reader);
                    LevelParser parser = new LevelParser(lines);
                    levels = parser.parse();
                } catch (Exception e) {
                    throw new RuntimeException("cant open given file!");
                }

                game.runLevels(levels);

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    System.out.print("");
                }

                return null;

//                GameFlow game = new GameFlow(runner);
//                List<LevelInformation> levels = new LinkedList<>();
//                levels.add(new DirectHit());
//                levels.add(new Emoji());
//                levels.add(new BlackAndWhite());
//                levels.add(new Matrix());
//
//                List<Integer> levelsFlow = new LinkedList<>();
//                for (String str : args) {
//                    try {
//                        int num = Integer.parseInt(str);
//                        if (1 <= num && num <= levels.size()) {
//                            levelsFlow.add(num);
//                        }
//                    } catch (Exception e) {
//                        System.out.print("");
//                    }
//                }
//                if (levelsFlow.size() == 0) {
//                    levelsFlow.add(1);
//                    levelsFlow.add(2);
//                    levelsFlow.add(3);
//                    levelsFlow.add(4);
//                }
//
//                List<LevelInformation> currentGameLevels = new LinkedList<>();
//                for (int levelNumber : levelsFlow) {
//                    currentGameLevels.add(levels.get(levelNumber - 1));
//                }
//
//                game.runLevels(currentGameLevels);
//                return null;
            }
        };
        menu.addSelection("s", "press s to play", game);

        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };
        menu.addSelection("q", "press q to quit the game", quit);

        while (true) {

            runner.run(menu);

            Task<Void> task = menu.getStatus();

            task.run();
        }
    }
}
