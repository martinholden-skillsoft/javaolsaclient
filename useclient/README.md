# Use Client
This ANT script will generate simple test client to use the skilsosft-olsa.jar file

To use:

## SET OLSA INSTANCE INFO
In the \config\olsa_user.properties set the correct values for the OLSA Site you want to create the class for.

## BUILD
~~~~
ant
~~~~

## USE CLIENT
This will call the OLSA site in the olsa_user.properties file and request UM_GetUserDetailsExtended for the username specified in config\ctk.properties, these will then be output to the screen

~~~~
ant testgetuserclient
~~~~
