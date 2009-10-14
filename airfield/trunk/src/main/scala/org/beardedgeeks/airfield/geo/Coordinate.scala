package org.beardedgeeks.airfield.geo

import _root_.org.beardedgeeks.airfield.math.DecimalDegree

/**
 * Represents a place on the surface of Earth.
 * @author hleinone
 */
trait Coordinate {
  val latitude:DecimalDegree
  val longitude:DecimalDegree
  
  override def toString():String = {
    return latitude + ", " + longitude
  }
}
