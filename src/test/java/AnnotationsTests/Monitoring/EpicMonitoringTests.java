package AnnotationsTests.Monitoring;

import io.qameta.allure.Epic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Epic(value="Мониторинг")
public @interface EpicMonitoringTests {
}
