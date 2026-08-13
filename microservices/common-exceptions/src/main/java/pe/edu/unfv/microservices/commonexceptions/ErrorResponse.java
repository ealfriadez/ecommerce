package pe.edu.unfv.microservices.commonexceptions;

import java.util.Map;

public record ErrorResponse(Map<String,String> error) {
}
