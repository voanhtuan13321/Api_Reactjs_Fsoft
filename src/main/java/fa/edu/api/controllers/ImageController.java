package fa.edu.api.controllers;

import fa.edu.api.common.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Image controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 02/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/images")
@Slf4j
public class ImageController {

  /**
   * Api call image.
   *
   * @param imageName want to get
   * @return ByteArrayResource
   */
  @GetMapping(path = "/{imageName}")
  public ResponseEntity<ByteArrayResource> getImage(@PathVariable("imageName") String imageName) {
    if (imageName != null) {
      Path fileName = Paths.get(ImageFile.PATH_IMAGE, imageName);
      try {
        byte[] buffer = Files.readAllBytes(fileName);
        ByteArrayResource bytes = new ByteArrayResource(buffer);
        return ResponseEntity.ok()
            .contentLength(buffer.length)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
      } catch (IOException e) {
        if ("undefined".equals(imageName)) {
          log.error("Read file error", e);
        }
      }
    }
    return ResponseEntity.badRequest().build();
  }
}
