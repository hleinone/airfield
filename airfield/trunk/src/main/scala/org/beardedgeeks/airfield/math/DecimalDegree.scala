package org.beardedgeeks.airfield.math

/**
 * Represents a value in decimal degree.
 * @author hleinone
 */
case class DecimalDegree(val value:Double) {
  override def toString():String = {
    value + " deg"
  }
}
