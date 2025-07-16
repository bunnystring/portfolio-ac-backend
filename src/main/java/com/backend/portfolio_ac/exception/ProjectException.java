package com.backend.portfolio_ac.exception;


public class ProjectException extends RuntimeException {

  public enum Type {
    NOT_FOUND_PROJECTS,
    PROJECT_EXIST,
    ERROR_DELETE,
    ERROR_UPDATE,
    ERROR_CREATE
  }

  private final ProjectException.Type type;

  public ProjectException(String message, ProjectException.Type type) {
    super(message);
    this.type = type;
  }

  public ProjectException.Type getType() {
    return type;
  }
}
