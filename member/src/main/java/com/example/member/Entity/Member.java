package com.example.member.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;

    @NotNull
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @NotNull
    @NotBlank(message = "이름을 입력해 주세요.")
    private String username;

    //Board 엔티티와 일대다 연관관계
    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();
}
