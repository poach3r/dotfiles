#!/usr/bin/env bash

tiramisu -s -o '{"icon": "#icon", "summary": "#summary", "body": "#body"}' | { 
  while read -r event; do
    if [[ $(eww get dndPressed) == "false" ]] then
      eww open notifications -c ~/.config/eww/
      eww update tiramisu="$event";
      sleep 4;
      eww close notifications -c ~/.config/eww/
    fi
  done
}
