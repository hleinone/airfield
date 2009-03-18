package org.beardedgeeks.airfield.math

/**
 * Represents a value in radian.
 * @author hleinone
 */
case class Radian(val value:Double) {
  override def toString():String = {
    value + " rad"
  }
}
