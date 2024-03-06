package refactored.controllers;

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
import refactored.model.PostDBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.UploadUI;

public class UploadController implements Controller {

    private static int currentID;
    private static boolean imageUploaded;
    private static UploadUI uploadUI;

    public static void openUploadUI() {
        uploadUI = new UploadUI();
        imageUploaded = false;
        uploadUI.setVisible(true);
    }
    
    public static void uploadAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(filter);
    
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                //get user info
                currentID = UserDBManager.currentID;
                String username = UserDBManager.getUsername(currentID);
                //create image info
                int imageId = PostDBManager.getnerateContentID();
                String fileExtension = getFileExtension(selectedFile);
                String newFileName = username + "_" + imageId + "." + fileExtension;
                //copy image
                Path destPath = Path.of(Paths.uploadsPath.toString(), newFileName);
                Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
    
                //save to posts database
                PostDBManager.createPost(currentID, newFileName, uploadUI.getCaption());

                // Load the image from the saved path
                ImageIcon imageIcon = new ImageIcon(destPath.toString());
                uploadUI.updateImagePreview(imageIcon);
    
                // Update the flag to indicate that an image has been uploaded
                imageUploaded = true;
    
                // Change the text of the upload button
                uploadUI.updateUploadButtonText(imageUploaded);
    
                JOptionPane.showMessageDialog(uploadUI, "Image uploaded and preview updated!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(uploadUI, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf + 1);
    }
}
