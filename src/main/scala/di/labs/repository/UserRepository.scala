package di.labs.repository

import com.twitter.finatra.http.exceptions.NotFoundException
import com.twitter.util.Future
import di.labs.domain.request.AddUserRequest
import di.labs.domain.thrift.TUserDetail

import java.sql.{Connection, ResultSet, SQLException}
import java.util.UUID
import javax.inject.Inject

trait UserRepository {
  /**
   * @param id id cua user
   * @return thông tin user có mã id cung cấp
   */
  @throws[NotFoundException]("id nay khong ton tai")
  @throws[InternalError]("gap bat cu exception nao")
  def getUsesById(id: String): Future[TUserDetail]

  /**
   * Xuất thông tin người dùng bằng thê ,chỉ cần chỉ cần đúng 1 phần tên của user
   * @param name
   * @return Danh sách chứa thông tin các user
   */
  def getUsersByName(name:String):Future[Seq[TUserDetail]]

  /**
   * Thêm nguời dùng mới
   * @param userInfo Các thông tin người
   * @return thông tin user vừa thêm vào
   */
  def addUser(userInfo: AddUserRequest): Future[TUserDetail]
}

class UserRepositoryImpl @Inject()(JDBCClient: JDBCClient) extends UserRepository {
  def getDetail (resultSet: ResultSet):Seq[TUserDetail]={
    var users:Seq[TUserDetail]=Seq[TUserDetail]()
      while (resultSet.next()){
      users= users:+ TUserDetail( id= resultSet.getString("id"),
            username = resultSet.getString("name"),
            age = resultSet.getInt("age"),
            sex =resultSet.getString("sex"),
            dob = resultSet.getDate("dob").toString
          )
      }
    users
  }
  override def getUsesById(id: String): Future[TUserDetail] = Future{
    try{
      val mySqlConn: Connection = JDBCClient.getConnection()
      val query = s"select * from user where id=?"
      val prepareStatement = mySqlConn.prepareStatement(query)
      prepareStatement.setString(1, id)
      val resultSet: ResultSet = prepareStatement.executeQuery()
      if (!resultSet.isBeforeFirst) throw NotFoundException("Can not find your user")
      getDetail(resultSet).head
    } catch {
      case e: NotFoundException => throw e
      case e: SQLException => throw e
      case e: Exception => throw new InternalError
    }

  }

  override def getUsersByName(name: String): Future[Seq[TUserDetail]] =Future  {

    try{
      val mySqlConn: Connection = JDBCClient.getConnection()
      val query = s"select * from user where name like ?"
      val prepareStatement = mySqlConn.prepareStatement(query)
      prepareStatement.setString(1, s"%${name}%")
      val resultSet: ResultSet = prepareStatement.executeQuery()
      if (!resultSet.isBeforeFirst) {
        throw NotFoundException("Can not find your user")
      } else {
        getDetail(resultSet)
      }
    } catch {
      case e :NotFoundException=> throw e
      case e: SQLException=>throw e
      case e: Exception => throw new InternalError
    }
  }
  override def addUser(userInfo: AddUserRequest): Future[TUserDetail] = Future{
    val mySqlConn: Connection = JDBCClient.getConnection()
    val query = s"insert into user(id, name, sex, age, dob) values(?, ?, ?, ?, ?)"
    val prepareStatement = mySqlConn.prepareStatement(query)
    val id = UUID.randomUUID().toString
    prepareStatement.setString(1,id)
    prepareStatement.setString(2,userInfo.name)
    prepareStatement.setString(3,userInfo.sex)
    prepareStatement.setInt(4,userInfo.age)
    prepareStatement.setString(5,userInfo.dob)
    prepareStatement.execute()
    val resultSet=prepareStatement.getResultSet
    TUserDetail(id =id,username = userInfo.name, age = userInfo.age  ,
      sex = userInfo.sex, dob = userInfo.dob)
  }
}

