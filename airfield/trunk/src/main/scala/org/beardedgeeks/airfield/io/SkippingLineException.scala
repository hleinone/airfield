package org.beardedgeeks.airfield.io

/**
 * Exception thrown if the line is skipped.
 * @author hleinone
 */
case class SkippingLineException(val message:String) extends Exception(message)
