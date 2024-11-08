import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Xinci Ma
 *
 */
public final class RSSReader {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSReader() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals(
                "channel") : "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        // Output the HTML header
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");

        // Output the channel's title
        int titleIndex = getChildElement(channel, "title");
        String title = channel.child(titleIndex).child(0).label();
        if (title.isEmpty()) {
            title = "Untitled";
        }
        out.println(title);
        out.println("</title>");
        out.println("</head>");

        // Output the HTML body
        out.println("<body>");

        // Output the channel's link
        int linkIndex = getChildElement(channel, "link");
        String link = channel.child(linkIndex).child(0).label();
        out.println("<h1><a href=\"" + link + "\">" + title + "</a></h1>");

        // Output the channel's description
        int descIndex = getChildElement(channel, "description");
        String description = channel.child(descIndex).child(0).label();
        if (description.isEmpty()) {
            description = "No description available";
        }
        out.println("<p>" + description + "</p>");

        // Output the HTML table for the news items
        out.println("<table border=\"1\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>Headline</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        int index = -1;
        for (int i = 0; i < xml.numberOfChildren() && index == -1; i++) {
            if (xml.child(i).isTag() && xml.child(i).label().equals(tag)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        // Write an HTML row tag
        out.println("<tr>");

        // Get the index locations of various child tags within the "item" object
        int dateIndex = getChildElement(item, "pubDate");
        int sourceIndex = getChildElement(item, "source");
        int titleIndex = getChildElement(item, "title");
        int linkIndex = getChildElement(item, "link");
        int descriptionIndex = getChildElement(item, "description");

        // If "pubDate" tag exists, write the date; otherwise, write "No date available"
        if (dateIndex != -1) {
            out.println(
                    "<td>" + item.child(dateIndex).child(0).label() + "</td>");
        } else {
            out.println("<td>No date available</td>");
        }

        // If "source" tag exists, write the source link and label; otherwise, write "No source available"
        if (sourceIndex != -1) {
            out.println("<td>");
            out.print("<a href=\"");
            out.print(item.child(sourceIndex).attributeValue("url"));
            out.println(
                    "\">" + item.child(sourceIndex).child(0).label() + "</a>");
            out.println("</td>");
        } else {
            out.println("<td>No source available</td>");
        }

        // Write the title or description, or "No title/description available" if neither exists or has an empty label
        out.println("<td>");
        if (linkIndex != -1) {
            out.print("<a href=\"");
            out.print(item.child(linkIndex).child(0).label());
            out.print("\">");
        }
        if (titleIndex != -1) {
            String label = item.child(titleIndex).child(0).label();
            out.print(label.isEmpty() ? "No title available" : label);
        } else if (descriptionIndex != -1) {
            String label = item.child(descriptionIndex).child(0).label();
            out.print(label.isEmpty() ? "No description available" : label);
        }
        if (linkIndex != -1) {
            out.println("</a>");
        }

        // Close the HTML row tag
        out.println("</td>");
        out.println("</tr>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompt the user to input the URL of an RSS 2.0 news feed
        out.print("Please enter the URL of an RSS 2.0 news feed: ");
        String url = in.nextLine();

        // Initialize an XMLTree object with the provided URL
        XMLTree xml = new XMLTree1(url);

        // Check if the XML input is a valid RSS 2.0 file
        if (xml.label().equals("rss") && xml.hasAttribute("version")
                && xml.attributeValue("version").equals("2.0")) {
            // Prompt the user to enter the name of the HTML output file and create an output stream to it
            out.print(
                    "Please enter the name of the output file with a .html extension: ");
            String filename = in.nextLine();
            SimpleWriter fileOut = new SimpleWriter1L(filename);

            // Call methods to output the header and table rows to the HTML file
            XMLTree channel = xml.child(0);
            outputHeader(channel, fileOut);

            // Loop over the child elements of the RSS feed's "channel" element and call processItem() on each "item" element
            for (int i = 0; i < channel.numberOfChildren(); i++) {
                XMLTree child = channel.child(i);
                if (child.isTag() && child.label().equals("item")) {
                    processItem(child, fileOut);
                }
            }

            // Call method to output the footer to the HTML file and close the output stream
            outputFooter(fileOut);
            fileOut.close();
        } else {
            // Output an error message if the provided URL is not an RSS 2.0 file
            out.println("The provided URL is not a valid RSS 2.0 url.");
        }
        out.println("Finished");
        // Close the input and output streams
        in.close();
        out.close();

    }
}