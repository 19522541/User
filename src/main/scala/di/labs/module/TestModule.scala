package di.labs.module

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import di.labs.repository.{JDBCClient, JDBCClientImpl, UserRepository, UserRepositoryImpl}
import di.labs.service.{UserService, UserServiceImpl}
object TestModule extends TwitterModule {
  override def configure(): Unit = {
    bind[UserService].to[UserServiceImpl]
    bind[UserRepository].to[UserRepositoryImpl]
  }

  @Provides
  def providesJDBC():JDBCClient = {
    new  JDBCClientImpl(url = "jdbc:mysql://localhost:3306/loanrecord", username = "root", pass = "di@2020!")

  }
}


