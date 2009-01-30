package org.beardedgeeks.airfield.model

import org.beardedgeeks.airfield.geo.Coordinate
import org.beardedgeeks.airfield.math.DecimalDegree

/**
 * Represents an airfield.
 * @author hleinone
 */
class Airfield(val mnemonic:String, val name:String, val latitude:DecimalDegree, val longitude:DecimalDegree) extends Coordinate {
  override def toString():String = {
    return mnemonic
  }
}
