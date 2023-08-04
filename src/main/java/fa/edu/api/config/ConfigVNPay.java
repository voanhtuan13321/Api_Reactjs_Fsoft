package fa.edu.api.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Configuration VNPay.
 *
 * @author Anh Tuan
 * @version 1.0
 * @since 03/08/2023
 */
public class ConfigVNPay {
  public static final String DEFAULT_IP = "127.0.0.1";
  public static final String VNP_VERSION = "2.1.0";
  public static final String VNP_COMMAND = "pay";
  public static final String VNP_CURR_CODE = "VND";
  public static final String VNP_BANK_CODE = "NCB";
  public static final String VNP_ORDER_TYPE = "250000"; // thanh toán hoá đơn
  public static final String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
  public static final String VNP_HASH_SECRET = "ONSLPOUWNXTBCWHHCZVFAQDXAJYZYGIT";
  public static final String VNP_TIME_CODE = "KWBZIWD8";
  private static final Random random = new Random();

  private ConfigVNPay() { }

  /**
   * Encrypt SHA512.
   *
   * @param key  The key
   * @param data The data
   * @return string SHA523
   */
  public static String hmacSHA512(final String key, final String data) {
    try {
      if (key == null || data == null) {
        throw new NullPointerException();
      }

      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes();

      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");

      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);

      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }

      return sb.toString();
    } catch (Exception ex) {
      return "";
    }
  }

  /**
   * Random number.
   *
   * @param len the length of the number
   * @return string number
   * @author Anh Tuan
   * @since 25/05/2023
   */
  public static String getRandomNumber(int len) {

    String chars = "0123456789";
    StringBuilder sb = new StringBuilder(len);

    for (int i = 0; i < len; i++) {
      sb.append(chars.charAt(random.nextInt(chars.length())));
    }

    return sb.toString();
  }
}
