import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Xinci Ma
 *
 */
public final class Newton1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        // Initial estimate of the square root is set to half of the input value
        double estimate = x / 2;
        double error = 1;

        while (error > 0.0001) {
            // Store the current estimate in a variable for later use
            double previousEstimate = estimate;
            // Update the estimate using the Babylonian method formula
            estimate = (estimate + x / estimate) / 2;
            // Calculate the error as the absolute value of the difference between the current estimate and the previous estimate, divided by the previous estimate
            error = (estimate - previousEstimate) / previousEstimate;
            // Make sure error is positive
            if (error < 0) {
                error = -error;
            }
        }

        // Return the final estimate as the approximate square root of the input value
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

        // variable to keep track of user's response
        String response = "y";
        // loop until user's response is "n"
        while (response.equals("y")) {
            output.println("Enter a positive number: ");
            double x = input.nextDouble();
            double estimate = x / 2;
            double error = 1;

            while (error > 0.0001) {
                double previousEstimate = estimate;
                estimate = (estimate + x / estimate) / 2;
                error = (estimate - previousEstimate) / previousEstimate;
                if (error < 0) {
                    error = -error;
                }
            }
            // Print the result
            output.println("Square root of " + x + " is " + estimate);
            output.println(
                    "Do you want to calculate another square root? (y/n)");
            response = input.nextLine();
        }
        // Close input and output streams
        input.close();
        output.close();
    }
}
