package docstore.tree

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import upickle.default.*

class PathTreeTest extends AnyWordSpec with Matchers {
  val data = ujson.read("""{
        "some" : "value"
      }""")

  val tree    = PathTree.forPath("a/b/c")
  val updated = tree.updateData(Seq("a", "b2", "see"), data, true)

  "PathTree.asJson" should {
    "turn the tree into json" in {
      val aHasData = updated.updateData(
        Seq("a"),
        ujson.read("""{
        "a-data" : "is here"
      }"""),
        true
      )
      println(aHasData.asJson.render(2))
    }
  }
  "PathTree.update" should {
    "update the tree" in {
      val updated = tree.updateData(Seq("a", "b2", "see"), data, true)
      tree.formatted.shouldBe("a.b.c = null")
      updated.formatted.shouldBe("""a.b.c = null
            |a.b2.see = {"some":"value"}""".stripMargin('|'))
    }

  }
}
