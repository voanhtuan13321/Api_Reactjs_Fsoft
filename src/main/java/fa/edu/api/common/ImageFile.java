package fa.edu.api.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

/**
 * Handle image file class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
public class ImageFile {
  public static final String PATH_IMAGE = "src\\main\\resources\\static\\image\\";

  /**
   * Save the image.
   *
   * @param imageFile need to save
   * @return file name
   */
  public static String saveImageFile(MultipartFile imageFile) {
    if (imageFile.isEmpty()) {
      return "";
    }

    Path path = Paths.get(PATH_IMAGE);

    try {
      InputStream inputStream = imageFile.getInputStream();
      String extension = getFileExtension((imageFile));
      String filename = randomFileName(extension);
      Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
      return filename;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * Random file name.
   *
   * @param extension of the file
   * @return file name
   */
  private static String randomFileName(String extension) {
    String randomUUID = UUID.randomUUID().toString();
    long timeStamp = new Date().getTime();
    return randomUUID + timeStamp + "." + extension;
  }

  /**
   * Get file extension.
   *
   * @param file need to get the file extension
   * @return file name extension
   */
  private static String getFileExtension(MultipartFile file) {
    String originalFileName = file.getOriginalFilename();

    return (originalFileName != null && originalFileName.contains("."))
        ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
        : "";
  }

  /**
   * Delete file.
   *
   * @param imageName need to delete
   * @return status of delete
   */
  public static boolean deleteImageFile(String imageName) {
    File file = new File(PATH_IMAGE + imageName);
    if (file.exists()) {
      return file.delete();
    }
    return false;
  }
}
