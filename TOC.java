import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.util.Stack;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.Charset;

class TOC {
    public static void main(String[] args) throws Exception {
        Path tocPath = Paths.get(args[0]);
        PDDocument document = PDDocument.load( args[1] );
        PDDocumentOutline outline =  new PDDocumentOutline();
        document.getDocumentCatalog().setDocumentOutline( outline );
        List pages = document.getDocumentCatalog().getAllPages();
        Stack<Integer> indent_levels = new Stack<Integer>();
        Stack<PDOutlineNode> lineage = new Stack<PDOutlineNode>();
        indent_levels.push(-1);
        lineage.push(outline);
        List<String> lines = Files.readAllLines(tocPath,
                                                Charset.defaultCharset());
        for (String line : lines) {
            Entry entry = parseEntry(line);
            PDOutlineItem item = new PDOutlineItem();
            item.setTitle(entry.title);
            PDPageFitWidthDestination dest = new PDPageFitWidthDestination();
            dest.setPage( (PDPage) pages.get(entry.pageNum-1) );
            item.setDestination(dest);
            while (indent_levels.peek() >= entry.indent) {
                indent_levels.pop();
                lineage.pop();
            }
            lineage.peek().appendChild(item);
            lineage.push(item);
            indent_levels.push(entry.indent);
        }
        
        outline.openNode();

        document.save( args[2] );
    }
    
    static class Entry {
        public int indent;
        public String title;
        public int pageNum;
    }
    
    static Entry parseEntry(String line) {
        Entry entry = new Entry();
        int i = 0;
        // read indentation level
        while (true) {
            if (line.charAt(i) == '\t')
                entry.indent += 4;
            else if (line.charAt(i) == ' ')
                ++entry.indent;
            else
                break;
            ++i;
        }
        int titleStart = i;
        i = line.length() - 1;
        while (line.charAt(i) != '\t' && line.charAt(i) != ' ') --i;
        ++i;
        entry.pageNum = Integer.parseInt(line.substring(i));
        while (line.charAt(i) == '\t' || line.charAt(i) == ' ') --i;
        entry.title = line.substring(titleStart, i);
        return entry;
    }
}
