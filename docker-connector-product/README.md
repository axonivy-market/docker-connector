# Docker Connector

Docker is a platform that automates the deployment of applications inside lightweight, portable containers. A container is an isolated environment that includes an application and all its dependencies, ensuring consistent behavior across different environments.

 With our connector you can create, start, stop and remove docker containers directly from an Axon Ivy business process.
 
 ## Demo

The prerequisite for the demo process is that a Docker daemon is running on your computer: https://docs.docker.com/get-docker/

In the demo process we show you how to start the Axon Ivy Engine in a Docker container.

Then the process shows a HTML Dialog where you can open an additional browser tab that shows the info page of the Axon Ivy Engine.
If you proceed in the HTML Dialog, the container gets stopped and removed.

## Setup

To setup the Docker Connnector, add the following variable to your Axon Ivy Project:
```
@variables.yaml@
```
