@cls
@set CP=.
@set CP=%CP%;./ext/sqljdbc.jar

@set CP=%CP%;src
mkdir build\classes
javac -g:none -classpath %CP% -d "build/classes" src/ru/crystalservice/setquery/*.java
