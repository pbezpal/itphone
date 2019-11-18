package HelperClasses;

import io.qameta.allure.Step;

public class StepReport {

    @Step(value = "{0}")
    public static void stepTest(String message){}

}
