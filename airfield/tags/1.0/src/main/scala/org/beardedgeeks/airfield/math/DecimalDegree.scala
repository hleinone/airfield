package org.beardedgeeks.airfield.math

/**
 * Represents a value in decimal degree.
 * @author hleinone
 */
class DecimalDegree(var value:Double) {
  override def toString():String = {
    value + " deg"
  }
}
