package part2SimpleEditor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Fedotov Anton
 */
public class ScanDirectory {

    static public class MyFile extends File {

        public MyFile(File file) {
            super(file.getPath());
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    static ArrayList< MyFile> scan_path_for_files(File path) {
        ArrayList< MyFile> result = new ArrayList<>();
        File[] files = path.listFiles((File pathname)
                -> pathname.isFile());
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    result.add(new MyFile(file));
                }
            }
        }
        return result;
    }

    public static DefaultMutableTreeNode walk_throw_directory(File start_dir,
            DefaultMutableTreeNode node) {
        File[] sub_paths = start_dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        if (sub_paths != null) {
            for (File path : sub_paths) {
                if (path.isDirectory()) {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(path.getName());
                    node.add(child);
                    child.setAllowsChildren(true);
                    walk_throw_directory(path, child);
                }
            }
        }

        for (File file_path : scan_path_for_files(start_dir)) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file_path);
            node.add(child);
            child.setAllowsChildren(false);
        }
        return node;
    }
}
