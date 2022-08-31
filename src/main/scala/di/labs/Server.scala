package di.labs

import com.google.inject.Module
import com.google.inject.util.Modules
import com.twitter.finatra.filters.CORSFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.mapping.{CaseClassExceptionMapping, CommonExceptionMapping, JsonParseExceptionMapping}
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.routing.ThriftRouter
import di.labs.controller.http.HealthController
import di.labs.controller.http.HttpUserController

import di.labs.controller.thrift.UserController
import di.labs.module.{MainModule, TestModule}
import di.labs.util.ZConfig

/**
 * Created by SangDang on 9/8/
 **/
object MainApp extends Server

class TestServer extends Server {

  override def modules: Seq[com.google.inject.Module] =
    Seq(overrideModule(super.modules ++ Seq(TestModule): _*))

  private def overrideModule(modules: Module*): Module = {
    if (modules.size == 1) return modules.head

    var module = modules.head
    modules.tail.foreach(m => {
      module = Modules.`override`(module).`with`(m)
    })
    module
  }
}

class Server extends HttpServer with ThriftServer {

  override protected def defaultHttpPort: String =
    ZConfig.getString("server.http.port", ":8080")

  override protected def defaultThriftPort: String =
    ZConfig.getString("server.thrift.port", ":8082")

  override protected def disableAdminHttpServer: Boolean =
    ZConfig.getBoolean("server.admin.disable", true)


  override protected def modules: Seq[Module] = Seq(MainModule)

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CORSFilter](beforeRouting = true)
      .filter[CommonFilters]
      .add[HealthController]
      .add[HttpUserController]
      .exceptionMapper[CaseClassExceptionMapping]
      .exceptionMapper[JsonParseExceptionMapping]
      .exceptionMapper[CommonExceptionMapping]
  }

  override protected def configureThrift(router: ThriftRouter): Unit = {
    router.add[UserController]
    //router.add[UserController]
  }
}
