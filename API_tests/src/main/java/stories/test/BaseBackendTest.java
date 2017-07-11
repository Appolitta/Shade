package stories.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base class for backend tests.
 */
public abstract class BaseBackendTest extends BaseTest {
    protected static final ObjectMapper mapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .enable(SerializationFeature.INDENT_OUTPUT);

    @BeforeClass
    public void setupBaseBackendTest() {
    }

    @AfterClass
    public void shutdownBaseBackendTest() {
    }

}
