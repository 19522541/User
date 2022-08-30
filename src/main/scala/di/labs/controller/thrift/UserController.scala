package di.labs.controller.thrift

import com.twitter.finatra.thrift.Controller
import di.labs.domain.request.AddUserRequest
import di.labs.service.{TUser, UserService}

import javax.inject.{Inject, Singleton}

@Singleton
class UserController @Inject()(userService: UserService) extends Controller(TUser) {

  handle(TUser.GetUserById) { require: TUser.GetUserById.Args => {
    val response =userService.getUserById(require.id)
    println(s"UserController::handTUser.GetUserById::response${response}")
    response
    }
  }
  handle(TUser.AddUser) { require: TUser.AddUser.Args => {
    val detail: AddUserRequest = AddUserRequest(name = require.userInfo.username,
      dob = require.userInfo.dob, sex = require.userInfo.sex, age = require.userInfo.age)
      userService.addUser(detail)
  }
  }
  handle(TUser.GetUserByName) { require: TUser.GetUserByName.Args => {
    val users = userService.getUserByName(require.name)
    users
  }
  }

}
