package Pages.Providers;

public interface IProvidersPage {


    default boolean isCheckProviderPage() { return false; }
    default boolean isCheckProvider(String provider){ return false; }
    void clickButtonEditProvider(String provider);
    void clickButtonAddProvider();
    default boolean isCheckFormEditProvider(){ return false; }
    void clickSelectTypeProvider(String type);
    default boolean isNotVisibleLabel(String label){ return false; }
    void clickButtonAddProviders();
    default boolean clickSaveProvider(String provider){ return false; }
    default boolean isConfigurationServerBooster(){ return false; }
    default Object getInstanceProvider(String provider){ return null; }
}
