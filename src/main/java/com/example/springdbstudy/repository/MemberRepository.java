package com.example.springdbstudy.repository;

import com.example.springdbstudy.domain.Member;

import java.sql.SQLException;

public interface MemberRepository {
    Member save(Member member); //체크 예외 -> 인터페이스가 JDBC 기술에 종속적이다
    Member findById(String memberId);
    void update(String memberId, int money);
    void delete(String memberId);
}
