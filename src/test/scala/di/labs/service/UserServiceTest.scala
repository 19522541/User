package di.labs.service

import com.google.inject.Guice
import com.twitter.inject.{Injector, IntegrationTest}
import di.labs.domain.InfoTemplate
import di.labs.module.{MainModule, TestModule}
import di.labs.util.Implicits.FutureEnhance

class UserServiceTest extends IntegrationTest{

  private val service = injector.instance[UserService]

  override protected def injector: Injector = Injector(Guice.createInjector(Seq(TestModule): _*))
  test(" Get User successfull") {
   val userInfo =await( service.getUserById(id ="2e85ff94-2724-11ed-8cd4-0242ac120002"))
    assert(userInfo.age==21)
  }
  test("add User successfull"){
    val  userInfo= service.addUser(userInfo = InfoTemplate(
      name= "NGuye Van Q",
      age = 21,
      sex = "male",
      dob = "2001-03-11"
    )).sync()
    assert(userInfo.age==21)
  }


}
