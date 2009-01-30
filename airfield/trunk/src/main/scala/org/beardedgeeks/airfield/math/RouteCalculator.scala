package org.beardedgeeks.airfield.math

import java.text.DecimalFormat
import org.slf4j.{Logger, LoggerFactory}
import org.beardedgeeks.airfield.geo.Coordinate
import scala.collection.mutable.Buffer

/**
 * Calculates the distance of a route.
 * @author hleinone
 */
object RouteCalculator {
  private val logger = LoggerFactory.getLogger("org.beardedgeeks.airfield.math.RouteCalculator")
  private val decimalFormat = new DecimalFormat("0.00")

  /**
   * Calculates the distance through multiple Coordinates.
   * @param route The route.
   * @return The distance.
   */
  def routeLength(route:Buffer[_ <: Coordinate]):Double = {
    implicit def decimalDegree2Radian(arg:DecimalDegree):Radian = {
      return new Radian(arg.value * Math.Pi / 180.0)
    }

    var totalDistance = 0.0
    var i = 0
    while(i + 1 < route.length) {
      val origin = route(i)
      val destination = route(i + 1)
      val distance = GreatCircle.distance(origin.latitude, origin.longitude, destination.latitude, destination.longitude)
      totalDistance += distance
      i += 1
    }
    logger.debug(route + " = " + decimalFormat.format(totalDistance) + " km")
    return totalDistance
  }
}
