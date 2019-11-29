#!/bin/bash

# Afganistan, Kunar
call_1='{
  "calling": "38121123456",
  "called": "9362111222333",
  "start": "2019-02-01T12:34:22.00Z",
  "duration": 450
}'

# Afganistan, Kunar
call_2='{
  "calling": "38121123456",
  "called" : "9362111333555",
  "start"  : "2019-02-04T22:00:23.00Z",
  "duration" : 300
}'

# Angola, LundaySul
call_3='{
  "calling": "38164778899",
  "called" : "244253112233",
  "start"  : "2019-01-23T00:00:00.00Z"
  "duration": 20
}'

curl \
  --verbose \
  --request POST \
  --data "${call_3}" \
  --header "Content-Type: application/json" \
  "http://localhost:8080/reference/switchboard/call"
