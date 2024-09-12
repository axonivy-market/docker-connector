# Docker Konnektor

Container sind schlanke, isolierte Einheiten, die Anwendungen zusammen mit ihren Abhängigkeiten verpacken 
und so die Konsistenz und Portabilität über verschiedene Umgebungen hinweg sicherstellen. Docker ist eine weit 
verbreitete Plattform mit einer daemonbasierten Architektur, die umfangreiche Verwaltungsfunktionen für Container
und Ökosystem-Tools wie Docker Hub und Docker Compose bietet. 

Podman, eine Alternative zu Docker, verwaltet ebenfalls Container, kommt aber ohne einen zentralen Daemon aus und 
legt den Schwerpunkt auf Sicherheit und Einfachheit. Beide Tools können mit Docker-Container-Images umgehen und 
verfügen über ähnliche Befehlszeilenfunktionen, was den Übergang zwischen ihnen erleichtert. Während Docker auf 
einen Hintergrunddienst angewiesen ist, ermöglicht das daemonlose Design von Podman die direkte Verwaltung von Containern.

**Mit unserem Docker-Konnektor können Sie Docker-Container direkt aus einem Axon Ivy-Geschäftsprozess erstellen, starten, stoppen und entfernen.**

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
