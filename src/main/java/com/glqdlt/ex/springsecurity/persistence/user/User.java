package com.glqdlt.ex.springsecurity.persistence.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String name;
    private String id;
    private String password;
    private String role;
}
