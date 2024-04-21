package com.stockportfolio.stockservice.Models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pending_users")
public class PendingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pan_number", unique = true)
    private String panNumber;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "pan_verified", nullable = false)
    private boolean pan_verified;

    @Column(name = "phone_number_verified", nullable = false)
    private boolean phone_number_verified;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "roles")
    private String roles;

    public static class PendingUserBuilder {

        private Long id;
        private String username;
        private String email;
        private String panNumber;
        private String phoneNumber;
        private boolean panVerified;
        private boolean phoneNumberVerified;
        private Date createdAt;
        private String roles;

        public PendingUserBuilder() {
        }

        // Setters for optional parameters
        public PendingUserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PendingUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public PendingUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PendingUserBuilder panNumber(String panNumber) {
            this.panNumber = panNumber;
            return this;
        }

        public PendingUserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PendingUserBuilder panVerified(boolean panVerified) {
            this.panVerified = panVerified;
            return this;
        }

        public PendingUserBuilder phoneNumberVerified(boolean phoneNumberVerified) {
            this.phoneNumberVerified = phoneNumberVerified;
            return this;
        }

        public PendingUserBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PendingUserBuilder roles(String roles) {
            this.roles = roles;
            return this;
        }

        // Build method to create PendingUser object
        public PendingUser build() {
            PendingUser pendingUser = new PendingUser();
            pendingUser.setEmail(email);
            pendingUser.setUsername(username);
            pendingUser.setPanNumber(panNumber);
            pendingUser.setPhoneNumber(phoneNumber);
            pendingUser.setPan_verified(panVerified);
            pendingUser.setPhone_number_verified(panVerified);
            pendingUser.setRoles(roles);
            pendingUser.setCreatedAt(new Date());
            return pendingUser;
        }
    }
}
