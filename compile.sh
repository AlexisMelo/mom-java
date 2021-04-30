#!/bin/bash 

echo "-> Compiling server..."
javac -cp class -sourcepath src src/ir/mom/server/view/MomService.java -d class/
echo "-> Compiling client..."
javac -cp class -sourcepath src src/ir/mom/client/MomClientTerminal.java -d class/