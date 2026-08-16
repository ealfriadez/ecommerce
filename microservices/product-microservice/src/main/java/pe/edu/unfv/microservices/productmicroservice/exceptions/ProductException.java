package pe.edu.unfv.microservices.productmicroservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductException extends RuntimeException {

    private final String message;
}
