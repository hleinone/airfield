package org.beardedgeeks.airfield.model

import _root_.org.slf4j.LoggerFactory
import _root_.com.meterware.httpunit.{HttpUnitOptions, WebConversation, GetMethodWebRequest, HttpException}
import _root_.java.io.IOException
import _root_.java.net.MalformedURLException
import _root_.org.xml.sax.SAXException
import _root_.org.beardedgeeks.airfield.math.DecimalDegree
import _root_.org.beardedgeeks.airfield.geo.Coordinate
import _root_.org.beardedgeeks.airfield.io.SkippingLineException
import _root_.scala.collection.mutable.HashMap

/**
 * GOF Factory Method for returning airfields by the International Civil Aviation Organization airport code.
 * @author hleinone
 */
object AirfieldFactory {
  private val logger = LoggerFactory.getLogger(getClass)

  private var airfields = new HashMap[String, Airfield]
  private val airFieldCoordinatesUrl = "http://gc.kls2.com/airport/"
  
  /**
   * Returns an Airfield object representing the data found from http://gc.kls2.com/ for the given mnemonic.
   * @param mnemonic The International Civil Aviation Organization airport code.
   * @return The airfield.
   * @throws SkippingLineException If the given mnemonic is not syntactically an ICAO airfield code. or if 
   * airfield with such code isn't found.
   * @throws NoSuchAirfieldException If airfield with such code isn't found.
   * @throws InsufficentAirfieldDataException If the airfield data is not found from the airfield information
   * page on the Internet.
   */
  @throws(classOf[SkippingLineException])
  @throws(classOf[NoSuchAirfieldException])
  @throws(classOf[InsufficentAirfieldDataException])
  def get(mnemonic:String):Airfield = {
    implicit def string2DecimalDegree(string:String):DecimalDegree = {
      DecimalDegree(java.lang.Double.parseDouble(string))
    }

    if (!airfields.contains(mnemonic)) {
       if(!mnemonic.matches("[A-Z]{4}"))
        throw SkippingLineException(mnemonic)

       // fetch the airport data from Internet
       HttpUnitOptions.setScriptingEnabled(false)
       val webConversation = new WebConversation()
       val request = new GetMethodWebRequest(airFieldCoordinatesUrl + mnemonic)
       logger.debug("Retrieving data from Internet...")
       // if response throws an HttpException it's not an airfield info page
       val response = try {
         webConversation.getResponse(request)
       } catch {
         case e:MalformedURLException =>
           throw NoSuchAirfieldException("No airfield '" + mnemonic + "' exists")
         case e:IOException =>
           throw NoSuchAirfieldException("No airfield '" + mnemonic + "' exists")
         case e:SAXException =>
           throw NoSuchAirfieldException("No airfield '" + mnemonic + "' exists")
       }
       // in case we get some other url than the requested, something is wrong
       if (response.getURL.toString != airFieldCoordinatesUrl + mnemonic)
         throw NoSuchAirfieldException("No airfield '" + mnemonic + "' exists")
       
       val geoPosition = response.getMetaTagContent("name", "geo.position")
       if (geoPosition.length != 1)
         throw InsufficentAirfieldDataException("Illegal geo.position")
       val coordinates = geoPosition(0).split(";")
       if (coordinates.length != 2)
         throw InsufficentAirfieldDataException("Illegal coordinates")
       
       val description = response.getMetaTagContent("name", "description")
       if(description.length != 1)
         throw InsufficentAirfieldDataException("Illegal description")
       
       logger.debug("Found data for " + mnemonic)
       airfields.put(mnemonic, Airfield(mnemonic, description(0), coordinates(0), coordinates(1)))
    }
    
    return airfields(mnemonic)
  }
}
