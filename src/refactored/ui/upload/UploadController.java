package refactored.ui.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import refactored.factories.Paths;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class UploadController implements IPageController
{
    private final UIManager manager;
    private final UploadPage page;
    
    private final int userId;
    private final String username;

    public UploadController(UIManager manager, int userId, String username)
    {
        this.manager = manager;
        this.page = new UploadPage(() -> handleUpload(), pageType -> manager.navigateToPage(pageType));

        this.userId = userId;
        this.username = username;
    }
    
    @Override
    public void open()
    {
        page.setVisible(true);
    }

    @Override
    public void close()
    {
        page.dispose();
    }

    private void handleUpload()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
    
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            try
            {
                //create image info
                int imageId = manager.createContentId();
                String fileExtension = getFileExtension(selectedFile);
                String newFileName = username + "_" + imageId + "." + fileExtension;
                //copy image
                Path destPath = Path.of(Paths.uploadsPath.toString(), newFileName);
                Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
    
                //save to posts database
                manager.savePost(userId, newFileName, page.getCaption());

                // Load the image from the saved path
                ImageIcon imageIcon = new ImageIcon(destPath.toString());
                page.updateImagePreview(imageIcon);
    
                // Change the text of the upload button
                page.updateUploadButtonText(true);
    
                JOptionPane.showMessageDialog(page, "Image uploaded and preview updated!");
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(page, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String getFileExtension(File file)
    {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }
}