package fa.edu.api.requests;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PaymentForm {
  private Integer amount;
  private String vnpOrderInfo;
  private String vnpBankCode;
  private String vnpTxnRef;
  private String txtBillingMobile;
  private String txtBillingEmail;
  private String txtBillingFullname;
  private String vnpReturnUrl;
  private String vnpResponseCode;  // check response code: vnp_ResponseCode == 0 => success
}
