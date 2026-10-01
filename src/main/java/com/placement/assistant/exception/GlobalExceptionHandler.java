package com.placement.assistant.exception;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(404).body(Map.of("error",e.getMessage(),"status",404));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException e){
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->errors.put(((FieldError)err).getField(),err.getDefaultMessage()));
        return ResponseEntity.status(400).body(Map.of("errors",errors,"status",400));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleIllegal(IllegalArgumentException e){
        return ResponseEntity.status(400).body(Map.of("error",e.getMessage(),"status",400));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneral(Exception e){
        return ResponseEntity.status(500).body(Map.of("error","Internal server error: "+e.getMessage(),"status",500));
    }
}
