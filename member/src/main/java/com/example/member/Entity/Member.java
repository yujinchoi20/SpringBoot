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

    private String userId;
    private String password;
    private String username;

    //Board 엔티티와 일대다 연관관계
    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    /*
        연관관계 편의 메서드 -> 이 설정이 없으면 Board 쪽에서 작성자가 누구인지 알 수 없음.
     */
    public void addBoard(Board board) {
        board.setMember(this);
        this.boardList.add(board);
    }
}
