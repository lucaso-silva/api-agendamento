package com.lucas.agendamento.infra.adapter.inbound.rest;

import com.lucas.agendamento.core.domain.exception.DomainException;
import com.lucas.agendamento.core.exception.BusinessException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    protected ResponseEntity<ProblemDetail> handleDomainException(DomainException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(ex.getHttpStatus()), ex.getMessage()
        );
        problemDetail.setTitle("Operação inválida");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return ResponseEntity.status(ex.getHttpStatus()).body(problemDetail);
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(ex.getHttpStatus()), ex.getMessage()
        );
        problemDetail.setTitle("Regra de negócio violada");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("code", ex.getCode());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return ResponseEntity.status(ex.getHttpStatus()).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> {
                    Map<String, String> param = new HashMap<>();
                    param.put("name", e.getField());
                    param.put("reason", e.getDefaultMessage());

                    return  param;
                })
                .collect(toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Requisição invalida"
        );
        problemDetail.setTitle("Requisição invalida");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("invalid-params", invalidParams);
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
