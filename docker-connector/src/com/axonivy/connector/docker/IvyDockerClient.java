package com.axonivy.connector.docker;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.NoHttpResponseException;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;

import ch.ivyteam.ivy.environment.Ivy;

public class IvyDockerClient {

  private static final IvyDockerClient INSTANCE = new IvyDockerClient();
  private DockerClient client;

  private IvyDockerClient() {
    try {
      client = createDockerClient();
    } catch (Exception ex) {
      Ivy.log().error("Could not create docker client", ex);
    }
  }

  public static IvyDockerClient instance() {
    return INSTANCE;
  }

  private static DockerClient createDockerClient() {
    var dockerUrl = Ivy.var().get("Docker.URL");
    var config = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost(dockerUrl)
            .build();

    var httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    var docker = DockerClientImpl.getInstance(config, httpClient);
    return docker;
  }

  public CreateContainerCmd createContainerCmd(String image) {
    var resp = client.listImagesCmd().withFilter("reference", List.of(image)).exec();
    if (resp.isEmpty()) {
      var result = new PullResultCallback();
      client.pullImageCmd(image).exec(result);
      result.await();
    }
    return client.createContainerCmd(image);
  }

  public ListContainersCmd listContainersCmd() {
    return client.listContainersCmd();
  }

  public void startContainer(String id) {
    retry(() -> client.startContainerCmd(id).exec());
    Ivy.log().info("Container "+id+" started");
  }

  public void stopContainer(String id) {
    retry(() -> client.stopContainerCmd(id).exec());
    Ivy.log().info("Container "+id+" stopped");
  }

  public void removeContainer(String id) {
    retry(() -> client.removeContainerCmd(id).exec());
    Ivy.log().info("Container "+id+" removed");
  }

  public void executeInContainer(String id, String... command) {
    var cmd = client.execCreateCmd(id)
        .withCmd(command)
        .withAttachStderr(true)
        .withAttachStdout(true)
        .withTty(true)
        .exec();
    var res = new ExecuteResultCallback();
    client.execStartCmd(cmd.getId())
        .withDetach(false)
        .withTty(true)
        .exec(res);
    var cmdStr = Arrays.stream(command).collect(Collectors.joining(" "));
    Ivy.log().info("Execute '"+cmdStr+"' in container "+id+":\n" + res.await());
  }

  public String createNetwork(String name) {
    return client.createNetworkCmd().withName(name).exec().getId();
  }

  public Optional<Network> findNetwork(String name) {
    return client.listNetworksCmd().withFilter("name", List.of(name)).exec().stream().findAny();
  }

  public void connectContainerToNetwork(String id, String networkId) {
    client.connectToNetworkCmd().withContainerId(id).withNetworkId(networkId).exec();
  }

  public void copyToContainer(Path hostPath, String containerId, String containerPath) {
    client.copyArchiveToContainerCmd(containerId)
          .withHostResource(hostPath.toString())
          .withRemotePath(containerPath)
          .exec();
  }

  private void retry(Runnable r) {
    int retry = 0;
    while (true) {
      try {
        r.run();
        return;
      } catch (RuntimeException ex) {
        if (! (ex.getCause() instanceof NoHttpResponseException)) {
          throw ex;
        }
        retry++;
        if (retry >= 3) {
          throw ex;
        }
      }
    }
  }
}
