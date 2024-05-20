package docstore

import org.scalajs.dom
import scalatags.JsDom.all.*
import org.scalajs.dom.{HTMLElement, Node, document, html}
import org.scalajs.dom.html.Div
import scala.scalajs.js.Dynamic.global
import scala.concurrent.Future
import scala.concurrent.duration.given
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSExportTopLevel
import upickle.default.*

import kind.logic.js.*
import kind.logic.telemetry.*
import scala.util.control.NonFatal

def content = div("Yo!").render

@main
def mainJSApp(): Unit = {
  println("Hello from Scala.js!")
  val container: Div = HtmlUtils.$("main-container")
  container.innerHTML = ""
  container.appendChild(content)
}
