import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.util.Stack;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

class TOC {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        System.out.println(filename);
        Stack<int> indent_levels = new Stack<int>();
        Stack<PDOutlineItem> lineage = new Stack<PDOutlineItem>();
        PDOutlineItem outline = new PDOutlineItem();
        indent_levels.push(0);
        lineage.push(outline);
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            int indentation = line.getIndentation();
            if (indentation > indent_levels.peek()) {
                indent_levels.push(indentation);
                lineage.peek().appendChild();
                //child
            }
            else if (indentation == stack.peek()) {
                // sibling
            }
            else {

            }
        }
    }

    static String getTitle(String line) {
        
    }

    static int getIndentation(String line) {
        int result = 0;
        int i = 0;
        while (true) {
            if (line.charAt(i) == '\t')
                result += 4;
            else if (line.charAt(i) == ' ')
                ++result;
            else
                break;
            ++i;
        }
        return result;
    }
}
