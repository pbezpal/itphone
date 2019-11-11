package AnnotationsTests.LoginTests;

import io.qameta.allure.Feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Feature(value = "Тестирование авторизации на сервере")
public @interface FeatureLoginTests {
}
