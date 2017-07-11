package stories.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.config.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import stories.model.user.authenticator.AuthenticationData;
import stories.model.custom.BackendSettings;
import stories.util.FormattingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Default REST requester.
 */
public class RestDefaultImpl implements Rest {
    private static final Logger log = LoggerFactory.getLogger(RestDefaultImpl.class);

    private final String GET_CONTENT_TYPE = "application/json;charset=UTF-8";
    private final String POST_CONTENT_TYPE = "application/json;charset=UTF-8";

    private Map<String, String> STANDARD_HEADERS = new HashMap<>();

    private RequestSpecification postSpecification;
    private RequestSpecification postSpecificationUnContent;
    private RequestSpecification postSpecificationUnloadContent;
    private RequestSpecification getSpecification;
    private ResponseSpecification responseSpecification;

    private boolean ssl;
    private String baseUri;
    private int port;
    private String basePath;

    private RequestSpecBuilder getRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestUnContentSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestUnloadContentSpecBuilder = new RequestSpecBuilder();
    private ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

    private BackendSettings backendSettings;
    private AuthenticationData authData;

    public RestDefaultImpl(BackendSettings backendSettings, AuthenticationData authData)
            throws IOException {
        this.backendSettings = backendSettings;

        basePath = getBackendSettings().getBaseApiPath();

        String sslStr = getBackendSettings().getServerSsl().toLowerCase();
        String sslDefaultStr = getBackendSettings().getServerSslDefault().toLowerCase();
        ssl = sslStr.equals("${ssl}")
                ? (sslDefaultStr.contains("true") || sslDefaultStr.contains("yes"))
                : (sslStr.contains("true") || sslStr.contains("yes"));

        String xHost = getBackendSettings().getServerUrl();
        baseUri = (ssl ? "https://" : "http://") + xHost;

        port = Integer.parseInt(getBackendSettings().getServerPort());

        RestAssured.config =
                RestAssuredConfig
                        .newConfig()
                        .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config =
                RestAssuredConfig.
                        newConfig()
                        .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));

        // Parameters for time out separately for POST and GET requests.
        HttpClientConfig getHttpConfig =
                RestAssuredConfig
                        .newConfig()
                        .getHttpClientConfig()
                        .setParam("http.connection.timeout", 25000)
                        .setParam("http.socket.timeout", 25000);
        HttpClientConfig postHttpConfig =
                RestAssuredConfig
                        .newConfig()
                        .getHttpClientConfig()
                        .setParam("http.connection.timeout", 40000)
                        .setParam("http.socket.timeout", 40000);

        // Initialization of standard headers that used in each request.
        STANDARD_HEADERS.put("Host", xHost);
        STANDARD_HEADERS.put("Accept-Language", "ru-RU");
        STANDARD_HEADERS.put("Accept-Charset", "UTF-8");

        // Specific options for GET requests.
        getRequestSpecBuilder.setBaseUri(baseUri + "/" + basePath);
        getRequestSpecBuilder.setPort(port);
        getRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        getRequestSpecBuilder.setContentType(GET_CONTENT_TYPE);
        getRequestSpecBuilder.setConfig(
                RestAssuredConfig.newConfig().
                        httpClient(getHttpConfig).
                        sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                        decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                        encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        getSpecification = getRequestSpecBuilder.build();

        // Specific options for POST requests.
        postRequestSpecBuilder.setBaseUri(baseUri + "/" + basePath);
        postRequestSpecBuilder.setPort(port);
        postRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestSpecBuilder.setContentType(POST_CONTENT_TYPE);
        postRequestSpecBuilder.setConfig(
                RestAssuredConfig.newConfig().
                        httpClient(postHttpConfig).
                        sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                        decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                        encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecification = postRequestSpecBuilder.build();

        // Specific options for upload requests.
        postRequestUnContentSpecBuilder.setBaseUri(baseUri + "/" + basePath);
        postRequestUnContentSpecBuilder.setPort(port);
        postRequestUnContentSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestUnContentSpecBuilder.setConfig(
                RestAssuredConfig.newConfig().
                        httpClient(postHttpConfig).
                        sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                        decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                        encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecificationUnContent = postRequestUnContentSpecBuilder.build();

        // Other settings.
        postRequestUnloadContentSpecBuilder.setBaseUri(baseUri + "/" + basePath);
        postRequestUnloadContentSpecBuilder.setPort(port);
        postRequestUnloadContentSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestUnloadContentSpecBuilder.setContentType("multipart/form-data");
        postRequestUnloadContentSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(postHttpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecificationUnloadContent = postRequestUnloadContentSpecBuilder.build();

        responseSpecification = responseSpecBuilder.build();
        RestAssured.urlEncodingEnabled = false;

        // Must be called last.
        if (authData != null) {
            setAuthData(authData);
        }
    }

    public BackendSettings getBackendSettings() {
        return backendSettings;
    }

    public AuthenticationData getAuthData() {
        return authData;
    }

    // Rework this method according to your project needs.
    private void setAuthData(AuthenticationData authData) {
        if ((authData != null)) {
            this.authData = authData;

            switch (getAuthData().getAuthenticationType()) {
                case AUTHENTICATION_TYPE_YOUR_PROJECT:
                    getRequestSpecBuilder.addCookie(".AspNet.Cookies", authData.getAuthCookie());
                    getSpecification = getRequestSpecBuilder.build();

                    postRequestSpecBuilder.addCookie(".AspNet.Cookies", authData.getAuthCookie());
                    postSpecification = postRequestSpecBuilder.build();

                    postRequestUnContentSpecBuilder.addCookie(".AspNet.Cookies", authData.getAuthCookie());
                    postSpecificationUnContent = postRequestUnContentSpecBuilder.build();
                    break;
                default:
                    log.error(
                            "Something gone wrong with rest service initialization. Unknown AUTHENTICATION_TYPE: {}.",
                            getAuthData().getAuthenticationType());
            }
        }
    }

    @Override
    public Response get(final String methodPath,
                        final Map<String, String> headers,
                        final String description) {
        Response response;
        try {
            if (null == headers) {
                response = RestAssured.given().
                        spec(getSpecification).
                        get(methodPath);
            } else {
                response = RestAssured.given().
                        spec(getSpecification).
                        headers(headers).
                        get(methodPath);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "GET " + basePath + methodPath,
                                    headers,
                                    null,
                                    "",
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response get(final String methodPath,
                        final Map<String, String> headers,
                        final Map<String, ?> parameters,
                        final String description) {
        Response response;
        try {
            if (headers != null) {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(getSpecification).
                            headers(headers).
                            parameters(parameters).
                            get(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(getSpecification).
                            headers(headers).
                            get(methodPath);
                }
            } else {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(getSpecification).
                            parameters(parameters).
                            get(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(getSpecification).
                            get(methodPath);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "GET " + basePath + methodPath,
                                    headers,
                                    null,
                                    "",
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response post(final String methodPath,
                         final Map<String, String> headers,
                         final String requestPayload,
                         final String description)
            throws IOException {
        Response response;
        try {
            if (headers != null) {
                if (requestPayload != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            body(requestPayload).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            post(methodPath);
                }
            } else {
                if (requestPayload != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            body(requestPayload).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            post(methodPath);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "POST " + basePath + methodPath,
                                    headers,
                                    null,
                                    requestPayload,
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response post(final String methodPath,
                         final Map<String, String> headers,
                         final Map<String, ?> parameters,
                         final String description)
            throws IOException {
        Response response;
        try {
            if (headers != null && headers.size() != 0) {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            parameters(parameters).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            post(methodPath);
                }
            } else {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            parameters(parameters).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecificationUnContent).
                            post(methodPath);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "POST " + basePath + methodPath,
                                    headers,
                                    parameters,
                                    "",
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response put(final String methodPath,
                        final Map<String, String> headers,
                        final String requestPayload,
                        final String description)
            throws IOException {
        Response response;
        try {
            response = RestAssured.given().
                    spec(postSpecification).
                    headers(headers).
                    body(requestPayload).
                    put(methodPath);
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "PUT " + basePath + methodPath,
                                    headers,
                                    null,
                                    requestPayload,
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response delete(final String methodPath,
                           final Map<String, String> headers,
                           final String requestPayload,
                           final String description)
            throws IOException {
        Response response;
        try {
            if (null != headers) {
                if (null != requestPayload) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            body(requestPayload).
                            delete(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            delete(methodPath);
                }
            } else {
                if (null != requestPayload) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            body(requestPayload).
                            delete(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            delete(methodPath);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "DELETE " + basePath + methodPath,
                                    headers,
                                    null,
                                    requestPayload,
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response patch(final String methodPath,
                          final Map<String, String> headers,
                          final String requestPayload,
                          final String description)
            throws IOException {
        Response response;
        try {
            if (headers != null) {
                if (requestPayload != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            body(requestPayload).
                            patch(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).
                            patch(methodPath);
                }
            } else {
                if (requestPayload != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            body(requestPayload).
                            patch(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            patch(methodPath);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription(
                                    "PATCH " + basePath + methodPath,
                                    headers,
                                    null,
                                    requestPayload,
                                    3) +
                            "\n" + e);
        }

        return response;
    }

    @Override
    public Response post(final String methodPath,
                         final String file,
                         final Map<String, ?> headers,
                         final String description)
            throws IOException {
        Response response;
        try {
            response = RestAssured.
                    given().
                    spec(postSpecificationUnContent).
                    multiPart(new File(file)).
                    headers(headers).
                    when().
                    post(methodPath);
        } catch (Exception e) {
            throw new RuntimeException(
                    description +
                            FormattingUtils.requestDescription("POST " + basePath + methodPath,
                                    null,
                                    null,
                                    null,
                                    3) +
                            "\n" + e);
        }

        return response;
    }
}

