#!/bin/bash 

run_server() {
    echo "-> running server..."
    java -cp class:lib/* ir.mom.server.view.MomService $@
}

run_client() {
    echo "-> running client..."
    java -cp class:lib/* ir.mom.client.MomClientTerminal $@
}

case $1 in
    "server") run_server $2;;
    "client") run_client $2;;
    "") run_server; run_client;;
esac
