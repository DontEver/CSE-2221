import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Xinci Ma
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        if (exp.label().equals("number")) {
            return new NaturalNumber2(exp.attributeValue("value"));
        }

        NaturalNumber leftValue, rightValue;

        if (!exp.child(0).hasAttribute("value")) {
            leftValue = evaluate(exp.child(0));
        } else {
            leftValue = new NaturalNumber2(
                    exp.child(0).attributeValue("value"));
        }

        if (!exp.child(1).hasAttribute("value")) {
            rightValue = evaluate(exp.child(1));
        } else {
            rightValue = new NaturalNumber2(
                    exp.child(1).attributeValue("value"));
        }

        NaturalNumber result = new NaturalNumber2();

        if (exp.label().equals("plus")) {
            result.copyFrom(leftValue);
            result.add(rightValue);
        } else if (exp.label().equals("minus")) {
            if (rightValue.compareTo(leftValue) > 0) {
                // Handle negative results by returning zero instead
                result.clear();
            } else {
                result.copyFrom(leftValue);
                result.subtract(rightValue);
            }
        } else if (exp.label().equals("times")) {
            result.copyFrom(leftValue);
            result.multiply(rightValue);
        } else if (exp.label().equals("divide")) {
            if (rightValue.isZero()) {
                // Handle division by zero by returning zero instead
                result.clear();
            } else {
                result.copyFrom(leftValue);
                result.divide(rightValue);
            }
        } else {
            throw new IllegalArgumentException(
                    "Unknown operator: " + exp.label());
        }

        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}