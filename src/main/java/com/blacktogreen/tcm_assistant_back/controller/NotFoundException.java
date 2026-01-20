package com.blacktogreen.tcm_assistant_back.controller;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
