package part2SimpleEditor;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Ella
 */
public class OpenFileItem extends SaveConfirmation {

    private String selectedFile;
    private String currentSelectedFile;
    JFileChooser chooser;

    OpenFileItem(String path, SimpleEditor mainWindow) {
        super(path, mainWindow, "Open File");
    }

    @Override
    protected void addListener() {
        chooser = new JFileChooser(path);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                currentSelectedFile = mainWindow.getSelectedFile();
                if (saveConfirm(mainWindow, path, currentSelectedFile)) {
                    fileChooser();
                }
            }
        });
    }

    private void fileChooser() {
        chooser.setDialogTitle("Выбрать файл");
        int returnVal = chooser.showOpenDialog(mainWindow);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile().getPath();
            mainWindow.setSelectedFile(selectedFile);
            try {
                mainWindow.showContentFile(FileManager.readFile(selectedFile));
            } catch (HeadlessException he) {
                String warning = he.getMessage();
                JOptionPane.showMessageDialog(mainWindow, warning, "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                String warning = selectedFile + " недоступен для чтения";
                JOptionPane.showMessageDialog(mainWindow, warning, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
