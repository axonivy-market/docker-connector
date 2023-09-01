package com.axonivy.connector.docker;

import java.io.Closeable;
import java.io.IOException;

import com.github.dockerjava.api.async.ResultCallback;

abstract class AbstractResultCallback<T> implements ResultCallback<T> {

  enum State {
    CREATED,
    RUNNING,
    ERROR,
    COMPLETE,

  }
  private AbstractResultCallback.State state = State.CREATED;
  private Closeable closeable;
  protected StringBuilder builder = new StringBuilder();
  private Throwable error = null;

  @Override
  public synchronized void close() {
    try {
      this.closeable.close();
    } catch (IOException ex) {
      error = ex;
    }
  }

  @Override
  public synchronized void onStart(Closeable c) {
    this.closeable = c;
    this.state = State.RUNNING;
  }

  @Override
  public synchronized void onError(Throwable e) {
    this.state = State.ERROR;
    this.error = e;
    close();
    notifyAll();
  }

  @Override
  public synchronized void onComplete() {
    this.state = State.COMPLETE;
    close();
    notifyAll();
  }

  public synchronized String await() {
    while (state == State.RUNNING || state == State.CREATED) {
      try {
        wait();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    }
    if (error != null) {
      if (error instanceof RuntimeException re) {
        throw re;
      }
      throw new RuntimeException(error);
    }
    return builder.toString();
  }
}