package stories.test;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import stories.test.listener.TestLifecycleListener;
import org.testng.annotations.Listeners;

import java.util.Map;

/**
 * Base class for any types of tests.
 * It is getting and keeping meta information about test.
 */
@Listeners(TestLifecycleListener.class)
abstract class BaseTest {
    protected int testCaseId;
    protected String testDescription;
    @BeforeClass
    public void setTestDescription(ITestContext context) {
        String xmlFileName = context.getSuite().getXmlSuite().getFileName().replaceAll("(?i)(.*[\\\\/]?)([\\\\/])([a-z][a-z\\d_]+\\.xml)", "$3");
        StringBuffer suiteParamsStr = new StringBuffer("\n");
        if (context.getSuite().getXmlSuite().getParameters() != null) {
            suiteParamsStr.append("Suite parameters:\n");
            Map<String, String> params = context.getSuite().getXmlSuite().getParameters();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                suiteParamsStr.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
            }
        }
        StringBuffer testParamsStr = new StringBuffer("\n");
        if (context.getCurrentXmlTest().getTestParameters() != null) {
            testParamsStr.append("Test parameters:\n");
            Map<String, String> params = context.getCurrentXmlTest().getTestParameters();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                testParamsStr.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
            }
        }
        testDescription =
                "\n==================================================================" +
                        "\nSuite file name: " + xmlFileName +
                        "\nSuite: " + context.getSuite().getName() +
                        suiteParamsStr +
                        "\nTest:  " + context.getCurrentXmlTest().getName() +
                        testParamsStr.toString() +
                        "\n------------------------------------------------------------------\n";
        System.out.println(testDescription);

    }

}

