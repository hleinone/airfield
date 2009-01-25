package org.beardedgeeks.airfield

import org.beardedgeeks.airfield.cmd.{CommandFactory, InsufficentParameterException, UnsupportedCommand}
import org.beardedgeeks.airfield.model.{InsufficentAirfieldDataException, NoSuchAirfieldException}

/**
 * The main class for running the application.
 * @author hannu
 */
object Runner extends Application {
  /**
   * Starts executing the application.
   * @param args The command-line arguments.
   */
  override def main(args:Array[String]) {
    val command = CommandFactory.get(args)
    try {
      command.execute()
    } catch {
      case e:Exception => {
        new UnsupportedCommand(e.getMessage).execute()
      }
    }
  }
}
