package org.poach3r

object Main:
  def main(args: Array[String]): Unit =
    if args.isEmpty then System.exit(0)

    println(args(0) match
      case "GetApps" => GetApps.get()
      case "Caps" => Caps.get()
      case "Notifs" => Notifications.get(args.lift(1))
      case "ClearNotifs" => Notifications.clearOld()
    )