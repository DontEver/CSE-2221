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
public final class Newton3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error epsilon.
     *
     * @param x
     *            number to compute square root of
     * @param epsilon
     *            relative error
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        // initial estimate is half of the input number
        double estimate = x / 2;
        double error = 1;

        // Loop to improve the estimate
        while (error > epsilon) {
            // store the current estimate to calculate the error
            double previousEstimate = estimate;
            // update the estimate using Babylonian method
            estimate = (estimate + x / estimate) / 2;
            // calculate the error as the difference between current and previous estimate divided by previous estimate
            error = (estimate - previousEstimate) / previousEstimate;
            // check if the error is negative, if so make it positive
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

        output.println("Enter the value of epsilon: ");
        double epsilon = input.nextDouble();
        // variable to check if the user wants to continue calculating square roots
        String response = "y";
        while (response.equals("y")) {
            output.println("Enter a number: ");
            double x = input.nextDouble();
            // call the sqrt method to calculate square root of x with the given epsilon
            double estimate = sqrt(x, epsilon);

            output.println("Square root of " + x + " is " + estimate);
            output.println(
                    "Do you want to calculate another square root? (y/n)");
            response = input.nextLine();
        }
        // close the input and output streams
        input.close();
        output.close();
    }

}
