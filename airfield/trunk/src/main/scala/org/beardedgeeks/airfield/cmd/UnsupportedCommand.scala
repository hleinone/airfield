package org.beardedgeeks.airfield.cmd

import org.slf4j.LoggerFactory

/**
 * Represents an unsupported command-line argument. Doesn't have a preceding command.
 * @author hleinone
 */
class UnsupportedCommand(val cause:String) extends Command[Unit, Unit](None) {
  private final val logger = LoggerFactory.getLogger(getClass)

  protected def executeCommand(result:Option[Unit]):Unit = {
    logger.warn(cause)
    println(cause)
    println("Try '--help' for more information.")
  }
}
