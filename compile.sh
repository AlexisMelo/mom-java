#!/bin/bash 

clean() {
    echo "-> Cleaning old class file..."
    rm -rf class/ir/*
}

compile_server() {
    echo "-> Compiling server..."
    javac -cp class:lib/* -sourcepath src src/ir/mom/server/view/MomService.java -d class/
}

compile_client() {
    echo "-> Compiling client..."
    javac -cp class:lib/* -sourcepath src src/ir/mom/client/MomClientTerminal.java -d class/

}

case $1 in
    "server") compile_server ;;
    "client") compile_client ;;
    "") clean; compile_server; compile_client;;
esac
