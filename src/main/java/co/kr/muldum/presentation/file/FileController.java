package co.kr.muldum.presentation.file;

import co.kr.muldum.domain.file.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ara/files")
public class FileController {
  private final FileStorageService fileService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(
          @RequestPart("files") MultipartFile file,
          @RequestParam(name = "type", defaultValue = "NOTICE") String type
  ) {
    String fileUrl = fileService.upload(file, type);

    return ResponseEntity.ok(Map.of("fileUrl", fileUrl));
  }
}
