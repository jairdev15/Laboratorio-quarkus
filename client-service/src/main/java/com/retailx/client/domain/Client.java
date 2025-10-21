package com.retailx.client.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Client extends PanacheEntity {

    @NotBlank
    @Size(max = 120)
    private String name;

    @Email
    @NotBlank
    @Size(max = 120)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String document;

    public Client() {}

    public Client(String name, String email, String document) {
        this.name = name;
        this.email = email;
        this.document = document;
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

    public Long getId() {
        return id;
    }
}
