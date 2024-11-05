import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Xinci Ma
 *
 */
public final class Newton6 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton6() {
    }

    /**
     * Computes estimate of k-th root of x to within relative error epsilon.
     *
     * @param x
     *            number to compute k-th root of
     * @param k
     *            root
     * @param epsilon
     *            relative error
     * @return estimate of k-th root
     */
    private static double kthRoot(double x, int k, double epsilon) {
        double estimate = x / k; //initial estimate is set to x/k
        double error = 1; //initial error is set to 1
        double tmp;
        while (error > epsilon) {
            double previousEstimate = estimate; //store the current estimate in a variable for later use
            tmp = 1.0;
            for (int i = 0; i < k - 1; i++) {
                tmp *= estimate; //calculate the value of tmp by repeatedly multiplying estimate
            }
            estimate = ((k - 1) * estimate + x / tmp) / k; //update the estimate using the formula
            error = (estimate - previousEstimate) / previousEstimate; //calculate the error by comparing the current estimate to the previous estimate
            if (error < 0) {
                error = -error;
            }
        }
        return estimate;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();

        // Prompt user to enter the value of epsilon
        double epsilon = 0;
        boolean isEpsilonValid = false;
        while (!isEpsilonValid) {
            output.println("Enter the value of epsilon: ");
            String epsilonString = input.nextLine();
            if (FormatChecker.canParseDouble(epsilonString)) {
                epsilon = Double.parseDouble(epsilonString);
                if (epsilon > 0) {
                    isEpsilonValid = true;
                } else {
                    output.println("Error: Epsilon must be a positive number.");
                }
            } else {
                output.println("Error: Input is not a valid number.");
            }
        }

        // Prompt user to enter a number
        double x = 0;
        boolean isXValid = false;
        int k = 0;
        while (!isXValid) {
            output.println("Enter a number: ");
            String xString = input.nextLine();
            if (FormatChecker.canParseDouble(xString)) {
                x = Double.parseDouble(xString);
                if (x >= 0) {
                    // Prompt user to enter the root k
                    output.println("Enter the root k: ");
                    String kString = input.nextLine();
                    if (FormatChecker.canParseInt(kString)) {
                        k = Integer.parseInt(kString);
                        if (k >= 2) {
                            isXValid = true;
                        } else {
                            output.println(
                                    "Error: k must be an integer greater than or equal to 2.");
                        }
                    } else {
                        output.println("Error: Input is not a valid integer.");
                    }
                } else {
                    output.println("Goodbye!");
                    isXValid = true;
                }
            } else {
                output.println("Error: Input is not a valid number.");
            }
        }
        // While x is greater than or equal to 0, the kth root of x will be calculated using the kthRoot() method
        while (x >= 0) {
            //Calculate the kth root of x with a given epsilon value
            double estimate = kthRoot(x, k, epsilon);
            //Print the result of the kth root calculation
            output.println(k + "-th root of " + x + " is " + estimate);
            boolean isXValid1 = false;
            //Loop to validate user input for x and k
            while (!isXValid1) {
                output.println("Enter a number: ");
                String xString = input.nextLine();
                if (FormatChecker.canParseDouble(xString)) {
                    x = Double.parseDouble(xString);
                    if (x >= 0) {
                        output.println("Enter the root k: ");
                        String kString = input.nextLine();
                        if (FormatChecker.canParseInt(kString)) {
                            k = Integer.parseInt(kString);
                            if (k >= 2) {
                                isXValid1 = true;
                            } else {
                                output.println(
                                        "Error: k must be an integer greater than or equal to 2.");
                            }
                        } else {
                            output.println(
                                    "Error: Input is not a valid integer.");
                        }
                    } else {
                        output.println("Goodbye!");
                        isXValid1 = true;
                    }
                } else {
                    output.println("Error: Input is not a valid number.");
                }
            }
        }
        // close the input and output streams
        input.close();
        output.close();
    }
}
