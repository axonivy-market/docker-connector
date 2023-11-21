package com.axonivy.connector.docker;

import java.nio.charset.StandardCharsets;

import com.github.dockerjava.api.model.Frame;

class ExecuteResultCallback extends AbstractResultCallback<Frame> {

  @Override
  public synchronized void onNext(Frame frame) {
    builder.append(new String(frame.getPayload(), StandardCharsets.UTF_8));
  }
}