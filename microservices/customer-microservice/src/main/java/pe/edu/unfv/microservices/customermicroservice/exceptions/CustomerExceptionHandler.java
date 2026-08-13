package pe.edu.unfv.microservices.customermicroservice.exceptions;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.unfv.microservices.commonexceptions.ErrorResponse;
import pe.edu.unfv.microservices.commonexceptions.GlobalExceptionHandler;

import java.util.HashMap;

@RestControllerAdvice(basePackages = "pe.edu.unfv.microservices.customer-microservice")
@Primary
public class CustomerExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(CustomerNotFoundException exception){
        var errors = new HashMap<String,String>();
        var fieldName = "customer";
        errors.put(fieldName, exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }
}
