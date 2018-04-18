# Building the Client
This ANT build script will generate a jar file that contains the Axis/WSS4J based OLSA Client

To use:

## SET OLSA INSTANCE INFO
In the \config\olsa_user.properties set the correct values for the OLSA Site you want to create the class for.

## SET THE NAMESPACE
In the build.properties set the project.namespacepath, this is the path for the olsa client. During build the namespace is created from the
path by replacing the / with .

## BUILD
~~~~
ant
~~~~

The JAR will be copied to the ..\outlib folder
