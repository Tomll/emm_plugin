package com.example.administrator.emmapplication.utils;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustedCredentialsUtils {

    public static KeyStore getKeystore(){
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                keyStore.load(null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return keyStore;
    }

    public static X509TrustManager getX509TrustManager(KeyStore keystore) throws Exception {
        X509TrustManager pkixTrustManager = null;
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//        tmf.init(keystore);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keystore);
        TrustManager tms[] = tmf.getTrustManagers();
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                pkixTrustManager = (X509TrustManager) tms[i];
                break;
            }
        }
//        throw new Exception("Couldn't initialize");
        return pkixTrustManager;
    }

    public static void checkClientTrusted(X509TrustManager pkixTrustManager,X509Certificate[] arg0, String arg1) throws CertificateException {
        try {
            pkixTrustManager.checkClientTrusted(arg0, arg1);
        } catch (CertificateException excep) {
            excep.printStackTrace();
        }
    }

    public static void checkServerTrusted(X509TrustManager pkixTrustManager,X509Certificate[] arg0, String arg1) throws CertificateException {
        try {
            pkixTrustManager.checkServerTrusted(arg0, arg1);
        } catch (CertificateException excep) {
            excep.printStackTrace();
        }
    }

    public static X509Certificate[] getAcceptedIssuers(X509TrustManager pkixTrustManager) {
        LogUtils.d( "Number of trusted certificates is "+   pkixTrustManager.getAcceptedIssuers().length);
        return pkixTrustManager.getAcceptedIssuers();
    }

    public static void listX509Certificate(){
        try {
            //获取所有证书信息
            X509Certificate[] x509Certificates = getAcceptedIssuers(getX509TrustManager((KeyStore) null));
//            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<x509Certificates.length;i++){
                X509Certificate x509Certificate = x509Certificates[i];
//                x509Certificate.getBasicConstraints();
//                x509Certificate.getExtendedKeyUsage();
//                x509Certificate.getIssuerAlternativeNames();
//                x509Certificate.getNotAfter();
//                x509Certificate.getNotBefore();
//                x509Certificate.getKeyUsage();
//                stringBuffer.append("证书名 SigAlgName : "+x509Certificate.getSigAlgName()+"\n");
//                stringBuffer.append("证书名 SigAlgName : "+x509Certificate.getSigAlgOID()+"\n");
//                stringBuffer.append("证书名 SigAlgName : "+x509Certificate.getType()+"\n");
//                stringBuffer.append("版本号 Version : "+ x509Certificate.getVersion()+"\n");
//                stringBuffer.append("序列号 Serial Number : "+x509Certificate.getSerialNumber()+"\n");
//                stringBuffer.append("公钥 public key :"+x509Certificate.getPublicKey()+"\n\n\n");


                LogUtils.d("证书名信息 : "+x509Certificate.toString()+"\n\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
