package org.beardedgeeks.airfield.cmd

import _root_.java.io.FileNotFoundException
import _root_.java.lang.reflect.{ParameterizedType, Type, TypeVariable}
import _root_.org.slf4j.LoggerFactory
import _root_.scala.collection.mutable.Buffer
import _root_.org.beardedgeeks.airfield.io.AirfieldFileReader
import _root_.org.beardedgeeks.airfield.model.{Airfield, InsufficentAirfieldDataException}
import _root_.scala.io.Source

/**
 * GOF Factory Method for returning the command from command-line arguments.
 * @author hleinone
 */
object CommandFactory {
  private val logger = LoggerFactory.getLogger(getClass)

  /**
   * Returns a command representing the command-line arguments.
   * @param args The command-line arguments.
   * @return The command.
   */
  def get(args:Array[String]):Command[_, _] = {
    /**
     * The generics can't know which type of command is required we'll have to check it via reflection.
     * @param command The precending command.
     * @param required The required return type for the following command.
     * @return An optional UnsupportedCommand.
     */
    def reflectGenericCommandType(command:Option[Command[_, _]], required:Class[_]):Option[Command[_, _]] = {
      if(!command.isEmpty) {
        val method = command.get.getClass.getMethod("executeCommand", Array(classOf[Option[_]]):_*)
        var returnType = method.getGenericReturnType()
        if(returnType.isInstanceOf[ParameterizedType])
          returnType = returnType.asInstanceOf[ParameterizedType].getRawType()
        if(returnType != required) {
          return Some(new UnsupportedCommand("Invalid chaining commands! required: " + required + " got: " + returnType))
        }
      }
      return None
    }
    
    if(args.length == 0)
      return new UnsupportedCommand("Please specify a command")

    if(args.length >= 1 && args(0) == "--help")
      return new HelpCommand()
      
    try {
      Source.fromFile(args(0), "UTF-8")
    } catch {
      case e:FileNotFoundException => return new UnsupportedCommand("File not found: " + args(0))
    }
    
    val commands = args.slice(1, args.length)
    
    if(commands.length > 3)
      return new UnsupportedCommand("Invalid commands: " + commands)
    
    var command:Option[Command[_, _]] = None

    if(commands.contains("--findShortestRoute")) {
      if(commands.findIndexOf(_.startsWith("--startingPoint")) != -1) {
        try {
          val from = commands(commands.findIndexOf(_.startsWith("--startingPoint"))).split("=")(1)
          command = Some(new FindRouteFromCommand(from))
        } catch {
          case e:ArrayIndexOutOfBoundsException => return new UnsupportedCommand("Invalid startingPoint value")
        }
      }
      
      reflectGenericCommandType(command, classOf[Airfield]) match {
        case Some(value) => return value
        case None => command = Some(new FindShortestRouteCommand(command.asInstanceOf[Option[Command[Any, Airfield]]], AirfieldFileReader.readFile(args(0))))
      }
    }
      
    if(commands.contains("--printRouteLength")) {
      reflectGenericCommandType(command, classOf[Buffer[Airfield]]) match {
        case Some(value) => return value
        case None => command = Some(new PrintRouteLengthCommand(command.asInstanceOf[Option[Command[Any, Buffer[Airfield]]]], args(0)))
      }
    }
    
    command match {
      case None => return new UnsupportedCommand("Please provide a command.")
      case Some(value) => return value
    }
  }
}
