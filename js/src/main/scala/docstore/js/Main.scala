package docstore

import kind.logic.telemetry.*
import docstore.api.DefaultService
import org.scalajs.dom.html.Div
import kind.logic.js.*

@main
def mainJSApp(): Unit = {

  // NOTE: in "real life", this service would probably use a fetch client:

  // val url = s"/api/test?path=$path"
  // val request = FetchRequest(url)
  // Fetch.fetch(request).map { response =>
  //   response.text().map { text =>
  //     resultDiv.innerHTML = text
  //   }
  // }

  val telemetry               = Telemetry()
  val service: DefaultService = DocStoreApp()(using telemetry)

  val container: Div = HtmlUtils.$("main-container")
  container.innerHTML = ""
  container.appendChild(TestContainer(service, telemetry).content)
}
