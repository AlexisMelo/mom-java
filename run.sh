#!/bin/bash 

run_server() {
    echo "-> running server..."
    java -cp class:lib/* ir.mom.server.view.MomService
}

run_client() {
    echo "-> running client..."
    java -cp class:lib/* ir.mom.client.MomClientTerminal
}

case $1 in
    "server") run_server ;;
    "client") run_client ;;
    "") run_server; run_client;;
esac
