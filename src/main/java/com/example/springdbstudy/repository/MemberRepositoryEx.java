package com.example.springdbstudy.repository;

import com.example.springdbstudy.domain.Member;

import java.sql.SQLException;

public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException; //체크 예외 -> 인터페이스가 JDBC 기술에 종속적이다
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}

/**
 * 런타임 예외와 인터페이스
 * 인터페이스에 런타임 예외를 따로 선언하지 않아도 된다. 따라서 인터페이스가 특정 기술에 종속적일 필요가 없다.
 */