package com.retailx.client.dto;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientDTO {
    private Long id;

    @NotBlank @Size(max=120)
    private String name;

    @Email @NotBlank @Size(max=120)
    private String email;

    @NotBlank @Size(max=30)
    private String document;

    public ClientDTO() {}
    public ClientDTO(Long id, String name, String email, String document) {
        this.id = id; this.name = name; this.email = email; this.document = document;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDocument() {
        return document;
    }

}
