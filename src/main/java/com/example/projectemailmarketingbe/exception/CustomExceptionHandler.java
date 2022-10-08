package com.example.projectemailmarketingbe.exception;

import com.example.projectemailmarketingbe.dto.ExceptionRpDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> Exception(Exception ex, WebRequest request) {
        ResponseBodyDto<ExceptionRpDto> responseBodyDto = new ResponseBodyDto<ExceptionRpDto>();
        ExceptionRpDto exceptionResponse =
                new ExceptionRpDto( new Date(), ex.getMessage(), request.getDescription(false));
        responseBodyDto.setData(exceptionResponse);
        responseBodyDto.setStatusCode(500);
        return new ResponseEntity<>(responseBodyDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> handleBadRequestException(BadRequestException ex, WebRequest request) {

        return getResponseBodyDtoResponseEntity(request, ex.getMessage(), ex.getSTATUS(), ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> handleNotFoundException(NotFoundException ex, WebRequest request) {

        return getResponseBodyDtoResponseEntity(request, ex.getMessage(), ex.getSTATUS(), ex);
    }

    private ResponseEntity<ResponseBodyDto<ExceptionRpDto>> getResponseBodyDtoResponseEntity(WebRequest request, String message, HttpStatus status, RuntimeException ex) {
        ResponseBodyDto<ExceptionRpDto> responseBodyDto = new ResponseBodyDto<ExceptionRpDto>();
        ExceptionRpDto exceptionResponse =
                new ExceptionRpDto(new Date(), message, request.getDescription(false));
        responseBodyDto.setData(exceptionResponse);
        responseBodyDto.setStatusCode(status.value());
        return new ResponseEntity<>(responseBodyDto, status);
    }

    @ExceptionHandler({MalformedJwtException.class})
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> handleMalformedJwtException(MalformedJwtException ex, WebRequest request) {

        ResponseBodyDto<ExceptionRpDto> responseBodyDto = new ResponseBodyDto<ExceptionRpDto>();
        ExceptionRpDto exceptionResponse =
                new ExceptionRpDto(new Date(), ex.getMessage(), request.getDescription(false));
        responseBodyDto.setData(exceptionResponse);
        responseBodyDto.setStatusCode(400);
        return new ResponseEntity<>(responseBodyDto,HttpStatus.BAD_REQUEST);
    }

    ////    ExpiredException.class, UnsupportedJwtException.class, IllegalArgumentException.class,,UnsupportedJwtException.class
    @ExceptionHandler({ ExpiredException.class})
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> handleExpiredException(ExpiredException ex, WebRequest request) {

        ResponseBodyDto<ExceptionRpDto> responseBodyDto = new ResponseBodyDto<ExceptionRpDto>();
        ExceptionRpDto exceptionResponse =
                new ExceptionRpDto(new Date(), ex.getMessage(), request.getDescription(false));
        responseBodyDto.setData(exceptionResponse);
        responseBodyDto.setStatusCode(401);
        return new ResponseEntity<>(responseBodyDto,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ResponseBodyDto<ExceptionRpDto>> handleDMSRESTException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ExceptionRpDto exceptionResponse =
                new ExceptionRpDto( new Date(), String.join(",", errorList), request.getDescription(false));
        ResponseBodyDto<ExceptionRpDto> responseBodyDto = new ResponseBodyDto<ExceptionRpDto>();
        responseBodyDto.setData(exceptionResponse);
        responseBodyDto.setStatusCode(400);
        return new ResponseEntity<>(responseBodyDto, HttpStatus.BAD_REQUEST);
    }



}
