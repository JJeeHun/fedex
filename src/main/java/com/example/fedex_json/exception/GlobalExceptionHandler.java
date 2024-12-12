package com.example.fedex_json.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Object ex01(HttpServletResponse response, RuntimeException ex, HandlerMethod handlerMethod) throws IOException {
        log.error("Error: {}", ex.getMessage(), ex);

        boolean isRestController = handlerMethod.getBeanType().isAnnotationPresent(RestController.class)
            || handlerMethod.hasMethodAnnotation(ResponseBody.class);

        if( isRestController ) {
            return new ResponseEntity<>(new HashMap<>() {{
                put("message", ex.getMessage());
            }}, HttpStatus.BAD_REQUEST);
        }else {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<h1>");
            writer.println(ex.getMessage());
            writer.println("</h1>");
            writer.println("</body></html>");
        }

        return null;
    }
}
