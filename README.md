# interop-common

This is a common project package as a library for all interop projects such as interop-domain, interop-dfsp-gateway and interop-spsp-clientproxy. This contains common classes and common properties files that are used by interop projects and properties. 

Referenced in the projects, specified by the release version 1.x.x, groupId = com.l1p.interop and artifactId = interop-common


Contents:

- [Deployment](#deployment)
- [Configuration](#configuration)
- [API](#api)
- [Logging](#logging)
- [Tests](#tests)

## Deployment

Project is built using Maven and uses Circle for Continous Integration.

This is released as a jar file (upon the tests succeeding) to artifactory from which the required versions are referenced and used by base level projects or the interop-domain project

## Configuration

[pom.xml](./pom.xml) and [circle.yml](./circle.yml) can be found in the repo

The project contains configuration of the common HTTP connector and the host/port details. Also contains metrics properties, related classes and other common classes used across the interop services.

## API

This repo does not contain an API.

## Logging

This project by itself doesn't generate any logs but when referenced, some of the classes do generate logs.

Server path to logs is: <mule_home>/logs/*.log

Currently the logs are operational and include information such as TraceID and other details related to the calls or transactions such as path, method used, header information and payer/payee details.

## Tests

There are java unit test for the interop-common project that test JSON transformations and Ledger URL Mappings.

Unit Tests are run via Maven as part of the 'mvn clean package' command, but unit tests can also be skipped by running 'mvn clean package -DskipTests=true'
