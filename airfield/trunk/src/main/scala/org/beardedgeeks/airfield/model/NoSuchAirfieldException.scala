package org.beardedgeeks.airfield.model

/**
 * Exception thrown if such airfield is not found.
 * @author hannu
 */
class NoSuchAirfieldException(val message:String) extends Exception(message) {
}
