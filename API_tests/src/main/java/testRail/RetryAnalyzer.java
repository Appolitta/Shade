package testRail;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Created by Admin on 21.01.2016.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private Integer retryCount = 0;
    private Integer retryMaxCount = 5;

    @Override
    public boolean retry(ITestResult testResult) {
        boolean result = false;
        String stackTrace=testResult.getThrowable().fillInStackTrace().toString();
        if (!testResult.isSuccess()&& (stackTrace.contains("java.net")||stackTrace.contains("Broken pipe") )) {
            System.out.println("retry count = " + retryCount + "\n max retry count = " + retryMaxCount);
            if (retryCount < retryMaxCount) {
                System.out.println("Retrying" + testResult.getName() + " with status " + testResult.getStatus() + " for the try " + (retryCount + 1) + " of " + retryMaxCount + " max times, with stacktrace "+stackTrace);
                retryCount++;
                result = true;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getResultStatusName(int status) {
        String resultName = null;
        if(status==1)
            resultName = "SUCCESS";
        if(status==2)
            resultName = "FAILURE";
        if(status==3)
            resultName = "SKIP";
        return resultName;
    }
}

