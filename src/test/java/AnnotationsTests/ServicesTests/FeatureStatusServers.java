package AnnotationsTests.ServicesTests;

import io.qameta.allure.Feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Feature(value = "Тестирование раздела Мониторинг")
public @interface FeatureStatusServers {
}
