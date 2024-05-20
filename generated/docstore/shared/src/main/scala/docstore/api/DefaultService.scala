//> using scala "3.3.1"
//> using lib "com.lihaoyi::cask:0.8.3"
//> using lib "com.lihaoyi::scalatags:0.12.0"
/** Document Storage API API for storing, retrieving, updating, and deleting JSON documents.
  *
  * OpenAPI spec version: 1.1.0
  *
  * Contact: team@openapitools.org
  *
  * NOTE: This class is auto generated by OpenAPI Generator.
  *
  * https://openapi-generator.tech
  */

// generated from apiService.mustache
package docstore.api

import _root_.docstore.model.CompareDocuments200Response
import _root_.docstore.model.CompareDocuments400Response
import _root_.docstore.model.CompareDocumentsRequest
import _root_.docstore.model.CopyDocument200Response
import _root_.docstore.model.CopyDocument404Response
import _root_.docstore.model.CopyDocumentRequest
import _root_.docstore.model.DeleteDocument200Response
import _root_.docstore.model.GetDocument404Response
import _root_.docstore.model.GetMetadata200Response
import _root_.docstore.model.SaveDocument200Response
import _root_.docstore.model.UpdateDocument200Response

import _root_.docstore.model.*

object DefaultService {
  def apply(): DefaultService = new DefaultService {
    override def compareDocuments(
        compareDocumentsRequest: CompareDocumentsRequest
    ): CompareDocuments200Response | CompareDocuments400Response = ???
    override def copyDocument(copyDocumentRequest: CopyDocumentRequest): CopyDocument200Response |
      CopyDocument404Response = ???
    override def deleteDocument(path: String): DeleteDocument200Response | GetDocument404Response =
      ???
    override def getDocument(path: String, version: Option[String]): ujson.Value |
      GetDocument404Response = ???
    override def getMetadata(path: String): GetMetadata200Response                      = ???
    override def saveDocument(path: String, body: ujson.Value): SaveDocument200Response = ???
    override def updateDocument(path: String, body: ujson.Value): UpdateDocument200Response |
      GetDocument404Response = ???
  }
}

/** The Default business-logic
  */
trait DefaultService {

  /** Compare two JSON documents
    *
    * @return
    *   CompareDocuments200Response
    */
  def compareDocuments(
      compareDocumentsRequest: CompareDocumentsRequest
  ): CompareDocuments200Response | CompareDocuments400Response

  /** Copy JSON data from one path to another
    *
    * @return
    *   CopyDocument200Response
    */
  def copyDocument(copyDocumentRequest: CopyDocumentRequest): CopyDocument200Response |
    CopyDocument404Response

  /** Delete JSON data at a specified path
    *
    * @return
    *   DeleteDocument200Response
    */
  def deleteDocument(path: String): DeleteDocument200Response | GetDocument404Response

  /** Retrieve JSON data from a specified path
    *
    * @return
    *   Object
    */
  def getDocument(path: String, version: Option[String]): ujson.Value | GetDocument404Response

  /** Retrieve metadata information
    *
    * @return
    *   GetMetadata200Response
    */
  def getMetadata(path: String): GetMetadata200Response

  /** Save JSON data to a specified path
    *
    * @return
    *   SaveDocument200Response
    */
  def saveDocument(path: String, body: ujson.Value): SaveDocument200Response

  /** Update JSON data at a specified path
    *
    * @return
    *   UpdateDocument200Response
    */
  def updateDocument(path: String, body: ujson.Value): UpdateDocument200Response |
    GetDocument404Response
}
