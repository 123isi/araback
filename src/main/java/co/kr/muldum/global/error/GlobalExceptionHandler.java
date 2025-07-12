package co.kr.muldum.global.error;

import co.kr.muldum.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientInvalidException.class)
    public ResponseEntity<ErrorResponse> handleClientInvalid(ClientInvalidException ex) {
        Map<String, String> fields = new HashMap<>();
        fields.put("client", "클라이언트 정보가 잘못되었습니다");
        ErrorResponse response = new ErrorResponse(400, ex.getMessage(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCodeNotFound(CodeNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
