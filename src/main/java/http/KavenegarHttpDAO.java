package http;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import ir.baran.portalgateway.Configs;
import ir.baran.portalgateway.general.configuration.logging.dao.DaoClassExtraParam;
import ir.baran.portalgateway.general.configuration.logging.dao.DaoMethodExtraParam;
import ir.baran.portalgateway.general.configuration.logging.dao.DaoTargetEnum;
import ir.baran.portalgateway.general.configuration.logging.dao.DaoTypeEnum;
import ir.baran.portalgateway.util.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author tooraj <tooraj.jamal@gmail.com>
 * @since 8/31/16.
 */
@Component
@DaoClassExtraParam(
        target = DaoTargetEnum.KAVE_NEGAR,
        type = DaoTypeEnum.HTTP
)
public class KavenegarHttpDAO extends BaseHttpDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(KavenegarHttpDAO.class);


    private static final String KAVENEGAR_PATH_SEND_SMS = "/v1/%s/sms/send.json";
    private static final String KAVENEGAR_PATH_SEND_VOICE_MESSAGE = "/v1/%s/call/maketts.json";


    /**
     * It is OK with both numbers with 98 or without
     */
    @DaoMethodExtraParam(path = KAVENEGAR_PATH_SEND_SMS)
    public Boolean sendSMS(String password,
                           String from,
                           String to,
                           String body) {
        /*LOGGER.info( "Request for kavenegar.sendSMS API." +
                " | to='" + to + "'" +
                " | body='" + body.replace("\n", "<NL>") + "'");*/
        Double status = null;
        String postRes = null;
        try {
            final ArrayList<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("receptor", to));
            urlParameters.add(new BasicNameValuePair("message", body));
            urlParameters.add(new BasicNameValuePair("sender", from));
            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme(Configs.KAVENEGAR_PROTOCOL)
                    .setHost(Configs.KAVENEGAR_HOST)
                    .setPort(Configs.KAVENEGAR_PORT)
                    .setPath(String.format(Configs.KAVENEGAR_PATH_SEND_SMS, password))
                    .addParameters(urlParameters);

            postRes = HttpUtil.get(uriBuilder);

            final LinkedTreeMap map = new Gson().fromJson(postRes, LinkedTreeMap.class);
            status = (Double) ((LinkedTreeMap) map.get("return")).get("status");
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e), e);
            return false;
        }

        return status != null && (status == 200 || status == 201);
    }

    /**
     * It is OK with both numbers with 98 or without
     */
    @DaoMethodExtraParam(path = KAVENEGAR_PATH_SEND_VOICE_MESSAGE)
    public Boolean sendVoiceCall(String to,
                                 String body) {
        Double status = null;
        String postRes = null;
        try {
            final ArrayList<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("receptor", to));
            urlParameters.add(new BasicNameValuePair("message", body));
            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme(Configs.KAVENEGAR_PROTOCOL)
                    .setHost(Configs.KAVENEGAR_HOST)
                    .setPort(Configs.KAVENEGAR_PORT)
                    .setPath(String.format(Configs.KAVENEGAR_PATH_SEND_VOICE_MESSAGE, Configs.KAVENEGAR_API_KEY))
                    .addParameters(urlParameters);

            postRes = HttpUtil.get(uriBuilder);

            final LinkedTreeMap map = new Gson().fromJson(postRes, LinkedTreeMap.class);
            status = (Double) ((LinkedTreeMap) map.get("return")).get("status");
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e), e);
            return false;
        }

        LOGGER.info("Response from kavenegar.sendVoiceMessage." +
                " | kavenegarResponseDTO='" + postRes + "'");

        return status != null && (status == 200 || status == 201);
    }


}
