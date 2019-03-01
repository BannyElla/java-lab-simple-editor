package part2SimpleEditor;

import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Ella
 */
public class TreeWidget {

    private JScrollPane treeWithscroll;
    private SimpleEditor mainWindow;
    private String selectedFile;
    private JTree tree;

    TreeWidget(File rootDirectory, SimpleEditor mainWindow) {
        this.mainWindow = mainWindow;
        initCatalogDirectory(rootDirectory);
    }

    public JScrollPane getTreeCatalogDirectory() {
        return treeWithscroll;
    }

    private void initCatalogDirectory(File rootDirectory) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootDirectory, true);
        tree = new JTree(ScanDirectory.walk_throw_directory(rootDirectory, node));
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.setAsksAllowsChildren(true);
        treeWithscroll = new JScrollPane(tree,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addMouseListenerToTree();
    }

    private void addMouseListenerToTree() {
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mainWindow.doChangesExist()) {
                    String oldSelectedFile = mainWindow.getSelectedFile();
                    int confirm = mainWindow.getConfirm();
                    if (confirm == JOptionPane.YES_OPTION) {
                        String newText = mainWindow.getEditedText();
                        try {
                            FileManager.saveFile(oldSelectedFile, newText);
                            JOptionPane.showMessageDialog(mainWindow, 
                                                "Файл успешно сохранен",
                                                "Сообщение",
                                                JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            String warning = oldSelectedFile + " недоступен для записи";
                            JOptionPane.showMessageDialog(mainWindow, warning);
                        }
                    } else if (confirm == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                } 
                    showFileContent(e);
            }

            private void showFileContent(MouseEvent e) {
                JTree selectedElement = (JTree) e.getSource();
                selectedFile = getFileName(selectedElement);
                if (new File(selectedFile).isFile()) {
                    mainWindow.setSelectedFile(selectedFile);
                    try {
                        mainWindow.showContentFile(FileManager.readFile(selectedFile));
                    } catch (IOException ex) {
                        String warning = selectedFile + " недоступен для чтения";
                        JOptionPane.showMessageDialog(mainWindow, warning);
                    } catch (HeadlessException ex) {
                        String warning = "Что-то пошло не так: " + ex.getMessage();
                        JOptionPane.showMessageDialog(mainWindow, warning);
                    }
                }
            }
        });
    }

    private String getFileName(JTree selectedElement) {
        TreePath selectionPath = selectedElement.getLeadSelectionPath();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectionPath.getPathCount(); ++i) {
            sb.append(selectionPath.getPathComponent(i));
            sb.append(FileManager.FILE_SEPARATOR);
        }
        return sb.toString();
    }
}
