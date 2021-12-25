package encryption;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Slf4j
public class Encrypt {

    @Test
    public void generateHttpSignature() throws InvalidKeyException, NoSuchAlgorithmException {
        String httpSignature = String.join(", ",
          getSignatureTemplate("keyid", getMerchantKey()),
          getSignatureTemplate("algorithm", "HmacSHA256"),
          getSignatureTemplate("headers", "host date (request-target) digest v-c-merchant-id"),
          getSignatureTemplate("signature", generateSignatureHash()));

        log.debug(httpSignature);
    }

    private String getSignatureTemplate(String keyString, String valueString){
        return  keyString+ "=\"" + valueString + "\"";
    }

    private String getMerchantKey(){
        return "22f596a4-23ae-4031-bcbc-6f25dbfca91b";
    }

    private String generateSignatureHash() throws NoSuchAlgorithmException, InvalidKeyException {
        String signatureParam = getSignatureParams();
        String keyString = getSharedSecret();
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(originalKey);
        hmacSha256.update(signatureParam.getBytes());
        byte[] HmachSha256DigestBytes = hmacSha256.doFinal();
        return Base64.getEncoder().encodeToString(HmachSha256DigestBytes);
    }

    private String getSharedSecret(){
        return "dfp630WMn73xEmd5KRa8/AZcbyLkoE3NHn3/SFAtmc8=";
    }

    public String generateDigest() throws NoSuchAlgorithmException {
        String payload = getPayload();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(payload.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String computedDigest = "SHA-256=" + Base64.getEncoder().encodeToString(digest);
        log.debug("Computed Digest: {}", computedDigest);
        return computedDigest;
    }

    private String getSignatureParams() throws NoSuchAlgorithmException {
        String customSignatureParam = String.join("\n",
          getHost(),
          getDate(),
          getRequestTarget(),
          getGeneratedDigest(),
          getMerchantId());

        return customSignatureParam;
    }

    private String getHost(){
        return  "host: apitest.cybersource.com";
    }

    private String getDate(){
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
        log.debug(date);
        return "date: "+date;
    }
    private String getRequestTarget(){
        return "(request-target): post /flex/v2/sessions";
    }

    private String getGeneratedDigest() throws NoSuchAlgorithmException {
        return "digest: "+ generateDigest();
    }
    private String getMerchantId(){
        return "v-c-merchant-id: michaelkors_test_usd";
    }

    private String getPayload(){
        String bodyText = "{\n" +
          "    \"fields\": {\n" +
          "        \"paymentInformation\": {\n" +
          "            \"card\": {\n" +
          "                \"number\": {},\n" +
          "                \"securityCode\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"expirationMonth\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"expirationYear\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"type\": {\n" +
          "                    \"required\": false\n" +
          "                }\n" +
          "            }\n" +
          "        },\n" +
          "        \"orderInformation\": {\n" +
          "            \"amountDetails\": {\n" +
          "                \"totalAmount\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"currency\": {\n" +
          "                    \"required\": false\n" +
          "                }\n" +
          "            },\n" +
          "            \"billTo\": {\n" +
          "                \"address1\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"address2\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"administrativeArea\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"buildingNumber\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"country\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"district\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"locality\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"postalCode\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"email\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"firstName\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"lastName\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"phoneNumber\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"company\": {\n" +
          "                    \"required\": false\n" +
          "                }\n" +
          "            },\n" +
          "            \"shipTo\": {\n" +
          "                \"address1\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"address2\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"administrativeArea\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"buildingNumber\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"country\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"district\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"locality\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"postalCode\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"firstName\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"lastName\": {\n" +
          "                    \"required\": false\n" +
          "                },\n" +
          "                \"company\": {\n" +
          "                    \"required\": false\n" +
          "                }\n" +
          "            }\n" +
          "        }\n" +
          "    }\n" +
          "}";

        return bodyText;
    }
}
