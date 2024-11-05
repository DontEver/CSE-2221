import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
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
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println(
                "This program approximates a given constant μ with the de Jager formula.");

        out.print("Enter the constant to be approximated: ");
        double mu = getPositiveDouble(in, out);

        out.println("Choose four personal numbers to use in the formula.");

        out.print("Enter the first personal number: ");
        double w = getPositiveDoubleNotOne(in, out);

        out.print("Enter the second personal number: ");
        double x = getPositiveDoubleNotOne(in, out);

        out.print("Enter the third personal number: ");
        double y = getPositiveDoubleNotOne(in, out);

        out.print("Enter the fourth personal number: ");
        double z = getPositiveDoubleNotOne(in, out);

        double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -0.5, -1 / 3,
                -1 / 4, 0.0, 1 / 4, 1 / 3, 0.5, 1.0, 2.0, 3.0, 4.0, 5.0 };
        double bestApproximation = 0;
        double bestError = Double.MAX_VALUE;
        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        int i = 0;
        // outer loop that iterates through all possible values of e1
        while (i < exponents.length) {
            double e1 = exponents[i];
            // inner loop that iterates through all possible values of e2
            int j = 0;
            while (j < exponents.length) {
                double e2 = exponents[j];
                // inner loop that iterates through all possible values of e3
                int k = 0;
                while (k < exponents.length) {
                    double e3 = exponents[k];
                    // inner loop that iterates through all possible values of e4
                    int l = 0;
                    while (l < exponents.length) {
                        double e4 = exponents[l];
                        // calculate the approximation using the four exponents
                        double approximation = Math.pow(w, e1) * Math.pow(x, e2)
                                * Math.pow(y, e3) * Math.pow(z, e4);
                        // calculate the error between the approximation and mu
                        double error = Math.abs(mu - approximation) / mu;
                        // if the error is smaller than the best error found so far, update the best approximation and best error
                        if (error < bestError) {
                            bestApproximation = approximation;
                            bestError = error;
                            a = e1;
                            b = e2;
                            c = e3;
                            d = e4;
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }

        out.println("The best approximation of " + mu + " is "
                + bestApproximation + ", error: " + (bestError * 100) + "%"
                + ", a, b, c, d: " + a + ", " + b + ", " + c + ", " + d);

        in.close();
        out.close();
    }
}
