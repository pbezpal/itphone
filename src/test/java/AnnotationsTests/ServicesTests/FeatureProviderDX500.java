package AnnotationsTests.ServicesTests;

import io.qameta.allure.Feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Feature(value = "Провайдер DX500")
public @interface FeatureProviderDX500 {
}
