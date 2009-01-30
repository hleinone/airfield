package org.beardedgeeks.airfield.cmd

/**
 * Exception thrown if the parameters were not acceptable.
 * @author hleinone
 */
class InsufficentParameterException(val message:String) extends Exception(message) {
}
