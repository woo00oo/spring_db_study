package com.example.springdbstudy.service;

import com.example.springdbstudy.domain.Member;
import com.example.springdbstudy.repository.MemberRepositoryV1;
import com.example.springdbstudy.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/*
    트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();

        try {
            con.setAutoCommit(false); // 트랜잭션 시작
            // 비즈니스 로직
            bizLogic(con, fromId, toId, money);
            con.commit(); //성공시 커밋
        } catch (Exception e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }

    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        // 시작
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

    private void release(Connection con) {
        if (con != null) {
          try {
              con.setAutoCommit(true); //커넥션 풀 고려
              con.close();
          } catch (Exception e) {
              log.error("error message", e);
          }
        }
    }
}

/*
    - 트랜잭션을 사용하기 위해 JDBC 기술에 의존하고 있음.
    - 결과적으로 비즈니스 로직보다 JDBC를 사용해서 트랜잭션을 처리하는 코드가 더 많다.
    - 향후 JDBC에서 JPA 같은 다른 기술로 바꾸어 사용하게 되면 서비스 코드도 모두 함께 변경해야 한다.(JPA는 트랜잭션을 사용하는 코드가 JDBC와 다르다)
    - 핵심 비즈니스 로직과 JDBC 기술이 섞여 있어서 유지보수 하기 어렵다.


   문제 정리
   - 트랜잭션 문제
    1. JDBC 구현 기술이 서비스 계층에 누수되는 문제
    2. 트랜잭션 동기화 문제
    3. 트랜잭션 적용 반복 문제
   - 예외 누수 문제
   - JDBC 반복 문제
 */