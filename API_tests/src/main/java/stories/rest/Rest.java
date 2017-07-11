package stories.rest;

import com.jayway.restassured.response.Response;
import stories.model.custom.BackendSettings;

import java.io.IOException;
import java.util.Map;

/**
 * Interface that any REST requester should implement.
 */
public interface Rest {
    Response get(final String methodPath,
                 final Map<String, String> headers,
                 final String description);

    Response get(final String methodPath,
                 final Map<String, String> headers,
                 final Map<String, ?> parameters,
                 final String description);

    Response post(final String methodPath,
                  final Map<String, String> headers,
                  final String requestPayload,
                  final String description)
            throws IOException;

    Response post(final String methodPath,
                  final Map<String, String> headers,
                  final Map<String, ?> parameters,
                  final String description)
            throws IOException;

    Response post(final String methodPath,
                  final String fileName,
                  final Map<String, ?> headers,
                  final String description)
            throws IOException;

    Response put(final String methodPath,
                 final Map<String, String> headers,
                 final String requestPayload,
                 final String description)
            throws IOException;

    Response patch(final String methodPath,
                   final Map<String, String> headers,
                   final String requestPayload,
                   final String description)
            throws IOException;

    Response delete(final String methodPath,
                    final Map<String, String> headers,
                    final String requestPayload,
                    final String description)
            throws IOException;

    BackendSettings getBackendSettings();
}