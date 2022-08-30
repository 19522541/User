package di.labs.service

import com.google.inject.Guice
import com.twitter.inject.{Injector, IntegrationTest}
import di.labs.domain.request.AddUserRequest
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
    val name= "NGuye Van zzZzz"
    val age= 21
    val sex= "MaleMale"
    val dob= "1998-01-11"
    val  userInfo= service.addUser(userInfo = AddUserRequest(
      name= name,
      age = age,
      sex = sex,
      dob = dob
    )).sync()
    assert(userInfo.age==21)
    assert(userInfo.sex.equals(sex))
    assert(userInfo.dob==dob)
    assert(userInfo.username.equals(name))
  }
}
