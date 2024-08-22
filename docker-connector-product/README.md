# Docker Connector

Containers are lightweight, isolated units that package applications with their dependencies, ensuring consistency and portability across different environments. Docker is a widely-used platform with a daemon-based architecture that provides extensive container management features and ecosystem tools like Docker Hub and Docker Compose. Podman, an alternative to Docker, also manages containers but operates without a central daemon, focusing on security and simplicity. Both tools can handle Docker container images and share similar command-line functionalities, making transitions between them easier. While Docker relies on a background service, Podman’s daemonless design allows for direct management of containers.

**With our docker connector you can create, start, stop and remove docker containers in Docker Desktop or in Podman directly from an Axon Ivy business process.**
 
 ## Demo

The prerequisite for the demo process is that a Docker daemon is running on your computer. This can be achieved using tools like Docker Desktop or Podman. In the following example, we used Podman.

In the first step  we show you how to create a container for the Axon Ivy engine by using the Axon Ivy engine image you find at docker hub: https://hub.docker.com/r/axonivy/axonivy-engine.

![image](images/dockerdemo1.png)

Next, the container is started - and with it an application on localhost with which an Axon Ivy Engine is started:

![image](images/dockerdemo2.png)

The container created in Podman:

![image](images/dockerdemo3.png)

An UI is now opened in the process, which gives you the option of stopping and deleting this container again by clicking "Proceed".

![image](images/dockerdemo4.png)

The container is now removed from Podman:

![image](images/dockerdemo5.png)

## Setup

To setup the Docker Connnector, add the following variable to your Axon Ivy Project:
```
@variables.yaml@
```

To determine the URL whether to use npipe (Named Pipes) or TCP to connect to your Docker daemon, you can use different commands depending on your operating system and Docker setup. Here's how to check for Docker, Docker Desktop, and Podman on Windows, macOS, and Linux:
### Windows:
1. Docker Desktop: Run this command in PowerShell:
    ```powershell
    docker context inspect default
    ```
    Look for the "Endpoints" section. If you see "npipe://" it's using Named Pipes. If you see "tcp://", it's using TCP.
2. Podman: Podman on Windows typically uses TCP. Check the configuration file:
    ```
    cat $env:USERPROFILE\.config\containers\podman\machine\qemu\podman-machine-default.json
    ```
### macOS:
1. Docker Desktop: Run this command in Terminal:
    ```
    docker context inspect default
    ```
    Look for the "Endpoints" section. It should show "unix:///var/run/docker.sock" for socket communication.
2. Podman: Podman on macOS typically uses TCP. Check the machine's configuration:
    ```
    podman machine inspect
    ```
    Look for the "ConnectionInfo.URI" field.
### Linux:
1. Docker Desktop: Check the Docker daemon configuration:
    ```
    sudo cat /etc/docker/daemon.json
    ```
    Look for the "hosts" field. If not found, Docker is likely using the default Unix socket. You can also check the systemd service file:
    ```
    sudo systemctl cat docker.service
    ```
    Look for the "ExecStart" line to see if any TCP options are specified.
2. Podman: Podman on Linux typically uses a Unix socket by default. Check the configuration:
    ```
    cat /etc/containers/containers.conf
    ```
### For Docker (all platforms):
```
docker info --format '{{.Host}}'
```
This will show you the current connection method being used.


Remember that the actual configuration may vary depending on your specific setup and any custom configurations you might have applied.