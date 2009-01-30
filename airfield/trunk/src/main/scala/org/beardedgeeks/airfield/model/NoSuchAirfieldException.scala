package org.beardedgeeks.airfield.model

/**
 * Exception thrown if such airfield is not found.
 * @author hleinone
 */
class NoSuchAirfieldException(val message:String) extends Exception(message) {
}
