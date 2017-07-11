package stories.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Test retrier.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);

    private Integer retryCount = 0;
    private Integer retryMaxCount = 3;

    @Override
    public boolean retry(ITestResult testResult) {
        boolean result = false;
        String stackTrace = testResult.getThrowable().fillInStackTrace().toString();
        if (!testResult.isSuccess() && !stackTrace.contains("java.lang")) {
            log.info("retry count = " + retryCount + "\n max retry count = " + retryMaxCount);

            if (retryCount < retryMaxCount) {
                log.info(
                        "Retrying" + testResult.getName() +
                                " with status " + testResult.getStatus() +
                                " for the try " + (retryCount + 1) + " of " + retryMaxCount +
                                " max times, with stacktrace " + stackTrace,
                        true);
                retryCount++;
                result = true;
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}

