package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private String ip;
    private String userAgent;
    private LocalDateTime visit;

    public User(String ip, String userAgent, LocalDateTime visit) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.visit = visit;
    }
}