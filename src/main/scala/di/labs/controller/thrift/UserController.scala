package di.labs.controller.thrift

import com.twitter.finatra.thrift.Controller
import di.labs.domain.InfoTemplate
import di.labs.service.{TUser, UserService}

import javax.inject.{Inject, Singleton}

@Singleton
class UserController @Inject()(userService: UserService) extends Controller(TUser) {

  handle(TUser.GetUserById) { require: TUser.GetUserById.Args => {
    userService.getUserById(require.id)
    }
  }
  handle(TUser.AddUser) { require: TUser.AddUser.Args => {
    val detail: InfoTemplate = InfoTemplate(name = require.userInfo.username,
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
