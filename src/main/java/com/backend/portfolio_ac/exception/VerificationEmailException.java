package com.backend.portfolio_ac.exception;

public class VerificationEmailException extends RuntimeException {

  public enum Type {
    CODE_INVALID,
    CODE_EXPIRED,
    EMAIL_VERIFIED,
    EMAIL_VERIFICATION_FAIL_SEND
  }

  private final VerificationEmailException.Type type;

  public VerificationEmailException(String message, VerificationEmailException.Type type) {
    super(message);
    this.type = type;
  }

  public VerificationEmailException.Type getType(){
    return type;
  }

}
