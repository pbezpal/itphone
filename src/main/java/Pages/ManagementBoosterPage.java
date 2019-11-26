package Pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ManagementBoosterPage {

    public static ManagementBoosterPage managementBoosterPage = new ManagementBoosterPage();
    public static ManagementBoosterPage getInstance() { return managementBoosterPage; }

    @Step(value = "Проверяем, что мы находимся на странице 'Управление кластерами'")
    public static boolean isManagementBoosterPage(){
        return $("table#list_clusters.table_standar").isDisplayed();
    }
}
