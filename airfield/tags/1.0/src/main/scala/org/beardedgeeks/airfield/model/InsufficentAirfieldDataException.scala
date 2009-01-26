package org.beardedgeeks.airfield.model

/**
 * Exception thrown if airfield data fetched from the Internet is not acceptable.
 * @author hannu
 */
class InsufficentAirfieldDataException(val message:String) extends Exception(message) {
}
