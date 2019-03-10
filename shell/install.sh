#!/bin/sh
echo Installing NeRPN...
if [ -d ${HOME}/bin ]; then
    cp NeRPN.jar ${HOME}/bin
    echo "java -jar ${HOME}/bin/NeRPN.jar &" >${HOME}/bin/nerpn
    chmod +x ${HOME}/bin/nerpn
    echo Done--you can run \'nerpn\' to start the program!
else
   echo You have no ${HOME}/bin directory!
fi

