package org.beardedgeeks.airfield.cmd

import _root_.org.slf4j.LoggerFactory

/**
 * Represents command-line argument --help. Doesn't have a preceding command.
 * @author hleinone
 */
class HelpCommand() extends Command[Unit, Unit](None) {
  private final val logger = LoggerFactory.getLogger(getClass)

  protected def executeCommand(result:Option[Unit]):Unit = {
    logger.debug("Displaying help text")
    println("Usage: FILE [--printRouteLength], [--findShortestRoute, [--startingPoint=XXXX]]")
    println("--printRouteLength\tPrint the length of the route which visits each of the airfields in the file in that order.")
    println("--findShortestRoute\tPrint the shortest route for visiting each of the defined airfields, as many times as mentioned in the file. Two sequent visits to an airfield require that another airfield has been visited in-between.")
    println("--startingPoint=XXXX\tDefines the starting point for which the shortest route should start.")
  }
}
