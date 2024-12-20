package org.poach3r

object Caps:
  def get(): String =
    """Caps Lock:\s+(on|off)""".r.findFirstIn(os.proc("xset", "-q").call().out.text()).get.split(" ").last match
      case "on" => "true"
      case "off" => "false"