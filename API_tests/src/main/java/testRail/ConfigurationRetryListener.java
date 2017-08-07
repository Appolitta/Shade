package testRail;

import org.testng.IConfigurationListener2;
import org.testng.ITestResult;

/**
 * Created by wizard on 7/6/16.
 */
public class ConfigurationRetryListener implements IConfigurationListener2 {
    private Integer retryCount = 0;
    private Integer retryMaxCount = 5;

    @Override
    public void onConfigurationSuccess(ITestResult tr) {
        System.out.println("on configuration success");
        retryCount=0;
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        System.out.println("on configuration failure");
    }

    @Override
    public void onConfigurationSkip(ITestResult tr) {
        System.out.println("on configuration skip");
    }

    @Override
    public void beforeConfiguration(ITestResult tr) {
        System.out.println("called before the configuration method is invoked");
    }
}
