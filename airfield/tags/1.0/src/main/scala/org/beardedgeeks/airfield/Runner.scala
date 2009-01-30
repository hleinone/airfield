package org.beardedgeeks.airfield

import org.beardedgeeks.airfield.cmd.{CommandFactory, InsufficentParameterException, UnsupportedCommand}
import org.beardedgeeks.airfield.model.{InsufficentAirfieldDataException, NoSuchAirfieldException}
import org.slf4j.LoggerFactory

/**
 * The main class for running the application.
 * @author hleinone
 */
object Runner extends Application {
  private val logger = LoggerFactory.getLogger(getClass)
  /**
   * Starts executing the application.
   * @param args The command-line arguments.
   */
  override def main(args:Array[String]) {
    val command = CommandFactory.get(args)
    logger.debug("Executing " + command);
    try {
      command.execute()
    } catch {
      case e:Exception => {
        logger.error("Fatal error.", e)
        println(e.getMessage)
      }
    }
  }
}
