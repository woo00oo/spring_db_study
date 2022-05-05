package com.example.springdbstudy.templatecallback;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TimeLogTemplateTest {

    @DisplayName("템플릿 콜백 패턴 - 익명 내부 클래스")
    @Test
    void callBackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    @DisplayName("템플릿 콜백 패턴 - 람다")
    @Test
    void callBackV2() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직1 실행"));

        template.execute(() -> log.info("비즈니스 로직2 실행"));
    }

}