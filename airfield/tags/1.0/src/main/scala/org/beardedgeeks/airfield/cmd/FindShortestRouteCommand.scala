package org.beardedgeeks.airfield.cmd

import java.text.DecimalFormat
import org.slf4j.LoggerFactory
import scala.collection.jcl.TreeMap
import scala.collection.mutable.{Buffer, ListBuffer}
import org.beardedgeeks.airfield.math.{DecimalDegree, GreatCircle, Radian, RouteCalculator}
import org.beardedgeeks.airfield.model.Airfield

/**
 * Represents command-line argument --findShortestRoute. Can take command returning an 
 * Airfield object as the preceding command.
 * @author hleinone
 */
class FindShortestRouteCommand(private val before:Option[Command[Any, Airfield]], val airfields:Buffer[Airfield]) extends Command[Airfield, Buffer[Airfield]](before) {
  private final val logger = LoggerFactory.getLogger(getClass)
  private final val decimalFormat = new DecimalFormat("0.00")
  private final val routes = new ListBuffer[Buffer[Airfield]]()

  protected def executeCommand(result:Option[Airfield]):Buffer[Airfield] = {
    def findAllRoutes(current:Airfield, route:Buffer[Airfield], left:Buffer[Airfield]):Unit = {
      left.remove(left.findIndexOf(_ == current))
      route.append(current)
      for(next <- left) {
        if(next != current)
          findAllRoutes(next, route.clone, left.clone)
      }
      if(left.size == 0 && !routes.contains(route)) {
        routes.append(route)
      }
    }

    result match {
      case Some(value) => {
        if(airfields.contains(value)) {
          findAllRoutes(value, new ListBuffer[Airfield](), airfields.clone)
        } else {
          throw new InsufficentParameterException("Starting point " + value + " not in airfields " + airfields + ".")
        }
      }
      case None => for(start <- airfields) {
        findAllRoutes(start, new ListBuffer[Airfield](), airfields.clone)
      }
    }
        
    var shortestRoute:Buffer[Airfield] = null
    var shortestRouteLength = Double.MaxValue
    
    for(route <- routes) {
      val length = RouteCalculator.routeLength(route)
      if(length < shortestRouteLength) {
        shortestRoute = route
        shortestRouteLength = length
      }
    }
    
    logger.debug("Shortest route: " + shortestRoute)

    print("Shortest route: ")
    var i:Int = 0
    while(i < shortestRoute.size) {
      print(shortestRoute(i).mnemonic)
      if(i != shortestRoute.size - 1)
        print(" -> ")
      i += 1
    }
    println()
    
    return shortestRoute
  }
}
