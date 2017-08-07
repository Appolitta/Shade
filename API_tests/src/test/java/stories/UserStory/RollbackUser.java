package stories.UserStory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stories.db.DBFacade;
import stories.managers.SettingsManager;
import stories.model.custom.BackendSettings;
import stories.model.shademodel.core.model.accountmodel.UserModel;
import stories.model.shademodel.core.model.accountmodel.UserModelResponse;
import stories.model.shademodel.core.model.usermodel.UserErrorResponse;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.test.BaseBackendTest;
import stories.util.SoftAssert;
import stories.util.ddto.DdtDataProvider;
import stories.util.ddto.DdtoSet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by weezlabs on 4/3/17.
 * Delete User before verification
 */




@Test(groups = {"user.rollback"})
public class RollbackUser extends BaseBackendTest {

    private static final String ROLLBACK_USER = "rollbackUser";
    private SettingsManager settingsManager;
    private BackendSettings backendSettings;
    private Integer IdDell = 0;

    private APIFacade accountAPIFacade;
    private DBFacade testDBFacade;

    @BeforeClass

    //The DataProvider for the positive test
    @DataProvider(name = "rollbackUser")
    private Object[][] GetUserDate(ITestContext context)
            throws IOException {
        settingsManager = SettingsManager.getSettingsManager();
        testDBFacade = new DBFacade( settingsManager.getDefaultBackendSettings());
        accountAPIFacade = new APIFacade(null, settingsManager.getDefaultBackendSettings());
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(
                context, ROLLBACK_USER,
                settingsManager.getDdtDataPath());
    }


    @Test(description = "Delete the user",
            dataProvider = "rollbackUser",
            groups = {"user.rollback"},/* dependsOnMethods = "CheckUser",*/ priority = 1)
    public void RollbackUserPositive(ITestContext context, final Map ddtSetMap)
            throws IOException, SQLException {
      //получение email из rollback.json
        Object[][] userData = GetUserDate(context);

        for (Object[] obj:userData) {
            LinkedHashMap<String, Object> tObj = (LinkedHashMap<String, Object>) obj[0];
            LinkedHashMap<String, String> dto = (LinkedHashMap<String, String>) tObj.get("dto");
            //str[3];
            String email = dto.get("email");
            Integer IdDell = testDBFacade.getIdUserByEmail(email);


            if (IdDell != 0) {
                String test_data = testDescription + "\nuser registration  twice test\n[ERROR] ";
                SoftAssert sa = new SoftAssert(test_data);
                UserErrorResponse error_response = null;

                accountAPIFacade.getAccountEndpoint().deleteUser(
                        IdDell,
                        Collections.singletonList(ResponseCheckFactory.getStatusCodeCheck(200)),
                        "Deleting user with id: " + IdDell + ".");

            }

        }
    }
}
