package fa.edu.api.controllers;

import fa.edu.api.config.ConfigVNPay;
import fa.edu.api.requests.PaymentForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller PaymentController.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 25/05/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {

  /**
   * Create payment.
   *
   * @param req thong tin payment
   * @return ResponseEntity
   */
  @PostMapping("/create-payment")
  public ResponseEntity<String> createPayment(@RequestBody PaymentForm req) {
    int amount = req.getAmount() * 100;
    Map<String, String> vnpParams = new HashMap<>();
    vnpParams.put("vnp_Version", ConfigVNPay.VNP_VERSION);
    vnpParams.put("vnp_Command", ConfigVNPay.VNP_COMMAND);
    vnpParams.put("vnp_TmnCode", ConfigVNPay.VNP_TIME_CODE);
    vnpParams.put("vnp_Amount", String.valueOf(amount));
    vnpParams.put("vnp_CurrCode", ConfigVNPay.VNP_CURR_CODE);
    vnpParams.put("vnp_OrderInfo", req.getVnpOrderInfo());
    vnpParams.put("vnp_OrderType", ConfigVNPay.VNP_ORDER_TYPE);
    vnpParams.put("vnp_BankCode", ConfigVNPay.VNP_BANK_CODE);
    vnpParams.put("vnp_TxnRef", ConfigVNPay.getRandomNumber(8));
    vnpParams.put("vnp_Locale", "vn");
    vnpParams.put("vnp_IpAddr", ConfigVNPay.DEFAULT_IP);
    vnpParams.put("vnp_ReturnUrl", req.getVnpReturnUrl());

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnpCreateDate = formatter.format(cld.getTime());
    vnpParams.put("vnp_CreateDate", vnpCreateDate);
    cld.add(Calendar.MINUTE, 15);
    vnpParams.put("vnp_ExpireDate", formatter.format(cld.getTime()));

    // Build data to hash and query string
    List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator<String> itr = fieldNames.iterator();

    while (itr.hasNext()) {
      String fieldName = itr.next();
      String fieldValue = vnpParams.get(fieldName);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnpSecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.VNP_HASH_SECRET, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
    String paymentUrl = ConfigVNPay.VNP_PAY_URL + "?" + queryUrl;

    return ResponseEntity.ok().body(paymentUrl);
  }

  /**
   * Check payment.
   *
   * @param req thong tin payment
   * @return ResponseEntity
   * @author AnhTuan
   * @since 25/05/2023
   */
  @PostMapping("/check-payment")
  public ResponseEntity<String> checkPayment(@RequestBody PaymentForm req) {
    if (req == null) {
      return ResponseEntity.notFound().build();
    }
    if (req.getVnpResponseCode().equals("00")) {
      return ResponseEntity.ok().body(null);
    }

    return ResponseEntity.notFound().build();
  }

}
