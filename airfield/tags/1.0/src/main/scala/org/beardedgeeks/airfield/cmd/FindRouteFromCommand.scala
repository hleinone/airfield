package org.beardedgeeks.airfield.cmd

import java.text.DecimalFormat
import org.slf4j.LoggerFactory
import scala.collection.jcl.TreeMap
import scala.collection.mutable.{Buffer, ListBuffer}
import org.beardedgeeks.airfield.math.{DecimalDegree, GreatCircle, Radian, RouteCalculator}
import org.beardedgeeks.airfield.model.{Airfield, AirfieldFactory}

/**
 * Represents command-line argument --startingPoint=XXXX. Doesn't have a preceding command.
 * @author hannu
 */
class FindRouteFromCommand(val from:String) extends Command[Unit, Airfield](None) {
  private val logger = LoggerFactory.getLogger(getClass)

  protected def executeCommand(result:Option[Unit]):Airfield = {
    logger.debug("Finding route from " + from)
    return AirfieldFactory.get(from)
  }
}
