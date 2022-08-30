package di.labs.service

import com.twitter.util.Future
import di.labs.domain.request.AddUserRequest
import di.labs.domain.thrift.TUserDetail
import di.labs.repository.UserRepository

import javax.inject.Inject

trait UserService {
  /**
   * Xuất thông tin người dùng, tìm dựa vào Id
   * @param id  ID của người dùng
   * @return Thông tin nguời dùng
   */
  def getUserById(id: String): Future[TUserDetail]

  /**
   * Xuất thông tin người dùng, tìm dựa vào tên
   * @param name
   * @return Danh sách các user
   */
  def getUserByName(name:String):Future[Seq[TUserDetail]]
  /**
   * Thêm thông tin nguời dùng
   * @param userInfo Thông tin của user
   * @return Thông tin nguời dùng vừa mới thêm
   */
  def addUser(userInfo: AddUserRequest): Future[TUserDetail]
}

class UserServiceImpl @Inject()(userRepository: UserRepository) extends UserService {
  override def getUserById(id: String): Future[TUserDetail] = {
    userRepository.getUsesById(id)
  }

  override def getUserByName(name: String): Future[Seq[TUserDetail]] = {
    userRepository.getUsersByName(name)
  }
  override def addUser(userInfo: AddUserRequest): Future[TUserDetail] = {
    userRepository.addUser(userInfo)
  }

}

