//package http;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.google.gson.Gson;
//import ir.baran.portalgateway.general.IDTO;
//import ir.baran.portalgateway.util.HttpUtil;
//import ir.baran.portalgateway.util.JsonUtil;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.utils.URIBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author tooraj <tooraj.jamal@gmail.com>
// * @since 8/31/16.
// */
//public class BaseHttpDAO {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHttpDAO.class);
//
//    public static IDTO get(URIBuilder uriBuilder, Class<? extends IDTO> returnObjectType) {
//        return get(uriBuilder, returnObjectType, null);
//    }
//
//    public static IDTO get(URIBuilder uriBuilder, Class<? extends IDTO> returnObjectType, Integer timeOut) {
//        final String getResultString = HttpUtil.get(uriBuilder, timeOut);
//
//        IDTO resultObject = null;
//
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//            resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(getResultString), returnObjectType);
//        } catch (IOException e) {
//            LOGGER.error(String.valueOf(e), e);
//        }
//
//        return resultObject;
//    }
//
//    public static IDTO get(URIBuilder uriBuilder,
//                           Class<? extends IDTO> returnObjectType,
//                           Map<String, String> headers,
//                           Integer timeOut) {
//        final String getResultString = HttpUtil.get(uriBuilder, timeOut, headers);
//
//        IDTO resultObject = null;
//
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//            resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(getResultString), returnObjectType);
//        } catch (IOException e) {
//            LOGGER.error(String.valueOf(e), e);
//        }
//
//        return resultObject;
//    }
//
//    public static IDTO delete(String path, Class<? extends IDTO> returnObjectType, List<NameValuePair> headerList) {
//        final String getResultString = HttpUtil.delete(path, null, headerList);
//
//        IDTO resultObject = null;
//
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//            resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(getResultString), returnObjectType);
//        } catch (IOException e) {
//            LOGGER.error(String.valueOf(e), e);
//        }
//
//        return resultObject;
//    }
//
//    public static boolean isJSONValid(String jsonInString) {
//        try {
//            new Gson().fromJson(jsonInString, Object.class);
//            if (jsonInString.indexOf("{") >= 0) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (com.google.gson.JsonSyntaxException ex) {
//            return false;
//        }
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType) {
//        return post(host, path, payload, returnObjectType, null, null, null, null, false);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, Integer timeout) {
//        return post(host, path, payload, returnObjectType, timeout, null, null, null, null);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, List<NameValuePair> headerList) {
//        return post(host, path, payload, returnObjectType, null, null, null, headerList, null);
//    }
//
//    protected IDTO postWithReturnErrorObject(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, List<NameValuePair> headerList) {
//        return post(host, path, payload, returnObjectType, null, null, null, headerList, true);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, String username, String password) {
//        return post(host, path, payload, returnObjectType, null, username, password, null, null);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, Integer timeOut, String username, String password, List<NameValuePair> headerList, Boolean returnErrorObject) {
//        final String postResultString = HttpUtil.post(host + path, payload, timeOut, username, password, headerList, returnErrorObject);
//        if (postResultString == null || !isJSONValid(postResultString)) {
//            return null;
//        }
//
//        IDTO resultObject;
//
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//            resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(postResultString), returnObjectType);
//        } catch (IOException e) {
//            LOGGER.error("Response mapping problem." +
//                    " | from='" + postResultString + "'" +
//                    " | to='" + returnObjectType + "'", e);
//            return null;
//        }
//
//        return resultObject;
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, Class<? extends IDTO> parametricTeturnObjectType, List<NameValuePair> headerList) {
//        return post(host, path, payload, returnObjectType, parametricTeturnObjectType, null, null, null, headerList);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, Class<? extends IDTO> parametricTeturnObjectType, List<NameValuePair> headerList, Integer timeout) {
//        return post(host, path, payload, returnObjectType, parametricTeturnObjectType, timeout, null, null, headerList);
//    }
//
//    protected IDTO post(String host, String path, Object payload, Class<? extends IDTO> returnObjectType, Class<? extends IDTO> parametricTeturnObjectType, Integer timeOut, String username, String password, List<NameValuePair> headerList) {
//        final String postResultString = HttpUtil.post(host + path, payload, timeOut, username, password, headerList, null);
//        if (postResultString == null) {
//            return null;
//        }
//
//        IDTO resultObject;
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(returnObjectType, parametricTeturnObjectType);
//        try {
//            resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(postResultString), javaType);
//        } catch (IOException e) {
//            LOGGER.error("Response mapping problem." +
//                    " | from='" + postResultString + "'" +
//                    " | to='" + returnObjectType + "'", e);
//            return null;
//        }
//
//        return resultObject;
//    }
//
//    /**
//     * Note: Appropriate for xml responses.
//     */
//    protected IDTO post(String host, String path, List<NameValuePair> urlParameters, Class<? extends IDTO> returnObjectType, ResponseTypeEnum responseType) {
//        return post(host, path, urlParameters, returnObjectType, null, null, null, responseType);
//    }
//
//    /**
//     * Note: Appropriate for xml responses.
//     */
//    protected IDTO post(String host, String path, List<NameValuePair> urlParameters, Class<? extends IDTO> returnObjectType, Integer timeOut, String username, String password, ResponseTypeEnum responseType) {
//        final String postResultString = HttpUtil.post(host + path, urlParameters, timeOut, username, password);
//
//        IDTO resultObject = null;
//
//        if (ResponseTypeEnum.XML.equals(responseType)) {
//            final XmlMapper objectMapper = new XmlMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            try {
//                resultObject = objectMapper.readValue(postResultString, returnObjectType);
//            } catch (IOException e) {
//                LOGGER.error("Response mapping problem." +
//                        " | from='" + postResultString + "'" +
//                        " | to='" + returnObjectType + "'", e);
//            }
//        } else if (ResponseTypeEnum.JSON.equals(responseType)) {
//            final ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            try {
//                resultObject = objectMapper.readValue(JsonUtil.cleanJsonString(postResultString), returnObjectType);
//            } catch (IOException e) {
//                LOGGER.error("Response mapping problem." +
//                        " | from='" + postResultString + "'" +
//                        " | to='" + returnObjectType + "'", e);
//            }
//        }
//
//        return resultObject;
//    }
//
//}
