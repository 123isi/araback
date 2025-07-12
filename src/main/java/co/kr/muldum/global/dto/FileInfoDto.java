package co.kr.muldum.global.dto;

public record FileInfoDto(
        Long fileId, String fileName, String filePath, String fileType, Integer fileSize
) {}
