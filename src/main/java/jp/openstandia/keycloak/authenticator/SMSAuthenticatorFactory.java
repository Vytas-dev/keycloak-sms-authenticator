package jp.openstandia.keycloak.authenticator;

import java.util.List;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

public class SMSAuthenticatorFactory
    implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

  public static final String PROVIDER_ID = "sms-authenticator-with-twilio";
  private static final SMSAuthenticator SINGLETON = new SMSAuthenticator();
  private static final Logger logger =
      Logger.getLogger(SMSAuthenticatorFactory.class.getPackage().getName());

  private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
    AuthenticationExecutionModel.Requirement.REQUIRED,
    AuthenticationExecutionModel.Requirement.DISABLED
  };

  private static final List<ProviderConfigProperty> configProperties;

  static {
    configProperties =
        ProviderConfigurationBuilder.create()
            .property()
            .name(SMSAuthContstants.CONFIG_TWILIO_ACCOUNT_SID)
            .label("Twilio Account SID")
            .type(ProviderConfigProperty.STRING_TYPE)
            .helpText("")
            .add()
            .property()
            .name(SMSAuthContstants.CONFIG_TWILIO_AUTH_TOKEN)
            .label("Twilio Auth Token")
            .type(ProviderConfigProperty.STRING_TYPE)
            .helpText("")
            .add()
            .property()
            .name(SMSAuthContstants.CONFIG_TWILIO_SERVICE_SID)
            .label("Twilio Service SID")
            .type(ProviderConfigProperty.STRING_TYPE)
            .helpText("")
            .add()
            .build();
  }

  public Authenticator create(KeycloakSession session) {
    return SINGLETON;
  }

  public String getId() {
    return PROVIDER_ID;
  }

  public void init(Scope scope) {
    logger.debug("Method [init]");
  }

  public void postInit(KeycloakSessionFactory factory) {
    logger.debug("Method [postInit]");
  }

  public List<ProviderConfigProperty> getConfigProperties() {
    return configProperties;
  }

  public String getHelpText() {
    return "SMS Authenticate using Twilio.";
  }

  public String getDisplayType() {
    return "Twilio SMS Authentication";
  }

  public String getReferenceCategory() {
    logger.debug("Method [getReferenceCategory]");
    return "sms-auth-code";
  }

  public boolean isConfigurable() {
    return true;
  }

  public Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES == null ? null : (Requirement[]) REQUIREMENT_CHOICES.clone();
  }

  public boolean isUserSetupAllowed() {
    return true;
  }

  public void close() {
    logger.debug("<<<<<<<<<<<<<<< SMSAuthenticatorFactory close");
  }
}
