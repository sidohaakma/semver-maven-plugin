# semver-maven-plugin

The semver-maven-plugin is used to determine the next version of a MAVEN project. Symantic versioning is used to specify the version symantics.

Check: [semver.org](https://www.semver.org) for more information

## Usage

Include the following depedency in your pom.xml

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>semver-maven-plugin</artifactId>
  <version>${semver.maven.plugin-version}</version>
</plugin>
```

The latest version of the semver-maven-plugin is:

```
<semver.maven.plugin-version>2.2.1</semver.maven.plugin-version>
```

### Run configurations

The goals that use the **maven-release-plugin** are attached to RUN_MODES:

* RELEASE
* RELEASE_RPM
* RELEASE_BRANCH

It generates the `release.properties` and then the *release:prepare*-goal can be used to make the maven release-artifact.

The goals that are **native** are attached to RUNMODES:

**Not yet implemented**

* NATIVE
* NATIVE_RPM
  
The **native**-method is developed in version: *3.0.0* to get rid of the dependency on the **maven-release-plugin**.

## Goals

* **patch**

    Create a bug-fix on your project: 0.0.x. 

* **minor**

    Create a non-breaking new feature on your project: 0.x.0.

* **major**

    Create a breaking changes in your project: x.0.0.

* **rollback**

    *not yet implemented (will be avaiable in version 3.0.0)*
    
    Rollback a patch, minor or major version. Also deletes created GIT-tags on local and remote repository.


* **cleanup-git-tags**

    *@Deprecated*
    
    Cleanup remote and local **build-**tags. Run before the build-server makes a release.

### Run the goals

The goals that use the **maven-release-plugin** are attached to RUNMODES:

* RELEASE
* RELEASE_RPM
* RELEASE_BRANCH

To run the goals please execute the following commands:

* `mvn semver:patch release:prepare`
* `mvn semver:minor release:prepare`
* `mvn semver:major release:prepare`


The goals that are **native** are attached to RUNMODES:

**Not yet implemented**

* NATIVE
* NATIVE_RPM

To run the goals please execute the following commands:

* `mvn semver:patch`
* `mvn semver:minor`
* `mvn semver:major`
* `mvn semver:rollback` 


## Build

To build the semver-maven-plugin, execute the following command:

`mvn clean package`