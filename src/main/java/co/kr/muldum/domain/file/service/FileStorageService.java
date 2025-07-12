package co.kr.muldum.domain.file.service;

import co.kr.muldum.domain.file.model.StoredFile;
import co.kr.muldum.domain.file.repository.FileRepository;
import co.kr.muldum.global.config.FilePathConfig;
import co.kr.muldum.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStorageService {
  private final FilePathConfig filePathConfig;
  private final FileRepository fileRepository;

  public String upload(MultipartFile multipartFile, String type) {
    String uploadDir;

    if ("BANNER".equalsIgnoreCase(type)) {
      uploadDir = filePathConfig.getUploadDir() + "/banner";
    } else {
      uploadDir = filePathConfig.getUploadDir() + "/notice";
    }

    String savedFileName = FileUtil.saveFile(multipartFile, uploadDir);
    String webPath = "/uploads/" + type.toLowerCase() + "/" + savedFileName;

    StoredFile storedFile = StoredFile.builder()
            .path(webPath)
            .name(multipartFile.getOriginalFilename())
            .type(multipartFile.getContentType())
            .sizeBytes((int) multipartFile.getSize())
            .ownerUserId(1001L) // 임시로 1L 넣기
            .build();

    fileRepository.save(storedFile);
    return webPath;
  }
}
