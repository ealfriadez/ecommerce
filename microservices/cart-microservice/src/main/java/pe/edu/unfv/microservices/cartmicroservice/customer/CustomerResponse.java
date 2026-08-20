package pe.edu.unfv.microservices.cartmicroservice.customer;

import lombok.Builder;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address,
        String city
) {
}
