package part2SimpleEditor;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

/**
 *
 * @author Ella
 */
public class SimpleEditor extends JFrame {
    private String root = System.getProperty("user.dir");
    private String selectedFile;
    private JScrollPane catalogDirectory;
    private JTextArea area;
    private JScrollPane areaScroll;
    private String compareContent = null;
    
    SimpleEditor() {
        initMenu();
        initMenuBar();
        initArea();
        initCatalogDirectory();
        splitPane();
    }
    
    private void initMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50,50,640,480);
        setLayout(new BorderLayout());
        setTitle("Простой текстовый редактор");
    }

    private void initArea() {
        area = new JTextArea();
        areaScroll = new JScrollPane(area);
        areaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void initMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new OpenFileItem(root, this);
        SaveFileItem save = new SaveFileItem(root, this);
        JMenuItem close = new CloseFileItem(root, this);
        file.add(open);
        file.add(save);
        file.add(close);
        bar.add(file);
        setJMenuBar(bar);
    }
    
        
    private void initCatalogDirectory() {    
        File startDir = new File(root);
        TreeWidget widget = new TreeWidget(startDir, this);
        catalogDirectory = widget.getTreeCatalogDirectory();       
    }
        
    private void splitPane() {   
        JSplitPane splitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(catalogDirectory);
        splitPane.setRightComponent(areaScroll);
        splitPane.setDividerLocation(250);
        add(splitPane);        
    }
    
    public void setSelectedFile(String fileName) {      
        selectedFile = fileName;
    }
    
    public String getSelectedFile() {
        return  selectedFile;
    }
    
    public void setCompareContent(String content) {
        compareContent = content;
    }
    
    public void showContentFile(String content) {
        area.setText("");
        compareContent = content;
        area.append(content);
    }
    
    public String getEditedText() {
       return  area.getText();
    }
    
    public boolean doChangesExist() {
        if(compareContent == null) return false;
        return !compareContent.equals(getEditedText());
    }
    
    public int getConfirm() {
        return  JOptionPane.showConfirmDialog(
                                      this, 
                                      "Вы хотите сохранить изменения?",
                                      "Окно подтверждения",
                                      JOptionPane.YES_NO_CANCEL_OPTION);
    }
    
    public void closeFile() {
        area.setText("");
        compareContent = null;
    }
}
