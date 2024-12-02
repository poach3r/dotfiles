let appNames = bspc query --nodes --node .tiled | xargs -I {} bspc query --tree --node {} | each {from json | get client.className}

let appIcons = $appNames | each {|e| ls /usr/share/icons/Papirus-Dark/32x32/apps/ | find $e } | each {get name}

let appIconExists = $appIcons | each { is-empty }

let finalAppIcons = $appIconExists | enumerate | each {|x|
  if (($appNames | get $x.index) == "jetbrains-idea") {
    "/usr/share/icons/Papirus-Dark/32x32/apps/intellij-idea-ultimate-edition.svg"
  } else if (($appNames | get $x.index) == "Nemo") {
    "/usr/share/icons/Papirus-Dark/32x32/apps/nemo.svg"
  } else if (($appNames | get $x.index) == "Alacritty") {
    "/usr/share/icons/Papirus-Dark/32x32/apps/terminal.svg"
  } else if (($appNames | get $x.index) == "lite-xl") {
    "/usr/share/icons/Papirus-Dark/32x32/apps/lite.svg"
  } else if (($appNames | get $x.index) == "floorp") {
    "/usr/share/icons/Papirus-Dark/32x32/apps/browser.svg"
  } else if $x.item == false {
    $appIcons | get $x.index | get 0 | ansi strip
  } else {
    "n/a"
  }
}

echo $finalAppIcons | to json | tr -d '\n' | tr -d ' '
