package di.labs.controller.http

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import javax.inject.Singleton

/**
 * Created by SangDang on 9/18/16.
 */
@Singleton
class HealthController extends Controller {
  get("/ping") { request: Request =>
  {
    logger.info("ping")
    Map("status" -> "ok", "data" -> "pong")
  }
  }
}
