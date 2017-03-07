# interop-common

This is a common project package as a library for all interop projects such as interop-dfsp-gateway and interop-spsp-clientproxy. This contains common classes and common properties files that are used by interop projects and properties. 

Referenced in the projects, specified by the release version 1.x.x, groupId = com.l1p.interop and artifactId = interop-common


Contents:

- [Deployment](#deployment)
- [Configuration](#configuration)
- [API](#api)
- [Logging](#logging)
- [Tests](#tests)

## Deployment

Project is built using Maven and uses Circle for Continous Integration.

(How do you run this code?)

(Example for NPM-published services--

Installation:

1. Install [Node.js and npm](https://nodejs.org/en/)

2. Configure your npm instance to use the LevelOneProject repository.

    See [Docs/Artifactory/NPM Repos](https://github.com/LevelOneProject/Docs/blob/master/Artifactory/npm_repos.md) for detailed instructions.

3. Install the `(package name)` package.

        npm install (package name)

Running the server locally:

    npm start

--end example)

## Configuration

pom.xml and circle.yml can be found at interop-common repo

(Explanation of important config parameters)


## API

(If the API is short, summarize it here; otherwise, link a separate page with the API docs or explain how to build the API docs from the repo.)

## Logging

Sever path to logs is: /opt/mule/mule-dfsp1/logs

(Explain important things about what gets logged or how to interpret the logs. Use subheaders if necessary.)

## Tests

There are java unit test for the interop-common project that test JSON transformations and Ledger URL Mappings.

Unit Tests are run via Maven as part of the 'mvn clean package' command, but unit tests can also be skipped by running 'mvn clean package -DskipTests=true'
