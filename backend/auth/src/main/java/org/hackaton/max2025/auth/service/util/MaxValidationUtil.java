package org.hackaton.max2025.auth.service.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.auth.exception.MaxValidationException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@UtilityClass
public class MaxValidationUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static Long validateAndExtractUserId(String initData, String botToken) {
        String decodedInitData = urlDecode(initData);

        Map<String, String> params = parseParams(decodedInitData);
        String receivedHash = params.get("hash");

        if (receivedHash == null) {
            throw new MaxValidationException("Hash not found in init data");
        }

        String dataCheckString = buildDataCheckString(params);

        String secretKeyHex = computeSecretKey(botToken);

        String expectedHash = computeHmacSha256Hex(dataCheckString, secretKeyHex);

        if (!expectedHash.equals(receivedHash)) {
            throw new MaxValidationException("Hash mismatch - data may be tampered");
        }

        Long userId = extractUserIdFromParams(params);
        return userId;
    }

    private static Map<String, String> parseParams(String decodedInitData) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = decodedInitData.split("&");

        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                params.put(key, value);
            }
        }
        return params;
    }

    private static String buildDataCheckString(Map<String, String> params) {
        Map<String, String> paramsCopy = new HashMap<>(params);
        paramsCopy.remove("hash");

        List<String> keys = new ArrayList<>(paramsCopy.keySet());
        Collections.sort(keys);

        StringBuilder dataCheckString = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = paramsCopy.get(key);
            dataCheckString.append(key).append("=").append(value);
            if (i != keys.size() - 1) {
                dataCheckString.append("\n");
            }
        }

        return dataCheckString.toString();
    }

    private static String computeSecretKey(String botToken) {
        byte[] secretKeyBytes = computeHmacSha256Bytes(botToken, "WebAppData");
        return bytesToHex(secretKeyBytes);
    }

    private static String computeHmacSha256Hex(String data, String hexKey) {
        try {
            byte[] keyBytes = hexStringToByteArray(hexKey);

            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, HMAC_SHA256);
            mac.init(secretKeySpec);

            byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new MaxValidationException("HMAC-SHA256 computation failed: " + e.getMessage());
        }
    }

    private static byte[] computeHmacSha256Bytes(String data, String key) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new MaxValidationException("HMAC-SHA256 computation failed: " + e.getMessage());
        }
    }

    private static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String urlDecode(String encoded) {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }

    private static Long extractUserIdFromParams(Map<String, String> params) {
        String userJson = params.get("user");
        if (userJson == null) {
            throw new MaxValidationException("User data not found in init data");
        }

        try {
            int idIndex = userJson.indexOf("\"id\":");
            if (idIndex == -1) {
                throw new MaxValidationException("User ID not found in user data");
            }

            int colonIndex = userJson.indexOf(":", idIndex);
            int commaIndex = userJson.indexOf(",", colonIndex);
            if (commaIndex == -1) {
                commaIndex = userJson.indexOf("}", colonIndex);
            }

            String idStr = userJson.substring(colonIndex + 1, commaIndex).trim();
            return Long.parseLong(idStr);

        } catch (Exception e) {
            throw new MaxValidationException("Failed to extract user ID from user data: " + e.getMessage());
        }
    }
}