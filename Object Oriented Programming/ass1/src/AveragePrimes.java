// ID: 212945760

/**
 * A class which calculates the average of the prime numbers between 2 and the given number.
 *
 * @author Ori Dabush
 */
public class AveragePrimes {

    /**
     * The isPrime function which checks if the given number is a prime number.
     *
     * @param num the given number, the function assumes that it is greater than 2 because this case is checked
     *            in the main function.
     * @return true/false - if the number is/isn't a prime number.
     */
    static boolean isPrime(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * The main function which takes the given number and calculates the average of the prime numbers between
     * 2 and the given number.
     *
     * @param args is the arguments passed to the main function in the command line.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid value");
        } else {
            int n = Integer.parseInt(args[0]);
            if (n <= 1) {
                System.out.println("Invalid value");
            } else {
                int sum = 0, count = 0;
                for (int i = 2; i < n; i++) {
                    if (isPrime(i)) {
                        count++;
                        sum += i;
                    }
                }
                System.out.println((float) sum / count);
            }
        }
    }
}
