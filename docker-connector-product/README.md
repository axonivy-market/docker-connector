# Docker Connector

Create, start, stop, remove Docker containers with the Axon Ivy's Docker Connector directly from your business processes.

## Demo

The demo process creates and starts an additional Axon Ivy Engine in a Docker container. 
Then the process shows a HTML Dialog where you can open an additional browser tab that shows the info page of the Axon Ivy Engine.
If you proceed in the HTML Dialog, the container gets stopped and removed.

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