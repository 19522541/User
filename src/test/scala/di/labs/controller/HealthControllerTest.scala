package di.labs.controller

import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.FeatureTest
import di.labs.TestServer
import com.twitter.finagle.http.Status
/**
 * Created by SangDang on 9/18/16.
 */
class HealthControllerTest extends FeatureTest {

  override protected def server = new EmbeddedHttpServer(twitterServer = new TestServer) with ThriftClient

  test("Test Health Http") {
    server.httpGet(path = "/ping",andExpect = Status.Ok, withJsonBody =
      """
        {
          "status":"ok",
          "data":"pong"
        }
      """.stripMargin)

  }
}
