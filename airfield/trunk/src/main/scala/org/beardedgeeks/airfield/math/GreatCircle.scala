package org.beardedgeeks.airfield.math

import _root_.org.beardedgeeks.airfield.geo.Coordinate

/**
 * Calculates the distance between two points.
 * @author hleinone
 */
object GreatCircle {
  /**
   * In kilometers.
   */
  private val earthRadius:Double = 6372.795

  /**
   * Calculates the distance in kilometers between two points.
   * @param latS The latitude of standpoint.
   * @param lonS The longitude of standpoint.
   * @param latF The latitude of forepoint.
   * @param lonF The longitude of forepoint.
   * @return The distance in kilometers.
   */
  def distance(latS:Radian, lonS:Radian, latF:Radian, lonF:Radian):Double = {
    implicit def radian2Double(arg:Radian):Double = {
      return arg.value
    }

    def cos(arg:Double):Double = Math.cos(arg)
    def sin(arg:Double):Double = Math.sin(arg)
    def atan2(arg0:Double, arg1:Double):Double = Math.atan2(arg0, arg1)
    def pow(arg0:Double, arg1:Double):Double = Math.pow(arg0, arg1)
    def sqrt(arg:Double):Double = Math.sqrt(arg)
    
    val longitudeDifference = lonS - lonF
    val numerator = sqrt(pow(cos(latF) * sin(longitudeDifference), 2) + pow(cos(latS) * sin(latF) - sin(latS) * cos(latF) * cos(longitudeDifference), 2))
    val denominator = sin(latS) * sin(latF) + cos(latS) * cos(latF) * cos(longitudeDifference)
    val angularDistance = atan2(numerator, denominator)
    return earthRadius * angularDistance
  }
}
