package co.kr.muldum.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class FileUtil {
  public static String saveFile(MultipartFile file, String uploadDir) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("파일이 첨부되지 않았습니다.");
    }

    try {
      File dir = new File(uploadDir);
      if (!dir.exists()) {
        dir.mkdirs();
      }

      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String savedFileName = UUID.randomUUID() + extension;

      Path path = Paths.get(uploadDir + File.separator + savedFileName);
      Files.copy(file.getInputStream(), path);

      return savedFileName;
    } catch (IOException e) {
      log.error("파일 저장 중 오류 발생", e);
      throw new RuntimeException("파일 저장 중 오류가 발생했습니다.");
    }
  }

  public static void deleteFile(String fileName, String uploadDir) {
    Path path = Paths.get(uploadDir + File.separator + fileName);
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      log.error("파일 삭제 실패: " + fileName, e);
      throw new RuntimeException("파일 삭제 중 오류가 발생했습니다.");
    }
  }

  public static boolean tryDeleteFile(String fileName, String uploadDir) {
    Path path = Paths.get(uploadDir + File.separator + fileName);
    try {
      return Files.deleteIfExists(path);
    } catch (IOException e) {
      log.error("파일 삭제 실패: {}", fileName, e);
      return false;
    }
  }
}
