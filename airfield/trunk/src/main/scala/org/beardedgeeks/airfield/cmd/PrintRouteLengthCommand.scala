package org.beardedgeeks.airfield.cmd

import java.text.DecimalFormat
import org.slf4j.LoggerFactory
import scala.collection.mutable.{Buffer, ListBuffer}
import org.beardedgeeks.airfield.io.AirfieldFileReader
import org.beardedgeeks.airfield.math.{DecimalDegree, GreatCircle, Radian, RouteCalculator}
import org.beardedgeeks.airfield.model.Airfield


/**
 * Represents command-line argument --printRouteLength. Can take command returning 
 * Buffer[Airfield] as the preceding command.
 * @author hleinone
 */
class PrintRouteLengthCommand(private val before:Option[Command[Any, Buffer[Airfield]]], val fileName:String) extends Command[Buffer[Airfield], Unit](before) {
  private final val logger = LoggerFactory.getLogger(getClass)
  private final val decimalFormat = new DecimalFormat("0.00")

  protected def executeCommand(result:Option[Buffer[Airfield]]):Unit = {
    val airfields:Buffer[Airfield] = result match {
      case Some(value) => value
      case None => AirfieldFileReader.readFile(fileName)
    }
    
    print("Route: ")
    var i:Int = 0
    while(i < airfields.size) {
      print(airfields(i).mnemonic)
      if(i != airfields.size - 1)
        print(" -> ")
      i += 1
    }
    println()
    
    print("Length: ")
    println(decimalFormat.format(RouteCalculator.routeLength(airfields)) + " km")
  }
}
