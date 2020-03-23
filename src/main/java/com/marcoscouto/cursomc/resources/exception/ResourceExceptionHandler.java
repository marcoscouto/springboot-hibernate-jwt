package com.marcoscouto.cursomc.resources.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.marcoscouto.cursomc.services.exceptions.AuthorizationException;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.FileException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
                                                        HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
                                                        HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> dataIntegrity(MethodArgumentNotValidException e,
                                                        HttpServletRequest request){

        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                System.currentTimeMillis());

        e.getBindingResult().getFieldErrors().forEach(
                x -> err.addError(x.getField(), x.getDefaultMessage())
        );

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(MethodArgumentNotValidException e,
                                                       HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e,
                                                       HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e,
                                                       HttpServletRequest request){

        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandardError err = new StandardError(code.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e,
                                                       HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e,
                                                  HttpServletRequest request){

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return ResponseEntity.status(err.getStatus()).body(err);

    }


}
