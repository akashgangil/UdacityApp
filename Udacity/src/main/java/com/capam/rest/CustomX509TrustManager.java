package com.capam.rest;

/**
 * Created by acer on 7/10/13.
 */

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Taken from: http://janis.peisenieks.lv/en/76/english-making-an-ssl-connection-via-android/
 *
 */
public class CustomX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs,
                                   String authType) throws CertificateException {

        // Here you can verify the servers certificate. (e.g. against one which is stored on mobile device)

        // InputStream inStream = null;
        // try {
        // inStream = MeaApplication.loadCertAsInputStream();
        // CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // X509Certificate ca = (X509Certificate)
        // cf.generateCertificate(inStream);
        // inStream.close();
        //
        // for (X509Certificate cert : certs) {
        // // Verifing by public key
        // cert.verify(ca.getPublicKey());
        // }
        // } catch (Exception e) {
        // throw new IllegalArgumentException("Untrusted Certificate!");
        // } finally {
        // try {
        // inStream.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
