#!/bin/bash
TOKEN=`curl -v --silent -uanonymous:none http://localhost:8080/ 2>&1 | grep x-auth-token |sed 's/< //g'`
DATA=xy_edsakjdlqediaejl$1
echo $TOKEN
sleep 3
RESULT=`curl -v 127.0.0.1:8080/$DATA -H "$TOKEN" 2>&1 | grep \"xy_ | sed 's/{"username":"anonymous","sessId":"//g' |sed 's/"}//g'`

echo "$RESULT = $DATA"
