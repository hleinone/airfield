package org.beardedgeeks.airfield.cmd

/**
 * GOF Command/Chain-of-responsibility representing the application logic to be executed.
 * @author hleinone
 */
abstract class Command[G, R](private val before:Option[Command[Any, G]]) {
  /**
   * Starts executing the command chain.
   * @return The result of this command.
   */
  def execute():R = {
    before match {
      case None => return executeCommand(None)
      case Some(value) => return executeCommand(Some(value.execute()))
    }
  }
  
  
  /**
   * Executes this command.
   * @param result The result of the previous command in the command chain.
   * @return The result of this command.
   */
  protected def executeCommand(result:Option[G]):R
}
