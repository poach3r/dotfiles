package org.poach3r

import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Decoder, Encoder}
import io.circe.syntax.*
import os.Path
import io.circe.parser.*

import scala.annotation.tailrec

object GetApps:
  private case class App(name: String, amt: Array[Int])
  private implicit val appEncoder: Encoder[App] = deriveEncoder[App]
  private lazy val theme: IndexedSeq[Path] = os.list(os.root/"usr"/"share"/"icons"/"Papirus"/"32x32"/"apps")

  def get(): String =
    val cache: Map[String, String] = decode[Map[String, String]](os.read(os.home/".config"/"eww"/"data"/"icon-cache.json")) match
      case Left(error) => throw new IllegalArgumentException(s"Failed to find cache: $error")
      case Right(apps) => apps

    val apps = findIcons(
      os.proc("nu", "-c", "swaymsg -t get_tree | from json | get nodes | select 1 | get nodes | each {|x| $x | get nodes | each {|y| $y | get app_id} | to text} | to text").call().out.text().strip().split("\n").groupBy(l => l).map(t => App(t._1, Array.range(0, t._2.length))).toArray,
      cache)

    if cache != apps._2 then
      os.remove(os.home/".config"/"eww"/"data"/"icon-cache.json")
      os.write(os.home/".config"/"eww"/"data"/"icon-cache.json", apps._2.asJson.toString)

    apps._1.asJson.noSpaces

  @tailrec
  private def findIcons(apps: Array[App], cache: Map[String, String], index: Int = 0): (Array[App], Map[String, String]) =
    if index == apps.length then
      (apps, cache)
    else
      val icon = findIcon(apps(index), cache)
      findIcons(apps.updated(index, icon._1), icon._2, index + 1)

  private def findIcon(app: App, cache: Map[String, String]): (App, Map[String, String]) =
    try
      return (app.copy(cache(app.name), app.amt), cache)
    catch
      case _: NoSuchElementException => () // app wasnt found in cache

    val icon = theme.map { icon =>
      (calculateDistance(app.name, icon.baseName), icon.toString)
    }.min._2 // storing this many variables is probably part of why this is so slow

    (App(icon, app.amt), cache.updated(app.name, icon))


  private def calculateDistance(str1: String, str2: String): Int = {
    val m = str1.length
    val n = str2.length
    val dp = Array.ofDim[Int](m + 1, n + 1)

    (0 until m + 1).foreach(i => dp(i)(0) = i)
    (0 until n + 1).foreach(j => dp(0)(j) = j)

    for
      i <- 1 to m
      j <- 1 to n
    do
      if str1(i - 1) == str2(j - 1) then
        dp(i)(j) = dp(i - 1)(j - 1)
      else
        val substitution = dp(i - 1)(j - 1) + 1
        val insertion = dp(i)(j - 1) + 1
        val deletion = dp(i - 1)(j) + 1

        dp(i)(j) = Array(substitution, insertion, deletion).min

    dp(m)(n)
  }