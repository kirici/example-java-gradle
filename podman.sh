#!/usr/bin/env sh
set -uxo

IMAGE='localhost/kirici/example/java-gradle'

podman rmi "${IMAGE}":n2
podman tag "${IMAGE}":n1 "${IMAGE}":n2
podman tag "${IMAGE}":latest "${IMAGE}":n1
podman rmi "${IMAGE}":latest
podman build -t "${IMAGE}":latest -f ./debug.containerfile
podman run -it --rm --name kotgrad "${IMAGE}":latest bash
