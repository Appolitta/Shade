package stories.test;

import com.jayway.restassured.response.Response;
import stories.managers.GlobalObjectMappers;
import stories.model.shademodel.core.workflow.PackageFlowModel;
import stories.rest.APIFacade;
import stories.rest.responsecheck.ResponseCheck;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Util methods for test data clean up. It is just a set of wrappers for backend APIs.
 */
public class TestCleanUpUtils {
    private TestCleanUpUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    public static Response deleteDocumentPackage(final PackageFlowModel documentPackage,
                                                 final List<ResponseCheck> responseChecks,
                                                 final APIFacade APIFacade)
            throws IOException {
        if (Objects.nonNull(documentPackage)) {
            return deleteDocumentPackage(documentPackage.getId(), responseChecks, APIFacade);
        }

        return null;
    }

    public static Response deleteDocumentPackage(final Response documentPackageResponse,
                                                 final List<ResponseCheck> responseChecks,
                                                 final APIFacade APIFacade)
            throws IOException {
        if (Objects.nonNull(documentPackageResponse)) {
            PackageFlowModel documentPackage =
                    GlobalObjectMappers
                            .getObjectMapperWithNullFieldsPreserve()
                            .readValue(documentPackageResponse.asString(), PackageFlowModel.class);
            return deleteDocumentPackage(documentPackage.getId(), responseChecks, APIFacade);
        }

        return null;
    }

    public static Response deleteDocumentPackage(final Integer documentPackageId,
                                                 final List<ResponseCheck> responseChecks,
                                                 final APIFacade APIFacade)
            throws IOException {
        if (Objects.nonNull(documentPackageId)) {
            return APIFacade.getPackageEndpoint().deletePackage(
                    documentPackageId,
                    responseChecks,
                    "Deleting document package with id: " + documentPackageId + ".");
        }

        return null;
    }
}
