// ID: 212945760

/**
 * This is the CharCount class.
 *
 * @author Ori Dabush
 */
public class CharCount {

    /**
     * The main function which prints all the arguments which have the last char received as an argument in it,
     * and then prints all the arguments which don't have it.
     *
     * @param args is the arguments passed to the main function in the command line.
     */
    public static void main(String[] args) {
        if (args.length < 2 || args[args.length - 1].length() != 1) {
            System.out.println("Invalid input");
        } else {
            char c = args[args.length - 1].charAt(0);
            String withoutChar = "";
            for (int i = 0; i < args.length - 1; i++) {
                if (args[i].indexOf(c) != -1) {
                    System.out.println(args[i]);
                } else {
                    withoutChar += (args[i] + "\n");
                }
            }
            System.out.println(withoutChar);
        }
    }
}
