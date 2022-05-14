package com.example.springdbstudy.exception.basic;

import com.example.springdbstudy.exception.basic.UncheckedAppTest.Controller;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StackTraceTest {

    /**
     * 예외를 전환할 때는 꼭! 기존 예외를 포함하자
     */

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e);
        }
    }
}
