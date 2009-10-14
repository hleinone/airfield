package org.beardedgeeks.airfield.cmd

import _root_.java.text.DecimalFormat
import _root_.org.slf4j.LoggerFactory
import _root_.scala.collection.jcl.TreeMap
import _root_.scala.collection.mutable.{Buffer, ListBuffer}
import _root_.org.beardedgeeks.airfield.math.{DecimalDegree, GreatCircle, Radian, RouteCalculator}
import _root_.org.beardedgeeks.airfield.model.{Airfield, AirfieldFactory}

/**
 * Represents command-line argument --startingPoint=XXXX. Doesn't have a preceding command.
 * @author hleinone
 */
class FindRouteFromCommand(val from:String) extends Command[Unit, Airfield](None) {
  private final val logger = LoggerFactory.getLogger(getClass)

  protected def executeCommand(result:Option[Unit]):Airfield = {
    logger.debug("Finding route from " + from)
    return AirfieldFactory.get(from)
  }
}
