package ws.cloudcache.memcache;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.lang.StringEscapeUtils;
import ws.cloudcache.memcache.util.MetaUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * User: treeder
 * Date: Jan 13, 2009
 * Time: 7:35:28 PM
 */
public class CloudCache extends AbstractCache implements BigCache {
    private String accessKey;
    private String secretKey;
    private static final String BASE_URL = "http://cloudcache.ws/";
    private static String baseUrlWithKeys = BASE_URL + "{0}"; //?akey={1}&skey={2}";
    private HttpClient httpClient = new HttpClient();
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");

    {
        // Not sure how these should be set right now...
//        httpClient.getParams().setSoTimeout();
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout();
//        httpClient.getParams().setConnectionManagerTimeout();
    }

    public CloudCache(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;        
    }

    public CloudCache(String accessKey, String secretKey, ExecutorService executorService) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.executorService = executorService;
    }

    public void shutdown() {
        if (executorService != null) executorService.shutdownNow();
    }

    private String baseUrl(String key) {
        return MessageFormat.format(baseUrlWithKeys, key);
    }


    void auth() throws Exception {
        String ts = generateTimestamp();
        String sig = generateSignature("CloudCache", "auth", ts, secretKey);
        GetMethod method = new GetMethod(BASE_URL + "auth");
        method.addRequestHeader("akey", accessKey);
        method.addRequestHeader("timestamp", ts);
        method.addRequestHeader("signature", sig);
        try {
            int status = httpClient.executeMethod(method);
//            System.out.println("status=" + status);
            if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            statistics.puts.incrementAndGet();
            System.out.println("RESPONSE: " + method.getResponseBodyAsString());
        } finally {
            method.releaseConnection();
        }
    }

    /**
     * todo: We'll probably want to send multi-part at some point with straight up bytes.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     * @throws Exception
     */
    public void put(String key, Serializable object, int expiresInSeconds) throws Exception {
        StringBuffer sb = new StringBuffer(baseUrl(key));

        String val = marshaller.marshalToString(object);
//        System.out.println("PUTTING VAL=" + val);
        val = object.getClass().getName() + "/" + MetaUtils.expiryAsString(expiresInSeconds) + "/" + val;
//        System.out.println("url=" + sb.toString());
        PutMethod method = new PutMethod(sb.toString());
        String op = "PUT";
        addHeaders(method, op);
        printHeaders(method);
        method.setRequestBody(val);
        try {
            int status = httpClient.executeMethod(method);
            System.out.println("status=" + status);
            if (status != HttpStatus.SC_CREATED && status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            statistics.puts.incrementAndGet();
            System.out.println("PUT RESPONSE: " + method.getResponseBodyAsString());
        } finally {
            method.releaseConnection();
        }
    }

    private void printHeaders(HttpMethod method) {
        Header[] headers = method.getRequestHeaders();
        System.out.println("HEADERS:");
        for (Header header : headers) {
            System.out.println("\t" + header.getName() + "=" + header.getValue());
        }
    }

    private void addHeaders(HttpMethod method, String op) {
        String ts = generateTimestamp();
        method.addRequestHeader("akey", accessKey);
        method.addRequestHeader("timestamp", ts);
        String sig = generateSignature("CloudCache", op, ts, secretKey);
        method.addRequestHeader("signature", sig);
    }

    private String generateSignature(String service, String operation, String timestamp, String secretKey) {
        String toEncode = service + operation + timestamp;
        return signWithHmacSha1(secretKey, toEncode);
    }

    /**
     * Calculate the HMAC/SHA1 on a string.
     *
     * @param awsSecretKey    AWS secret key.
     * @param canonicalString canonical string representing the request to sign.
     * @return Signature
     */
    public static String signWithHmacSha1(String awsSecretKey, String canonicalString) {

        // The following HMAC/SHA1 code for the signature is taken from the
        // AWS Platform's implementation of RFC2104 (amazon.webservices.common.Signature)
        //
        // Acquire an HMAC/SHA1 from the raw key bytes.
        SecretKeySpec signingKey = new SecretKeySpec(awsSecretKey.getBytes(), "HmacSHA1");

        // Acquire the MAC instance and initialize with the signing key.
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            // should not happen
            throw new RuntimeException("Could not find sha1 algorithm", e);
        }
        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            // also should not happen
            throw new RuntimeException("Could not initialize the MAC algorithm", e);
        }

        // Compute the HMAC on the digest, and set it.
        try {
            byte[] b64 = Base64.encodeBase64(mac.doFinal(canonicalString.getBytes("UTF-8")));
            return new String(b64);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to get bytes from canonical string", e);
        }
    }

    private String generateTimestamp() {
        Date d = new Date();
        return timeFormat.format(d);
    }

    public Object get(String key) throws Exception {
        String url = baseUrl(key);
        System.out.println("GETTING FROM: " + url);
        StringBuffer sb = new StringBuffer(url);
        GetMethod method = new GetMethod(sb.toString());
        String op = "GET";
        addHeaders(method, op);
        printHeaders(method);
        try {
            int status = httpClient.executeMethod(method);
            System.out.println("get status = " + status);
            statistics.gets.incrementAndGet();
            if (status == HttpStatus.SC_NOT_FOUND) {
                // fine, not in cache, return null
                return null;
            } else if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            statistics.hits.incrementAndGet();
            long length = method.getResponseContentLength();
            if (length < 512) {
                String asString = method.getResponseBodyAsString();
                System.out.println("RESPONSE: " + asString);
            }
            // Parse out the class and expiry times.
            BufferedInputStream reader = new BufferedInputStream((method.getResponseBodyAsStream()));
            int slash = '/';
            int c;
            String className = null;
            Long expiryTime = null;
            StringWriter writer = new StringWriter();
            int position = 0;
            while ((c = reader.read()) != -1) {
                position++;
                if (c == slash) {
                    if (className == null) {
                        className = writer.toString();
                        writer = new StringWriter();
                    } else {
                        expiryTime = new Long(writer.toString());
                        break;
                    }
                } else {
                    writer.write(c);
                }
            }

            if (expiryTime > 0 && expiryTime < System.currentTimeMillis()) {
                return null;
            }

//            byte[] in = new byte[(int)length - position];
//            int received = reader.read(in, position, (int)length);
//            byte[] decodedBytes = Base64.decodeBase64(in);
            StringWriter buff = new StringWriter((int) length);
            while ((c = reader.read()) != -1) {
                buff.write(c);
            }
            String xml = StringEscapeUtils.unescapeJava(buff.toString());
//            System.out.println("className = " + className);
//            System.out.println("BUFF=" + xml);
//            JAXBContext context = JAXBContext.newInstance(String.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            Object o = unmarshaller.unmarshal(new StringReader(xml));
            Object o = marshaller.unmarshal(className, xml);
            if (o instanceof JAXBElement) {
                JAXBElement je = (JAXBElement) o;
                return je.getValue();
            }
            /*Object o = marshaller.unmarshal(s3Object.getDataInputStream(), s3Object);
          s3Object.closeDataInputStream();
          return (Serializable) o;*/
            return o;
        } finally {
            method.releaseConnection();
        }
    }

    public void remove(String key) throws Exception {
        StringBuffer sb = new StringBuffer(baseUrl(key));
//        sb.append("&key=").append(urlEncode(key));
        DeleteMethod method = new DeleteMethod(sb.toString());
        String op = "DELETE";
        addHeaders(method, op);
        printHeaders(method);
        try {
            int status = httpClient.executeMethod(method);
            System.out.println("get status = " + status);
            statistics.removes.incrementAndGet();
            if (status == HttpStatus.SC_NOT_FOUND) {
                // fine, not in cache, return null
            } else if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
        } finally {
            method.releaseConnection();
        }
    }

    private Object urlEncode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e); // should never happen
        }
    }

    public void add(String key, Serializable object, int expiresInSeconds) throws Exception {

    }

    public void replace(String key, Serializable object, int expiresInSeconds) throws Exception {

    }

    public List<String> listKeys() throws Exception {
        GetMethod method = new GetMethod(BASE_URL + "listkeys.xml");
        String op = "listkeys";
        addHeaders(method, op);
//        printHeaders(method);
        try {
            int status = httpClient.executeMethod(method);
//            System.out.println("get status = " + status);
//            System.out.println("reponse=" + method.getResponseBodyAsString());
            if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            List<String> ret = new ArrayList<String>();
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader xmlr = inputFactory.createXMLStreamReader(method.getResponseBodyAsStream());
            int eventType;
            while (xmlr.hasNext()) {
                eventType = xmlr.next();
//                printEventType(eventType);
                // these functions print the information about
                // the particular event by calling the relevant
                // function
//                printStartElement(xmlr);
//                printEndElement(xmlr);
//                printText(xmlr);
//                printPIData(xmlr);
//                printComment(xmlr);
                if (eventType == XMLEvent.START_ELEMENT) {
//                    System.out.println(xmlr.getName());
                    if (xmlr.getName().getLocalPart().equals("ITEM")) {
                        xmlr.next();
                        String value = xmlr.getText();
//                        System.out.println("value=" + value);
                        ret.add(value);
                    }
                }
            }
            return ret;
        } finally {
            method.releaseConnection();
        }
    }

    public long increment(String counterKey) throws Exception {
        String op = "incr";
        PostMethod method = new PostMethod(BASE_URL + counterKey + "/incr");
        addHeaders(method, "POST");
        printHeaders(method);
        method.addParameter("val", "1");
        try {
            int status = httpClient.executeMethod(method);
            System.out.println("get status = " + status);
            System.out.println("reponse=" + method.getResponseBodyAsString());
            if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            long ret = Long.parseLong(method.getResponseBodyAsString());
            return ret;
        } finally {
            method.releaseConnection();
        }
    }

    public long decrement(String counterKey) throws Exception {
        String op = "listkeys";
        PostMethod method = new PostMethod(BASE_URL + counterKey + "/decr");
        addHeaders(method, "POST");
        printHeaders(method);
        method.addParameter("val", "1");
        try {
            int status = httpClient.executeMethod(method);
            System.out.println("get status = " + status);
            System.out.println("reponse=" + method.getResponseBodyAsString());
            if (status != HttpStatus.SC_OK) {
                throw new CloudCacheException("Bad response from server. Status code: " + status);
            }
            long ret = Long.parseLong(method.getResponseBodyAsString());
            return ret;
        } finally {
            method.releaseConnection();
        }
    }
}
