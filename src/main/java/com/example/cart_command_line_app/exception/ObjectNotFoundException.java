package com.example.cart_command_line_app.exception;

import java.util.NoSuchElementException;

public class ObjectNotFoundException extends NoSuchElementException {
  public ObjectNotFoundException(String message) {
    super(message);
  }
}
