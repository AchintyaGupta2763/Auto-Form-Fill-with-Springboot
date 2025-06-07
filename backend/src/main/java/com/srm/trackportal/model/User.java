package com.srm.trackportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Note: You have both jakarta.persistence.* AND jakarta.persistence.Entity & Id below;
// you should consistently use one JPA package. Since Spring Boot 3+ uses Jakarta EE,
// switch both to `jakarta.persistence.*` (remove `jakarta.*` imports).

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email; // SRM mail ID

    @NotBlank
    @Size(min = 6, max = 100)
    private String password; // stored hashed

    @Enumerated(EnumType.STRING)
    private Track track;

    public User() {}

    // Constructors should accept a Track enum, not a String
    public User(String name, String registrationNumber, String email, String password, Track track) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.password = password;
        this.track = track;
    }

    // ─── Getters & Setters ───

    public Long getId() {
        return id;
    }
    public void setId(Long id) {      // method name fixed (was setid)
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter signature was wrong (it took a String parameter). Fix:
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    // Method name corrected (was setPaassword)
    public void setPassword(String password) {
        this.password = password;
    }

    public Track getTrack() {
        return track;
    }
    public void setTrack(Track track) {
        this.track = track;
    }
}
