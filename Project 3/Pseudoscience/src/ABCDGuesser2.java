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
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        boolean validInput = false;
        double value = 0;
        // Loop until valid positive real number is entered
        while (!validInput) {
            // Prompt the user for a positive real number
            out.print("Enter a positive real number: ");
            // Read the user input
            String input = in.nextLine();
            // Check if the input can be parsed into a double
            if (FormatChecker.canParseDouble(input)) {
                value = Double.parseDouble(input);
                // Check if the value is positive
                if (value > 0) {
                    // Set validInput to true to exit the loop
                    validInput = true;
                } else {
                    // Print error message and prompt user again
                    out.println(
                            "Invalid input. Please enter a positive real number.");
                }
            }
        }
        // Return the valid positive real number
        return value;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */

    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double value = -1;
        // Keep looping until a positive real number not equal to 1.0 is entered
        while (value < 0 || value == 1.0) {
            out.print("Enter a positive real number not equal to 1.0: ");
            String input = in.nextLine();
            // Check if the input can be parsed as a double
            if (FormatChecker.canParseDouble(input)) {
                value = Double.parseDouble(input);
                // Check if the value is less than or equal to 0 or equal to 1.0
                if (value <= 0 || value == 1.0) {
                    out.println(
                            "Invalid input. Please enter a positive real number not equal to 1.0.");
                }
            }
        }
        // Return the valid positive real number
        return value;
    }

    /**
     * A method that computes the best approximation of the given constant using
     * the given personal numbers and the exponents.
     *
     * @param mu
     *            the constant to be approximated
     * @param w
     *            the first personal number
     * @param x
     *            the second personal number
     * @param y
     *            the third personal number
     * @param z
     *            the fourth personal number
     * @param exponents
     *            an array of exponents
     * @param out
     *            the output stream
     * @return an array containing the best approximation, the best error, and
     *         the exponents
     */
    private static double[] computeBestApproximation(double mu, double w,
            double x, double y, double z, double[] exponents,
            SimpleWriter out) {
        double bestApproximation = 0;
        double bestError = Double.MAX_VALUE;
        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        /*
         * Nested for loops to calculate the approximation using each
         * combination of the exponents in the exponents array
         */
        for (int i = 0; i < exponents.length; i++) {
            double e1 = exponents[i];
            for (int j = 0; j < exponents.length; j++) {
                double e2 = exponents[j];
                for (int k = 0; k < exponents.length; k++) {
                    double e3 = exponents[k];
                    for (int l = 0; l < exponents.length; l++) {
                        double e4 = exponents[l];
                        // Calculate the approximation using the formula
                        double approximation = Math.pow(w, e1) * Math.pow(x, e2)
                                * Math.pow(y, e3) * Math.pow(z, e4);
                        // Calculate the error
                        double error = Math.abs(mu - approximation) / mu;
                        /*
                         * If the error of the current approximation is less
                         * than the previous best error, update the
                         * bestApproximation and bestError
                         */
                        if (error < bestError) {
                            bestApproximation = approximation;
                            bestError = error;
                            a = e1;
                            b = e2;
                            c = e3;
                            d = e4;
                        }
                    }
                }
            }
        }
        // Return an array of the best approximation, best error, and the exponents
        double[] bestApproximationInfo = { bestApproximation, bestError, a, b,
                c, d };
        return bestApproximationInfo;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Print a description of the program
        out.println(
                "This program approximates a given constant μ with the de Jager formula.");

        // Prompt the user to enter the constant to be approximated and read the input
        out.print("Enter the constant to be approximated: ");
        double mu = getPositiveDouble(in, out);

        // Print instructions for choosing personal numbers
        out.println("Choose four personal numbers to use in the formula.");

        // Prompt the user to enter the first personal number and read the input
        out.print("Enter the first personal number: ");
        double w = getPositiveDoubleNotOne(in, out);

        // Prompt the user to enter the second personal number and read the input
        out.print("Enter the second personal number: ");
        double x = getPositiveDoubleNotOne(in, out);

        // Prompt the user to enter the third personal number and read the input
        out.print("Enter the third personal number: ");
        double y = getPositiveDoubleNotOne(in, out);

        // Prompt the user to enter the fourth personal number and read the input
        out.print("Enter the fourth personal number: ");
        double z = getPositiveDoubleNotOne(in, out);

        // Define an array of exponents
        double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -0.5, -1 / 3,
                -1 / 4, 0.0, 1 / 4, 1 / 3, 0.5, 1.0, 2.0, 3.0, 4.0, 5.0 };

        // Compute the best approximation of the given constant using the given personal numbers and exponents
        double[] bestApproximationInfo = computeBestApproximation(mu, w, x, y,
                z, exponents, out);

        // Extract the best approximation, best error, and exponents from the result array
        double bestApproximation = bestApproximationInfo[0];
        double bestError = bestApproximationInfo[1];
        double a = bestApproximationInfo[2];
        double b = bestApproximationInfo[3];
        double c = bestApproximationInfo[4];
        double d = bestApproximationInfo[5];

        // Print the best approximation, best error, and exponents
        out.println("The best approximation of " + mu + " is "
                + bestApproximation + ", error: " + (bestError * 100) + "%"
                + ", a, b, c, d: " + a + ", " + b + ", " + c + ", " + d);

        // Close the input and output streams
        in.close();
        out.close();
    }
}