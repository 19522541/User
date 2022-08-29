package di.labs.repository

import java.sql.{Connection, DriverManager}

trait JDBCClient {
  /**
   * Lay ra connection, noi lay chi can goi
   * val connection = client.getConnection()
   * connection.close()
   */

  def getConnection(): Connection

}

class JDBCClientImpl(url: String, username: String, pass: String) extends JDBCClient {
  override def getConnection(): Connection = DriverManager.getConnection(url, username, pass)
}