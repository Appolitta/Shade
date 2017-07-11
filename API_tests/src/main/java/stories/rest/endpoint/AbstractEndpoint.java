package stories.rest.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import stories.managers.GlobalObjectMappers;
import stories.rest.Rest;
import stories.rest.responsecheck.CheckUtils;
import stories.rest.responsecheck.ResponseCheck;
import stories.util.FormattingUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Base class for rest endpoints.
 */
public abstract class AbstractEndpoint {
    private final Rest rest;
    private final String pathToMethod;

    private static final ObjectMapper objectMapperWithNulls =
            GlobalObjectMappers.getObjectMapperWithNullFieldsPreserve();
    private static final ObjectMapper objectMapperWithoutNulls =
            GlobalObjectMappers.getObjectMapperWithoutNullFieldsPreserve();

    protected static final ObjectMapper responseMapper = GlobalObjectMappers.getObjectMapperWithoutNullFieldsPreserve();

    public AbstractEndpoint(Rest rest, String subPath) {
        this.rest = rest;
        this.pathToMethod = FormattingUtils.buildApiPath(rest.getBackendSettings()) + subPath;
    }

    protected Response get(final String method,
                           final Map<String, String> headers,
                           final List<ResponseCheck> responseChecks,
                           final String description)
            throws IOException {
        Response response = rest.get(pathToMethod + method, headers, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                null,
                headers,
                null,
                "GET " + pathToMethod + method,
                description);
        return response;
    }

    protected Response get(final String method,
                           final List<ResponseCheck> responseChecks,
                           final String description)
            throws IOException {
        Response response = rest.get(pathToMethod + method, Collections.emptyMap(), description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                null,
                null,
                null,
                "GET " + pathToMethod + method,
                description);
        return response;
    }

    protected Response post(final String method,
                            final Object requestPayload,
                            final boolean shouldPreserveNullFields,
                            final List<ResponseCheck> responseChecks,
                            final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.post(pathToMethod + method, Collections.emptyMap(), requestPayloadAsString, description);
        String d = response.getBody().toString();
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                null,
                null,
                "POST " + pathToMethod + method,
                description);
        return response;
    }

    protected Response post(final String method,
                            final Map<String, String> headers,
                            final Object requestPayload,
                            final boolean shouldPreserveNullFields,
                            final List<ResponseCheck> responseChecks,
                            final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.post(pathToMethod + method, headers, requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                headers,
                null,
                "POST " + pathToMethod + method,
                description);
        return response;
    }

    protected Response post(final String method,
                            final String filename,
                            final Map<String, ?> headers,
                            final List<ResponseCheck> responseChecks,
                            final String description)
            throws IOException {
        Response response = rest.post(pathToMethod + method, filename, headers, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                null,
                null,
                null,
                "POST " + pathToMethod + method,
                description);
        return response;
    }

    protected Response patch(final String method,
                             final Object requestPayload,
                             final boolean shouldPreserveNullFields,
                             final List<ResponseCheck> responseChecks,
                             final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.patch(pathToMethod + method, Collections.emptyMap(), requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                null,
                null,
                "PATCH " + pathToMethod + method,
                description);
        return response;
    }

    protected Response delete(final String method,
                              final Map<String, String> headers,
                              final Object requestPayload,
                              final boolean shouldPreserveNullFields,
                              final List<ResponseCheck> responseChecks,
                              final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.delete(pathToMethod + method, headers, requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                headers,
                null,
                "DELETE " + pathToMethod + method,
                description);
        return response;
    }

    protected Response delete(final String method,
                              final Object requestPayload,
                              final boolean shouldPreserveNullFields,
                              final List<ResponseCheck> responseChecks,
                              final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.delete(pathToMethod + method, Collections.emptyMap(), requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                null,
                null,
                "DELETE " + pathToMethod + method,
                description);
        return response;
    }

    protected Response put(final String method,
                           final Object requestPayload,
                           final boolean shouldPreserveNullFields,
                           final List<ResponseCheck> responseChecks,
                           final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.put(pathToMethod + method, Collections.emptyMap(), requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                null,
                null,
                "PUT " + pathToMethod + method,
                description);
        return response;
    }

    protected Response put(final String method,
                           final Map<String, String> headers,
                           final Object requestPayload,
                           final boolean shouldPreserveNullFields,
                           final List<ResponseCheck> responseChecks,
                           final String description)
            throws IOException {
        final String requestPayloadAsString = objectToString(requestPayload, shouldPreserveNullFields);
        Response response = rest.put(pathToMethod + method, headers, requestPayloadAsString, description);
        CheckUtils.assertResponse(
                response,
                responseChecks,
                requestPayloadAsString,
                headers,
                null,
                "PUT " + pathToMethod + method,
                description);
        return response;
    }

    protected final Rest getRest() {
        return rest;
    }

    private String objectToString(Object object, boolean shouldPreserveNullFields)
            throws IOException {
        final ObjectMapper selectedObjectMapper =
                (shouldPreserveNullFields) ? objectMapperWithNulls : objectMapperWithoutNulls;

        return object != null
                ? (object instanceof String ? (String) object : selectedObjectMapper.writeValueAsString(object))
                : null;
    }
}
