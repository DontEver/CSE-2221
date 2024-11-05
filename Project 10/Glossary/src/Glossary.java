import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Glossary {

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of the input file: ");
        SimpleReader inputReader = new SimpleReader1L();
        String inputFile = inputReader.nextLine();

        out.print("Enter the name of the output folder: ");
        String outputFolder = inputReader.nextLine();
        inputReader.close();

        Map<String, String> data = readInputFile(inputFile);
        generateHTMLFiles(data, outputFolder);

        out.println("Glossary files generated successfully.");
        out.close();
    }

    private static Map<String, String> readInputFile(String inputFile) {
        SimpleReader fileReader = new SimpleReader1L(inputFile);
        Map<String, String> data = new Map1L<>();

        while (!fileReader.atEOS()) {
            String term = fileReader.nextLine();
            StringBuilder definition = new StringBuilder();

            while (!fileReader.atEOS()) {
                String line = fileReader.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                definition.append(line);
            }

            data.add(term, definition.toString());
        }

        fileReader.close();
        return data;
    }

    private static void generateHTMLFiles(Map<String, String> data,
            String outputFolder) {
        Queue<String> terms = new Queue1L<>();
        for (Map.Pair<String, String> entry : data) {
            terms.enqueue(entry.key());
        }

        Queue<String> sortedTerms = sortTerms(terms);

        SimpleWriter indexWriter = new SimpleWriter1L(
                outputFolder + "/index.html");
        indexWriter.println("<!DOCTYPE html>");
        indexWriter.println("<html>");
        indexWriter.println("<head>");
        indexWriter.println("<title>Sample Glossary</title>");
        indexWriter.println("</head>");
        indexWriter.println("<body>");
        indexWriter.println("<h2>Sample Glossary</h2>");
        indexWriter.println("<hr />");
        indexWriter.println("<h3>Index</h3>");
        indexWriter.println("<ul>");

        while (sortedTerms.length() > 0) {
            String term = sortedTerms.dequeue();
            String definition = data.value(term);

            indexWriter.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");

            SimpleWriter termWriter = new SimpleWriter1L(
                    outputFolder + "/" + term + ".html");
            termWriter.println("<!DOCTYPE html>");
            termWriter.println("<html>");
            termWriter.println("<head>");
            termWriter.println("<title>" + term + "</title>");
            termWriter.println("</head>");
            termWriter.println("<body>");
            termWriter.println("<h2><b><i><font color=\"red\">" + term
                    + "</font></i></b></h2>"); // Modified header
            termWriter.println("<blockquote>" + definition + "</blockquote>"); // Added blockquote
            termWriter.println("<hr />"); // Added horizontal line
            termWriter.println(
                    "<p>Return to <a href=\"index.html\">index</a>.</p>"); // Updated back link
            termWriter.println("</body>");
            termWriter.println("</html>");
            termWriter.close();
        }

        indexWriter.println("</ul>");
        indexWriter.println("</body>");
        indexWriter.println("</html>");
        indexWriter.close();
    }

    public static Queue<String> sortTerms(Queue<String> terms) {
        Queue<String> sortedTerms = new Queue1L<>();

        while (terms.length() > 0) {
            String minTerm = terms.dequeue();
            Queue<String> temp = new Queue1L<>();

            while (terms.length() > 0) {
                String currentTerm = terms.dequeue();
                if (currentTerm.compareTo(minTerm) < 0) {
                    temp.enqueue(minTerm);
                    minTerm = currentTerm;
                } else {
                    temp.enqueue(currentTerm);
                }
            }

            sortedTerms.enqueue(minTerm);
            terms.transferFrom(temp);
        }

        return sortedTerms;
    }
}
