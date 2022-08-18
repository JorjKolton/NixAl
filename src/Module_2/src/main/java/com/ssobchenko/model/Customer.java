package com.ssobchenko.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Customer {
    private final String id;
    private final String email;
    private final int age;

    private Customer(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.email = builder.email;
        this.age = builder.age;
    }

    public static class Builder {
        private final int age;
        private String email = "customer@example.com";

        public Builder(int age) {
            this.age = age;
        }

        public Builder setEmail(String email) {
            if (email == null || !email.matches("\\w+@\\w+\\.\\w+")) {
                throw new IllegalArgumentException("Wrong email");
            }
            this.email = email;
            return this;
        }

        public Customer build() {
            if (age < 10) {
                throw new IllegalArgumentException("The age must be more than 10");
            }
            return new Customer(this);
        }
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id = '" + id + '\'' +
                ", email = '" + email + '\'' +
                ", age = " + age +
                '}';
    }
}