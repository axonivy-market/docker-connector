# Docker Connector

Create, start, stop, remove Docker containers with the Axon Ivy's Docker Connector directly from your business processes.

## Demo

The demo process creates and starts an additional Axon Ivy Engine in a Docker container. 
Then the process shows a HTML Dialog where you can open an additional browser tab that shows the info page of the Axon Ivy Engine.
If you proceed the HTML Dialog then the container gets stopped and removed.

## Setup

To setup the Docker Connnector, add the following variable to your Axon Ivy Project:
```
@variables.yaml@
```
