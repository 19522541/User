package di.labs.controller.http

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.{EmbeddedTwitterServer, FeatureTest}
import di.labs.TestServer

class UserControllerTest extends FeatureTest{
  override protected def server = new EmbeddedHttpServer(twitterServer = new TestServer)

  test("Test Http Create Request") {
    server.httpPost(path ="/user", postBody =
      """
         {
        "name":"Ngy GGH",
        "dob":"1999-05-12",
        "sex":"Male",
        "age":10000
        }
    """.stripMargin,andExpect = Status.Ok
    )

  }
}
