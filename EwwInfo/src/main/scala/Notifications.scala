package org.poach3r

import os.Path

object Notifications:
  lazy val dataPath: Path = os.home/".config"/"eww"/"data"
  lazy val oldNotifsPath: Path = dataPath/"old_notifications"
  lazy val newNotifsPath: Path = dataPath/"notifications"
    
  def get(notif: Option[String]): String =
    val size = (os.list(newNotifsPath).length + os.list(oldNotifsPath).length).toString
    os.write(newNotifsPath/size, notif.getOrElse("{}"))
    println(s"[[${getExistingNotifs().mkString(", ")}], [${getExistingNotifs(dataPath/"old_notifications").mkString(", ")}]]")

    Thread.sleep(5000)
    os.move(newNotifsPath/size, oldNotifsPath/size)
    println(s"[[${getExistingNotifs().mkString(", ")}], [${getExistingNotifs(dataPath/"old_notifications").mkString(", ")}]]")

    ""

  private def getExistingNotifs(path: Path = newNotifsPath): IndexedSeq[String] =
    os.list(path).sortBy(-_.baseName.toInt).map(os.read(_))
    
  def clearOld(): String = 
    os.list(oldNotifsPath).foreach(os.remove(_))
    os.list(newNotifsPath).foreach(os.remove(_))
    
    ""
