package stories.rest.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import stories.model.shademodel.core.form.FieldOptionModel;
import stories.model.shademodel.core.form.ValidationResult;
import stories.model.shademodel.core.workflow.PackageFlowModel;
import stories.model.shademodel.core.workflow.SavePackageFlowModel;
import stories.rest.responsecheck.ResponseCheck;
import stories.rest.responsecheck.ResponseCheckFactory;
import stories.rest.Rest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class PackageEndpoint extends AbstractEndpoint {
    public PackageEndpoint(Rest rest) {
        super(rest, "/package");
    }

    /**
     * Create or update document package.
     *
     * @param request                  containing document package information.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param description              description of your request.
     * @return response containing full information about created document package.
     * @throws IOException in case of network problems.
     */
    public PackageFlowModel postPackage(final SavePackageFlowModel request,
                                        final boolean shouldPreserveNullFields,
                                        final String description)
            throws IOException {
        Response response =
                postPackage(
                        request,
                        shouldPreserveNullFields,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), PackageFlowModel.class);
    }

    /**
     * Create or update document package.
     *
     * @param request                  containing document package information.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing full information about created document package.
     * @throws IOException in case of network problems.
     */
    public Response postPackage(final SavePackageFlowModel request,
                                final boolean shouldPreserveNullFields,
                                final List<ResponseCheck> responseChecks,
                                final String description)
            throws IOException {
        return post(
                "",
                request,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Create or update document package.
     *
     * @param request                  containing document package information.
     *                                 Building of request payload is up to caller.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing full information about created document package.
     * @throws IOException in case of network problems.
     */
    public Response postPackage(final String request,
                                final boolean shouldPreserveNullFields,
                                final List<ResponseCheck> responseChecks,
                                final String description)
            throws IOException {
        return post(
                "",
                request,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Create or update document package.
     *
     * @param packageId   id of document package you want to receive.
     * @param description description of your request.
     * @return response containing full information about created document package.
     * @throws IOException in case of network problems.
     */
    public PackageFlowModel getPackage(final int packageId,
                                       final String description)
            throws IOException {
        Response response =
                getPackage(
                        packageId,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), PackageFlowModel.class);
    }

    /**
     * Create or update document package.
     *
     * @param packageId      id of document package you want to receive.
     * @param responseChecks response checks.
     * @param description    description of your request.
     * @return raw response containing full information about created document package.
     * @throws IOException in case of network problems.
     */
    public Response getPackage(final int packageId,
                               final List<ResponseCheck> responseChecks,
                               final String description)
            throws IOException {
        return get(
                "/" + packageId,
                responseChecks,
                description);
    }

    /**
     * Delete specified document package.
     *
     * @param documentPackageId id of document package.
     * @param description       description of your request.
     * @return response containing operation result.
     * @throws IOException in case of network problems.
     */
    public ValidationResult deletePackage(final int documentPackageId, final String description)
            throws IOException {
        Response response =
                deletePackage(
                        documentPackageId,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), ValidationResult.class);
    }

    /**
     * Delete specified document package.
     *
     * @param documentPackageId id of document package.
     * @param responseChecks    response checks.
     * @param description       description of your request.
     * @return raw response containing operation result.
     * @throws IOException in case of network problems.
     */
    public Response deletePackage(final int documentPackageId,
                                  final List<ResponseCheck> responseChecks,
                                  final String description)
            throws IOException {
        return delete(
                "/" + documentPackageId,
                null,
                false,
                responseChecks,
                description);
    }

    /**
     * Delete specified document packages.
     *
     * @param documentPackagesIds      id of document packages.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param description              description of your request.
     * @return response containing operation result for each document.
     * @throws IOException in case of network problems.
     */
    public List<ValidationResult> bulkDeletePackages(final List<Integer> documentPackagesIds,
                                                     final boolean shouldPreserveNullFields,
                                                     final String description)
            throws IOException {
        Response response =
                bulkDeletePackages(
                        documentPackagesIds,
                        shouldPreserveNullFields,
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), new TypeReference<List<ValidationResult>>() {
        });
    }

    /**
     * Delete specified document packages.
     *
     * @param documentPackagesIds      ids of document packages.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing operation result for each document.
     * @throws IOException in case of network problems.
     */
    public Response bulkDeletePackages(final List<Integer> documentPackagesIds,
                                       final boolean shouldPreserveNullFields,
                                       final List<ResponseCheck> responseChecks,
                                       final String description)
            throws IOException {
        return post(
                "/delete",
                documentPackagesIds,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Delete specified document packages.
     *
     * @param documentPackagesIds      ids of document packages.
     *                                 Building of request payload is up to caller.
     * @param shouldPreserveNullFields should mapper preserve null fields in request payload.
     *                                 false if you want to remove fields with null values from request payload.
     * @param responseChecks           response checks.
     * @param description              description of your request.
     * @return raw response containing operation result for each document.
     * @throws IOException in case of network problems.
     */
    public Response bulkDeletePackages(final String documentPackagesIds,
                                       final boolean shouldPreserveNullFields,
                                       final List<ResponseCheck> responseChecks,
                                       final String description)
            throws IOException {
        return post(
                "/delete",
                documentPackagesIds,
                shouldPreserveNullFields,
                responseChecks,
                description);
    }

    /**
     * Get available document package source ids.
     *
     * @param description description of your request.
     * @return response containing available document package source ids.
     * @throws IOException in case of network problems.
     */
    public List<FieldOptionModel> getDocumentPackageSourceList(final String description)
            throws IOException {
        Response response =
                getDocumentPackageSourceList(
                        Collections.singletonList(
                                ResponseCheckFactory.getStatusCodeCheck(200)),
                        description);
        return responseMapper.readValue(response.asString(), new TypeReference<List<FieldOptionModel>>() {
        });
    }

    /**
     * Get available document package source ids.
     *
     * @param responseChecks response checks.
     * @param description    description of your request.
     * @return raw response containing available document package source ids.
     * @throws IOException in case of network problems.
     */
    public Response getDocumentPackageSourceList(final List<ResponseCheck> responseChecks, final String description)
            throws IOException {
        return get(
                "/source",
                responseChecks,
                description);
    }
}
