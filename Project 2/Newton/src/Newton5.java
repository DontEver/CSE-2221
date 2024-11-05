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
public final class Newton5 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton5() {
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
        // initial estimate is x/k
        double estimate = x / k;
        double error = 1;
        double tmp;
        // loop to improve the estimate
        while (error > epsilon) {
            // store the current estimate to calculate the error
            double previousEstimate = estimate;
            tmp = 1.0;
            // calculate the k-1 power of the estimate
            for (int i = 0; i < k - 1; i++) {
                tmp *= estimate;
            }
            // update the estimate using Newton iteration
            estimate = ((k - 1) * estimate + x / tmp) / k;
            // calculate the error as the difference between current and previous estimate divided by previous estimate
            error = (estimate - previousEstimate) / previousEstimate;
            // check if the error is negative, if so make it positive
            if (error < 0) {
                error = -error;
            }
        }
        // return the final estimate
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

        output.println("Enter a number: ");
        double x = input.nextDouble();
        // prompt user to enter the root
        output.println("Enter the root k: ");
        int k = input.nextInteger();
        // loop to calculate kth root of multiple numbers
        while (x >= 0) {
            // call the kthRoot method to calculate the kth root of x
            double estimate = kthRoot(x, k, epsilon);
            output.println(k + "-th root of " + x + " is " + estimate);
            // prompt user to enter another number
            output.println("Enter a number: ");
            x = input.nextDouble();
            if (x >= 0) {
                output.println("Enter the root k: ");
                k = input.nextInteger();
            }
        }
        output.println("Goodbye!");
        input.close();
        output.close();
    }
}
