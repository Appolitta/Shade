package stories.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import stories.managers.GlobalObjectMappers;
import stories.model.custom.BackendSettings;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Map;


/**
 * Different utils to format output.
 */
public class FormattingUtils {
    private static ScriptEngineManager factory = new ScriptEngineManager();
    private static ScriptEngine engine = factory.getEngineByName("JavaScript");

    private FormattingUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    public static String requestDescription(final String restMethodFullPath,
                                            final Map<String, String> reqHeaders,
                                            final Map<String, ?> reqParameters,
                                            final String reqBody,
                                            final int stackTraceDepth) {
        return "\nClass: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getClassName() +
                "\nMethod: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getMethodName() +
                "\nREST method: " + restMethodFullPath +
                ((reqHeaders != null)
                        ? "\nRequest headers:\n" + mapToString(reqHeaders)
                        : "") +
                ((reqParameters != null) ?
                        "\nRequest parameters:\n" + mapToString(reqParameters)
                        : "") +
                ((reqBody != null && !reqBody.equals(""))
                        ? "\nRequest body:\n" + reqBody
                        : "");
    }

    public static String jsonStringPretty(String jsonString) {
        String retPretty = jsonString;
        if (jsonString != null && !jsonString.equals("")) {
            try {
                Object json = GlobalObjectMappers.getObjectMapperWithNullFieldsPreserve().readValue(jsonString, Object.class);
                retPretty =
                        GlobalObjectMappers.getObjectMapperWithNullFieldsPreserve().writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return retPretty;
    }

    private static String mapToString(Map<String, ?> map) {
        StringBuilder retStr = new StringBuilder();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String valueStr;
            if (value == null) {
                valueStr = "null";
            } else if (value instanceof String ||
                    value instanceof Boolean ||
                    value instanceof Byte ||
                    value instanceof Character ||
                    value instanceof Short ||
                    value instanceof Integer ||
                    value instanceof Long ||
                    value instanceof Float ||
                    value instanceof Double ||
                    value instanceof BigDecimal) {
                valueStr = value.toString();
            } else {
                try {
                    valueStr = GlobalObjectMappers.getObjectMapperWithNullFieldsPreserve().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    valueStr = "!!! unknown object !!!";
                    e.printStackTrace();
                }
            }

            if (valueStr.contains("{")) {
                valueStr = jsonStringPretty(value.toString()).replaceAll("\r\n", "\r\n\t");
            }

            retStr.append(key)
                    .append("=")
                    .append(valueStr)
                    .append("\n");

            if (key.equals("X-Filter"))
                try {
                    String valueStrDecode = null;
                    try {
                        valueStrDecode = URLDecoder.decode((String) engine.eval("escape('" + valueStr + "')"), "UTF-8");
                    } catch (ScriptException e) {
                        e.printStackTrace();
                        try {
                            valueStrDecode = URLDecoder.decode(valueStr, "UTF-8");
                        } catch (IllegalArgumentException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (valueStrDecode != null) {
                        retStr.append("decode ")
                                .append(key)
                                .append("=")
                                .append(jsonStringPretty(valueStrDecode).replaceAll("\r\n", "\r\n\t"))
                                .append("\n");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        }

        return retStr.toString();
    }

    public static String buildApiPath(final BackendSettings backendSettings) {
        String sslStr = backendSettings.getServerSsl().toLowerCase();
        String sslDefaultStr = backendSettings.getServerSslDefault().toLowerCase();
        boolean ssl = sslStr.equals("${ssl}")
                ? (sslDefaultStr.contains("true") || sslDefaultStr.contains("yes"))
                : (sslStr.contains("true") || sslStr.contains("yes"));

        return (ssl ? "https://" : "http://") +
                backendSettings.getServerUrl() +
                "/" +
                backendSettings.getBaseApiPath();
    }
}
