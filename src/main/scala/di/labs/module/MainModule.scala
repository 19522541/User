package di.labs.module

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import di.labs.repository.{JDBCClient, JDBCClientImpl, UserRepository, UserRepositoryImpl}
import di.labs.service.{UserService, UserServiceImpl}
import di.labs.util.ZConfig

/**
 * Created by SangDang on 9/16/16.
 */
object MainModule extends TwitterModule {
  override def configure(): Unit = {
    bind[UserService].to[UserServiceImpl]
    bind[UserRepository].to[UserRepositoryImpl]
  }

  @Provides
  def providesJDBC(): JDBCClient = {
    new JDBCClientImpl(url = ZConfig.getString("database.mysql.url"),
      username = ZConfig.getString("database.mysql.username"),
      pass = ZConfig.getString("database.mysql.password"))

  }
}
