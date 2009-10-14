package org.beardedgeeks.airfield.io

import _root_.scala.collection.mutable.ListBuffer
import _root_.scala.io.Source
import _root_.org.beardedgeeks.airfield.model.{Airfield, AirfieldFactory, InsufficentAirfieldDataException, NoSuchAirfieldException}
import _root_.org.slf4j.LoggerFactory

/**
 * Reads a file supposedly containing airfield information.
 * @author hleinone
 */
object AirfieldFileReader {
  private val logger = LoggerFactory.getLogger(getClass)
  private val bom = Source.fromBytes(Array(0xEF.asInstanceOf[Byte], 0xBB.asInstanceOf[Byte], 0xBF.asInstanceOf[Byte]), "UTF-8").foldLeft("")(_ + _)

    
  
  /**
   * Reads the file and returns the airfields specified in it. 
   * @param fileName The name of the file to read.
   * @return An array containing all the airfields correctly described in file.
   * @throws NoSuchAirfieldException If airfield with such code isn't found.
   * @throws InsufficentAirfieldDataException If the airfield data is not found from the airfield information
   * page on the Internet.
   */
  @throws(classOf[NoSuchAirfieldException])
  @throws(classOf[InsufficentAirfieldDataException])
  def readFile(fileName:String):ListBuffer[Airfield] = {
    var airfields = new ListBuffer[Airfield]()
    logger.debug("Analyzing the input file " + fileName + "...")
    val file = Source.fromFile(fileName, "UTF-8")
    
    for(line <- file.getLines) {
      // To avoid Java/Scala failing to read the first line because of UTF-8 BOM :(
      var otherLine = line
      if(line.startsWith(bom)) {
        logger.debug("UTF-8 Byte-Order-Mark found in file " + fileName)
        otherLine = line.replaceFirst(bom, "")
      }
        
      // For skipping empty lines, Scala doesn't support continue :(
      if(otherLine.trim().length != 0) {
        val pieces:Array[String] = otherLine.trim().split("\t")
        try {
          val airfield = AirfieldFactory.get(pieces(0))
          airfields += airfield
        } catch {
          case e:SkippingLineException => logger.debug("Skipping line... " + e.getMessage)
        }
      }
    }
    
    return airfields
  }
}
