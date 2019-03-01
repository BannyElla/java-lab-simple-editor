package part2SimpleEditor;

import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Ella
 */
public abstract class SaveConfirmation extends JMenuItem {

    protected String path;
    protected SimpleEditor mainWindow;

    SaveConfirmation(String path, SimpleEditor mainWindow, String name) {
        super(name);
        this.path = path;
        this.mainWindow = mainWindow;
        addListener();
    }

    protected abstract void addListener();

    protected boolean saveConfirm(SimpleEditor mainWindow, String path, String selectedFile) {
        if (mainWindow.doChangesExist()) {
            int confirm = mainWindow.getConfirm();
            switch (confirm) {
                case JOptionPane.YES_OPTION:
                    String newText = mainWindow.getEditedText();
                    try {
                        FileManager.saveFile(selectedFile, newText);
                        JOptionPane.showMessageDialog(mainWindow,
                                "Файл успешно сохранен",
                                "Сообщение",
                                JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    } catch (IOException ex) {
                        String warning = path + " недоступен для записи";
                        JOptionPane.showMessageDialog(mainWindow, warning);
                    }
                case JOptionPane.CANCEL_OPTION:
                    return false;
                case JOptionPane.NO_OPTION:
                    return true;
                default:
                    break;
            }
        }
        return true;
    }
}
