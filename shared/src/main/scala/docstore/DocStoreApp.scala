package docstore

import kind.logic.*
import kind.logic.telemetry.*
import zio.*

trait DocStoreApp extends docstore.api.DefaultService

object DocStoreApp {

  val Id = Actor.service[DocStoreApp]

  def apply()(using telemetry: Telemetry): DocStoreApp = {
    val logic: [A] => DocStoreLogic[A] => Result[A] = DocStoreDefaults.defaultProgram
    App(logic)
  }

  class App(originalLogic: [A] => DocStoreLogic[A] => Result[A])(using telemetry: Telemetry)
      extends RunnableProgram[DocStoreLogic](originalLogic)
      with DocStoreApp {
    override protected def appCoords = Id

    def withOverride(overrideFn: PartialFunction[DocStoreLogic[?], Result[?]]): App = {
      val newLogic: [A] => DocStoreLogic[A] => Result[A] = [A] => {
        (_: DocStoreLogic[A]) match {
          case value if overrideFn.isDefinedAt(value) =>
            overrideFn(value).asInstanceOf[Result[A]]
          case value => logic(value).asInstanceOf[Result[A]]
        }
      }
      App(newLogic)(using telemetry)
    }

    def compareDocuments(
        compareDocumentsRequest: docstore.model.CompareDocumentsRequest
    ) = run(DocStoreLogic.diffPaths(compareDocumentsRequest)).execOrThrow()

    def copyDocument(
        copyDocumentRequest: docstore.model.CopyDocumentRequest
    ) = run(DocStoreLogic.copyPaths(copyDocumentRequest)).execOrThrow()

    def deleteDocument(
        path: String
    ): docstore.model.DeleteDocument200Response | docstore.model.GetDocument404Response = {
      run(DocStoreLogic.delete(path)).execOrThrow()
    }
    def getDocument(
        path: String,
        version: Option[String]
    ): Json | docstore.model.GetDocument404Response = {
      run(DocStoreLogic.get(path, version)).execOrThrow()
    }

    def getMetadata(path: String) = {
      ???
      // run(DocStoreLogic.()).execOrThrow()
    }
    def saveDocument(path: String, body: Json): docstore.model.SaveDocument200Response = {
      run(DocStoreLogic.save(path, body)).execOrThrow()

    }
    def updateDocument(
        path: String,
        body: Json
    ): docstore.model.UpdateDocument200Response | docstore.model.GetDocument404Response = {
      run(DocStoreLogic.update(path, body)).execOrThrow()
    }
  }

}
