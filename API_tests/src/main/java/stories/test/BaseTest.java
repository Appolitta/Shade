package stories.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.testng.ITestContext;
import org.testng.annotations.*;
import stories.test.listener.TestLifecycleListener;
import testRail.TestRailCaseId;
import testRail.TestRailRunId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Base class for any types of tests.
 * It is getting and keeping meta information about test.
 */


@Listeners(TestLifecycleListener.class)

abstract class BaseTest {
    @TestRailRunId
    protected int runId = 0;

    @TestRailCaseId
    protected int caseId = 0;

    protected ResourceBundle rbTest = ResourceBundle.getBundle("test");
    protected ResourceBundle rbTestRail = ResourceBundle.getBundle("testrail");
    protected ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);
 //   private NullComparator comparator = new NullComparator(true);

    protected int testCaseId;
    protected String testDescription;


    @Parameters({"runId"})
    @BeforeTest
    public void beforeTest(@Optional String runId)
            throws IOException, SQLException, InterruptedException {
        String testrailRunId = rbTestRail.getString("TestRail_RUN_ID");
        // if TestRail run_id is set in properties, use it. If not, use run_id from xml file
        if (testrailRunId != null) {
            this.runId = Integer.parseInt(testrailRunId);
        } else {
            if (runId != null) this.runId = Integer.parseInt(runId);
           // if (runId != 0) this.runId = runId;
        }

    }

    @BeforeClass
    public void setTestDescription( ITestContext context) {
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

    @BeforeMethod
    public void resetCaseId() {
        caseId = 0;
    }



}

