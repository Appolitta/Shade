package stories.stories.api.documentpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import stories.managers.SettingsManager;
import stories.model.shademodel.core.Roles;
import stories.model.shademodel.core.workflow.SavePackageFlowModel;
import stories.util.TestDataGenerator;
import stories.db.DBFacade;
import stories.model.user.YourProjectUser;
import stories.model.shademodel.core.workflow.PackageFlowModel;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.test.TestCleanUpUtils;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * APIs verified by this tests:
 * POST   .../api/package - document package creation.
 */
@Test(groups = {"test-type.your-test-type"})
public class DocumentPackageTests extends BaseBackendTest {
    private static final Logger log = LoggerFactory.getLogger(DocumentPackageTests.class);

    private static final String CREATE_DOCUMENT_PACKAGE_POSITIVE_DDT_FILE_NAME = "createDocumentPackagePositiveDdtFile";

    private SettingsManager settingsManager;

    private DBFacade dbFacade;

    private YourProjectUser adminUser;
    private APIFacade adminAPIFacade;

    private YourProjectUser registratorUser;
    private APIFacade registratorAPIFacade;

    // ---------------------------------------------------------------------------------------------------------------//
    // Configuration.
    // ---------------------------------------------------------------------------------------------------------------//
    @BeforeClass(dependsOnMethods = "setupBaseBackendTest")
    public void setUp()
            throws IOException, SQLException {
        settingsManager = SettingsManager.getSettingsManager();

        adminUser = new YourProjectUser(
                settingsManager.getDefaultSuperUserLogin(),
                settingsManager.getDefaultSuperUserPassword(),
                true,
                settingsManager.getDefaultBackendSettings());
        adminAPIFacade = new APIFacade(adminUser.getAuthenticationData(), adminUser.getBackendSettings());
        dbFacade = new DBFacade(settingsManager.getDefaultBackendSettings());

        registratorUser = TestDataGenerator.createYourProjectUserWithRole(
                TestDataGenerator.randomString("Username ", "", 128),
                "123456",
                Roles.ROLES_ADMIN,
                adminAPIFacade,
                dbFacade);
        registratorUser.authenticate(registratorUser.getLogin(), registratorUser.getPassword());
        registratorAPIFacade =
                new APIFacade(registratorUser.getAuthenticationData(), registratorUser.getBackendSettings());
    }

    @AfterClass(dependsOnMethods = "shutdownBaseBackendTest")
    public void tearDown()
            throws IOException, SQLException {
        final int registratorUserId = registratorUser.getAuthenticationData().getUserId();
/*        adminAPIFacade.getAccountEndpoint().deleteUser(
                registratorUserId,
                Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                "Deleting user with id: " + registratorUserId + ".");*/
        dbFacade.closeConnection();
    }

    // ---------------------------------------------------------------------------------------------------------------//
    // Data providers.
    // ---------------------------------------------------------------------------------------------------------------//
    // Example of data provider.
    @DataProvider(name = "CreateDocumentPackagePositiveProvider")
    private Object[][] createDocumentPackagePositiveProvider(ITestContext context)
            throws IOException {
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(
                context,
                CREATE_DOCUMENT_PACKAGE_POSITIVE_DDT_FILE_NAME,
                settingsManager.getDdtDataPath());
    }

    // ---------------------------------------------------------------------------------------------------------------//
    // Create positive.
    // ---------------------------------------------------------------------------------------------------------------//
    @Test(description = "Create valid document package. Test case id: yourtest id.",
            dataProvider = "CreateDocumentPackagePositiveProvider",
            groups = {"test-type.your-test-type-prefix.api, test-duration.short", "test-state.working"})
    public void createDocumentPackagePositive(final Map ddtSetMap)
            throws IOException, SQLException {
        testCaseId = 1;

        PackageFlowModel createdPackageResponse = null;
        try {
            DdtoSet<SavePackageFlowModel> ddtoSet =
                    mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<SavePackageFlowModel>>() {
                    });

            final SavePackageFlowModel createPackageRequest = ddtoSet.getDto();
            Response response =
                    registratorAPIFacade.getPackageEndpoint().postPackage(
                            createPackageRequest,
                            false,
                            Collections.singletonList(
                                    ResponseCheckFactory.getStatusCodeCheck(ddtoSet.getStatusCode())),
                            ddtoSet.getDescription());
            final ZonedDateTime documentPackageCreationDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            createdPackageResponse = mapper.readValue(response.asString(), PackageFlowModel.class);

            checkDocumentPackageResponse(createPackageRequest, createdPackageResponse, documentPackageCreationDateTime);
        } finally {
            TestCleanUpUtils.deleteDocumentPackage(createdPackageResponse, null, registratorAPIFacade);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------//
    // Create negative.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Read positive.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Read negative.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Update positive.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Update negative.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Delete positive.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Delete negative.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Bulk Delete positive.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Bulk Delete negative.
    // ---------------------------------------------------------------------------------------------------------------//

    // ---------------------------------------------------------------------------------------------------------------//
    // Utils.
    // ---------------------------------------------------------------------------------------------------------------//
    private void checkDocumentPackageResponse(final SavePackageFlowModel documentPackageRequest,
                                              final PackageFlowModel documentPackageResponse,
                                              final ZonedDateTime documentPackageCreationDateTime) {
    }
}
