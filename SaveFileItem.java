package part2SimpleEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Ella
 */
public class SaveFileItem extends JMenuItem {

    private String path;
    private String selectedFile;
    private SimpleEditor mainWindow;
    private String newText;

    SaveFileItem(String path, SimpleEditor mainWindow) {
        super("Save File");
        this.mainWindow = mainWindow;
        addListener();
    }

    private void addListener() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    newText = mainWindow.getEditedText();
                    selectedFile = mainWindow.getSelectedFile();
                    FileManager.saveFile(selectedFile, newText);
                    mainWindow.setCompareContent(newText);
                    JOptionPane.showMessageDialog(mainWindow, 
                                                "Файл успешно сохранен",
                                                "Сообщение",
                                                JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    String warning = path + " недоступен для записи";
                    JOptionPane.showMessageDialog(mainWindow, warning);
                }
            }
        });
    }
}
