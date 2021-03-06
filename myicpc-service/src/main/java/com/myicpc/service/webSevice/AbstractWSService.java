package com.myicpc.service.webSevice;

import com.myicpc.commons.utils.FormatUtils;
import com.myicpc.model.contest.Contest;
import com.myicpc.service.settings.GlobalSettingsService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Parent class for services, which connect to web service endpoints and get response
 *
 * @author Roman Smetana
 */
public abstract class AbstractWSService {
    @Autowired
    protected GlobalSettingsService globalSettingsService;

    /**
     * Connects to ICPC WS and get data from url
     *
     * @param url
     *            url to connect
     * @return received data from WS
     * @throws IOException
     */
    protected Object connectCM(String url, final Contest contest) throws IOException {
        return connectCM(globalSettingsService.getGlobalSettings().getContestManagementSystemUrl(), url, contest);
    }

    /**
     * Connects to ICPC WS and get data from url
     *
     * @param server
     *            ICPC server URL
     * @param url
     *            url to connect
     * @return received data from WS
     * @throws IOException
     */
    private String connectCM(final String server, final String url, final Contest contest) throws IOException {
        HttpGet httpGet = null;
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            builder.useProtocol("TLSv1.2");
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(contest.getWebServiceSettings()
                    .getWsCMToken(), ""));

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setSSLSocketFactory(sslsf);

            try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
                httpGet = new HttpGet("https://" + server + url);

                HttpResponse response = httpclient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                return IOUtils.toString(entity.getContent(), FormatUtils.DEFAULT_ENCODING);
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException ex) {
            throw new IOException(ex);
        } finally {
            releaseConnection(httpGet);
        }
    }

    /**
     * Release HTTP connection
     * @param httpGet
     */
    private static void releaseConnection(HttpGet httpGet) {
        if (httpGet != null) {
            httpGet.releaseConnection();
        }
    }
}
