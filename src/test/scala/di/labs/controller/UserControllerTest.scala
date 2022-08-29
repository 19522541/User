package di.labs.controller

import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finatra.thrift.ThriftClient
import com.twitter.inject.server.FeatureTest
import di.labs.domain.thrift.TUserDetail
import di.labs.service.TUser
import di.labs.{TestServer, service}


class UserControllerTest extends FeatureTest {
  override protected val server = new EmbeddedHttpServer(
    twitterServer = new TestServer
  ) with ThriftClient
  val client: TUser.MethodPerEndpoint = server.thriftClient[service.TUser.MethodPerEndpoint]("123")
  test("Thrith Get Existed User By Id Controller") {
    val response = await(client.getUserById("2e85ff94-2724-11ed-8cd4-0242ac120002"))
    println(s"UserControllerTest::GetUser::response$response")
    assert(response.age == 21)
  }
  test("Thrift addUser method controller") {
    val response = await(client.addUser(TUserDetail(id = "001", username = "Ng Van Z",
      age = 11, sex = "MAle", dob = "1999-05-12")))
    assert(response.age == 11)
  }
  test("Thrith Get Exist User By Name Controller") {
    val response = await(client.getUserByName("Ng Van A"))
    assert(response.length == 1)
  }
  test("Thrith Get Not Exist User By Name"){
    val response = await(client.getUserByName("Ng Van O"))

  }
  test("Thrith Get Not Exist User By Id") {
    val response = await(client.getUserById("MEIQ213"))
  }
}
