package AnnotationsTests.Monitoring;

import io.qameta.allure.Feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Feature(value = "Статус серверов")
public @interface FeatureStatusServers {
}
