package com.axonivy.connector.docker;

import com.github.dockerjava.api.model.PullResponseItem;

class PullResultCallback extends AbstractResultCallback<PullResponseItem>{

  @Override
  public void onNext(PullResponseItem pullItem) {
    builder.append(pullItem.getStatus());
  }

}
