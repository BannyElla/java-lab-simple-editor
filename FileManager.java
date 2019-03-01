package part2SimpleEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ella
 */
public class FileManager {
    private static final String LINE_SEPARATOR = "\n";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    public static String readFile(String path) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();            
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(LINE_SEPARATOR);
                }
        sb.deleteCharAt(sb.length()-1);

        br.close();
        reader.close();
        return sb.toString();
    }
    
    public static void saveFile(String path, String content) throws IOException {
        FileWriter writer = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(content);
        bw.flush();
        writer.close();
    }

}
