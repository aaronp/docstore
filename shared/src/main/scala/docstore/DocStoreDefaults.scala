package docstore

import docstore.model.*
import kind.logic.*
import kind.logic.telemetry.*
import zio.*
import docstore.tree.*
import javax.print.Doc

object DocStoreDefaults {
  def apply() = {
    val ref = Ref.make(PathTree.forPath("")).execOrThrow()
    new DocStoreDefaults(ref) {}
  }
}

trait DocStoreDefaults(ref: Ref[PathTree]) {
  import PathTree.asPath

  def defaultProgram(using telemetry: Telemetry): [A] => DocStoreLogic[A] => Result[A] = [A] =>
    (_: DocStoreLogic[A]) match {
      case command @ DocStoreLogic.CompareDocuments(
            CompareDocumentsRequest(Some(leftPath), Some(rightPath))
          ) =>
        val task = for {
          latest <- ref.get
          leftValue = latest.at(leftPath.asPath)
          leftData  = leftValue.map(_.data).getOrElse(ujson.Null)

          rightValue = latest.at(rightPath.asPath)
          rightData  = rightValue.map(_.data).getOrElse(ujson.Null)

          diff = leftData.diffWith(rightData)
        } yield CompareDocuments200Response(Option(diff))

        task.taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.CompareDocuments(request) =>
        ZIO
          .attempt(sys.error(s"invalid diff request $request"))
          .taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.CopyDocument(
            CopyDocumentRequest(Some(fromPath), Some(toPath))
          ) =>
        val task = for {
          latest <- ref.get
          data = latest.at(fromPath.asPath) match {
            case Some(tree) => tree.data
            case None       => ujson.Null
          }
          _ <- ref
            .update(_.updateData(toPath.asPath, data))
          response = docstore.model.CopyDocument200Response()
        } yield response
        task.taskAsResultTraced(DocStoreApp.Id, command)

      case command @ DocStoreLogic.CopyDocument(CopyDocumentRequest(from, to)) =>
        ZIO
          .attempt(sys.error(s"invalid copy request $from to $to"))
          .taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.DeleteDocument(request) =>
        docstore.model
          .DeleteDocument200Response()
          .asResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.GetDocument(path, None) =>
        val task = for {
          latest <- ref.get
          value = latest.at(path.asPath)
          data  = value.map(_.data).getOrElse(ujson.Null)
        } yield data

        task.taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.GetDocument(path, Some(version)) =>
        val task = for {
          latest <- ref.get
          value = latest.at(path.asPath :+ s"version-${version}")
          data  = value.map(_.data).getOrElse(ujson.Null)
        } yield data

        task.taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.GetMetadata(path) =>
        val task = for {
          latest <- ref.get
          value = latest.at(path.asPath)
          data  = value.map(_.data).getOrElse(ujson.Null)
        } yield GetMetadata200Response(latestVersion = Option(s"TODO: versions: ${data.render(2)}"))

        task.taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.SaveDocument(path, data) =>
        val task = for {
          _ <- ref
            .update(_.updateData(path.asPath, data))
          latest <- ref.get
          response = docstore.model
            .SaveDocument200Response(Option(s"Updated: ${latest.formatted}"))
        } yield response
        task.taskAsResultTraced(DocStoreApp.Id, command)
      case command @ DocStoreLogic.UpdateDocument(path, newValue) =>
        val task = for {
          _ <- ref
            .update(_.patchData(path.asPath, newValue))
          latest <- ref.get
          response = docstore.model
            .UpdateDocument200Response(Option(s"Updated: ${latest.formatted}"))
        } yield response
        task.taskAsResultTraced(DocStoreApp.Id, command)
  }
}

//   def asMermaid(quantity: Int, toppings: List[String]): String = {
//     given telemetry: Telemetry = Telemetry()
//     defaultProgram.orderPizzaAsMermaid(quantity, toppings)._2
//   }
