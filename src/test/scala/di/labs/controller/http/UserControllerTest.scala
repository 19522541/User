package di.labs.controller.http

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.{EmbeddedTwitterServer, FeatureTest}
import com.twitter.util.Future
import di.labs.TestServer
import di.labs.domain.UserDetail
import di.labs.domain.thrift.TUserDetail
import di.labs.util.JsonUtils

class UserControllerTest extends FeatureTest{
  override protected def server = new EmbeddedHttpServer(twitterServer = new TestServer)

  test("Test Http Create Request") {
   val response = server.httpPost(path ="/user", postBody =
      """
         {
        "name":"Ngueyn THi 123 RR ",
        "dob":"2000-05-12",
        "sex":"Male",
        "age":10
        }
    """.stripMargin,andExpect = Status.Ok
    )
    val lendResponses:UserDetail = JsonUtils.fromJson[UserDetail](response.contentString)
      println(lendResponses)

  }
}
