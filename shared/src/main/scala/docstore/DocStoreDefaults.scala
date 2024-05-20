package docstore

import kind.logic.*
import kind.logic.telemetry.*

object DocStoreDefaults extends DocStoreDefaults

trait DocStoreDefaults {

  def defaultProgram(using telemetry: Telemetry): [A] => DocStoreLogic[A] => Result[A] = [A] =>
    (_: DocStoreLogic[A]) match {
      case command @ DocStoreLogic.CompareDocuments(_) =>
        docstore.model
          .CompareDocuments200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.CopyDocument(_) =>
        docstore.model
          .CopyDocument200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.DeleteDocument(_) =>
        docstore.model
          .DeleteDocument200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.GetDocument(_, _) =>
        ujson.Null.asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.GetMetadata() =>
        docstore.model
          .GetMetadata200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.SaveDocument(_, _) =>
        docstore.model
          .SaveDocument200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
      case command @ DocStoreLogic.UpdateDocument(_, _) =>
        docstore.model
          .UpdateDocument200Response()
          .asResultTraced(Actor.service("app", "DocStore"), command)
  }
}

//   def asMermaid(quantity: Int, toppings: List[String]): String = {
//     given telemetry: Telemetry = Telemetry()
//     defaultProgram.orderPizzaAsMermaid(quantity, toppings)._2
//   }
