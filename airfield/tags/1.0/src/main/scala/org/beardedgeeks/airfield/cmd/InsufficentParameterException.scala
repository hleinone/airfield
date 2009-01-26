package org.beardedgeeks.airfield.cmd

/**
 * Exception thrown if the parameters were not acceptable.
 * @author hannu
 */
class InsufficentParameterException(val message:String) extends Exception(message) {
}
