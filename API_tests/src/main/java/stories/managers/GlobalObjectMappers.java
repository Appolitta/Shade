package stories.managers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Global Jackson object mappers.
 */
public class GlobalObjectMappers {
    private GlobalObjectMappers() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    private static final ObjectMapper OBJECT_MAPPER_WITH_NULL_FIELDS_PRESERVE =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                    .enable(SerializationFeature.INDENT_OUTPUT);

    private static final ObjectMapper OBJECT_MAPPER_WITHOUT_NULL_FIELDS_PRESERVE =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Get one of pre configured object mappers.
     *
     * @return mapper that map all fields to the payload even if they are containing nulls.
     */
    public static ObjectMapper getObjectMapperWithNullFieldsPreserve() {
        return OBJECT_MAPPER_WITH_NULL_FIELDS_PRESERVE;
    }

    /**
     * Get one of pre configured object mappers.
     *
     * @return mapper that exclude fields containing null values from request payload.
     */
    public static ObjectMapper getObjectMapperWithoutNullFieldsPreserve() {
        return OBJECT_MAPPER_WITHOUT_NULL_FIELDS_PRESERVE;
    }
}
