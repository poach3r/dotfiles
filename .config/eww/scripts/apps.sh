#!/usr/bin/env bash

bspc subscribe report | { 
  while read -r event; do
    echo $(nu /home/poacher/.config/eww/scripts/apps.nu)
  done
}
