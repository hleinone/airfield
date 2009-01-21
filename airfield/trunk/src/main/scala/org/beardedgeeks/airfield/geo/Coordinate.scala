package org.beardedgeeks.airfield.geo

import org.beardedgeeks.airfield.math.DecimalDegree

/**
 * Represents a place on the surface of Earth.
 * @author hannu
 */
trait Coordinate {
  val latitude:DecimalDegree
  val longitude:DecimalDegree
  
  override def toString():String = {
    return latitude + ", " + longitude
  }
}
