#!/usr/bin/env bash

result=$(xset -q | sed -n 's/^.*Caps Lock:\s*\(\S*\).*$/\1/p')

if [[ $result == "on" ]]; then
 echo "true"
else
  echo "false"
fi;
