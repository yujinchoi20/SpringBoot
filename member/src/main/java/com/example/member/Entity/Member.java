package com.example.member.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String username;
}
