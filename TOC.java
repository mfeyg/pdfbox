import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

class TOC {
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        System.out.println(filename);
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            System.out.println(line);
            System.out.println(getIndentation(line));
        }
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
