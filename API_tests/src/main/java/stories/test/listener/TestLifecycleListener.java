package stories.test.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TimeZone;

/**
 * Listener to log test lifecycle information.
 */
public class TestLifecycleListener extends TestListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(TestLifecycleListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info(
                "Test started at: {}" +
                        "\nTest class: {}" +
                        "\nTest name: {}\n",
                Instant
                        .ofEpochMilli(result.getStartMillis())
                        .atZone(TimeZone.getDefault().toZoneId())
                        .toString(),
                result.getTestClass().getName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        onTestResult(result, "Success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        onTestResult(result, "Failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        onTestResult(result, "Skipped");
    }

    private void onTestResult(ITestResult result, String resultDescription) {
        final ZonedDateTime startTime =
                Instant
                        .ofEpochMilli(result.getStartMillis())
                        .atZone(TimeZone.getDefault().toZoneId());
        final ZonedDateTime endTime =
                Instant
                        .ofEpochMilli(result.getEndMillis())
                        .atZone(TimeZone.getDefault().toZoneId());
        final long testDuration = ChronoUnit.SECONDS.between(startTime, endTime);

        log.info(
                "Test finished at: {}" +
                        "\nTest class: {}" +
                        "\nTest name: {}" +
                        "\nTest result: {}" +
                        "\nTest duration: {} sec." +
                        "\n",
                endTime,
                result.getTestClass().getName(),
                result.getMethod().getMethodName(),
                resultDescription,
                testDuration);
    }

    @Override
    public void onStart(ITestContext context) {
        log.info(
                "\n==================================================================" +
                        "\nSuite started at: {}" +
                        "\nSuite file name: {}" +
                        "\nSuite: {}" +
                        "\n------------------------------------------------------------------\n",
                Instant
                        .from(context.getStartDate().toInstant())
                        .atZone(TimeZone.getDefault().toZoneId())
                        .toString(),
                getXmlFileName(context),
                context.getSuite().getName() + getSuiteParameters(context)
        );
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info(
                "\n==================================================================" +
                        "\nSuite finished at: {}" +
                        "\nSuite file name: {}" +
                        "\nSuite: {}" +
                        "\n------------------------------------------------------------------\n",
                Instant
                        .from(context.getEndDate().toInstant())
                        .atZone(TimeZone.getDefault().toZoneId())
                        .toString(),
                getXmlFileName(context),
                context.getSuite().getName() + getSuiteParameters(context)
        );
    }

    private String getXmlFileName(ITestContext context) {
        return context
                .getSuite()
                .getXmlSuite()
                .getFileName()
                .replaceAll("(?i)(.*[\\\\/]?)([\\\\/])([a-z][a-z\\d_]+\\.xml)", "$3");
    }

    private String getSuiteParameters(ITestContext context) {
        StringBuilder suiteParamsStr = new StringBuilder("\n");
        if (context.getSuite().getXmlSuite().getParameters() != null) {
            suiteParamsStr.append("Suite parameters:\n");
            Map<String, String> params = context.getSuite().getXmlSuite().getParameters();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                suiteParamsStr
                        .append("\t")
                        .append(entry.getKey())
                        .append(" : ")
                        .append(entry.getValue())
                        .append("\n");
            }
        }
        return suiteParamsStr.toString();
    }
}
