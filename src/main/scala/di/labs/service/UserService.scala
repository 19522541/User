package di.labs.service

import com.twitter.util.{Await, Future}
import di.labs.domain.request.AddUserRequest
import di.labs.domain.thrift.{TResult, TUserDetail}
import di.labs.repository.UserRepository

import javax.inject.Inject

trait UserService {
  /**
   * Xuất thông tin người dùng, tìm dựa vào Id
   * @param id  ID của người dùng
   * @return Thông tin nguời dùng
   */
  def getUserById(id: String): Future[TResult]

  /**
   * Xuất thông tin người dùng, tìm dựa vào tên
   * @param name
   * @return Danh sách các user
   */
  def getUserByName(name:String):Future[TResult]
  /**
   * Thêm thông tin nguời dùng
   * @param userInfo Thông tin của user
   * @return Thông tin nguời dùng vừa mới thêm
   */
  def addUser(userInfo: AddUserRequest): Future[TResult]
}

class UserServiceImpl @Inject()(userRepository: UserRepository) extends UserService {
  override def getUserById(id: String): Future[TResult] =Future {
   try{
     val userDetail= Await.result(userRepository.getUsesById(id))
    TResult( code = 200, data = Seq(userDetail), msg = "Ok")
    }catch {
     case e:Exception=> TResult(code = 500, data = null, msg =e.toString)
    }
  }

  override def getUserByName(name: String): Future[TResult] = Future{

    try {
      val userDetails = Await.result(userRepository.getUsersByName(name))
      TResult(code = 200, data = userDetails, msg = "Ok")
    } catch {
      case e: Exception => TResult(code = 500, data = null, msg = e.toString)
    }
  }
  override def addUser(userInfo: AddUserRequest): Future[TResult] = Future{


    try {
      val userDetail = Await.result(userRepository.addUser(userInfo))
      TResult(code = 200, data = Seq(userDetail), msg = "Ok")
    } catch {
      case e: Exception => TResult(code = 500, data = null, msg = e.toString)
    }
  }

}

