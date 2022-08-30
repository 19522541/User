package di.labs.controller.http

import com.twitter.finatra.http.Controller
import di.labs.domain.InfoTemplate
import di.labs.domain.request.AddUserRequest
import di.labs.service.UserService

import javax.inject.Inject

class HttpUserController @Inject()(userService: UserService)  extends Controller{
  post("/user"){response:InfoTemplate=>{
    userService.addUser(response)
  }

  }
}



