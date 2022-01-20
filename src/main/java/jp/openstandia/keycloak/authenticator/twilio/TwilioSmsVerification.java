package jp.openstandia.keycloak.authenticator.twilio;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.jboss.logging.Logger;

public class TwilioSmsVerification {

  private static final Logger logger =
      Logger.getLogger(TwilioSmsVerification.class.getPackage().getName());

  private final String accountSid;
  private final String authToken;
  private final String serviceSid;

  public TwilioSmsVerification(String accountSid, String authToken, String serviceSid) {
    this.accountSid = accountSid;
    this.authToken = authToken;
    this.serviceSid = serviceSid;
  }

  public boolean sendSMS(String phoneNumber) {
    Twilio.init(accountSid, authToken);
    Verification verification = Verification.creator(serviceSid, phoneNumber, "sms").create();
    logger.info("Code send to phone " + phoneNumber + " sid: " + verification.getSid());

    return true;
  }

  public boolean verifySMS(String phoneNumber, String code) {
    Twilio.init(accountSid, authToken);
    VerificationCheck verificationCheck =
        VerificationCheck.creator(serviceSid, code).setTo(phoneNumber).create();
    String status = verificationCheck.getStatus();
    logger.info("Code " + code + " verify for phone " + phoneNumber + " status " + status);
    return "approved".equals(status);
  }
}
