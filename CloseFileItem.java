package part2SimpleEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Ella
 */
public class CloseFileItem extends SaveConfirmation {

    private String selectedFile;
    private String currentSelectedFile;

    CloseFileItem(String path, SimpleEditor mainWindow) {
        super(path, mainWindow, "Close File");
    }

    @Override
    protected void addListener() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                currentSelectedFile = mainWindow.getSelectedFile();
                if (saveConfirm(mainWindow, path, currentSelectedFile)) {
                    mainWindow.closeFile();
                }
            }
        });
    }
}
