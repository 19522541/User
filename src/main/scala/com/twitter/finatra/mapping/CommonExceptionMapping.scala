package com.twitter.finatra.mapping

import com.google.inject.Singleton
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.exception.{ApiError, BaseException}
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.inject.Logging

import javax.inject.Inject

/**
  * @author anhlt
  */
@Singleton
class CommonExceptionMapping @Inject()(response: ResponseBuilder)
    extends ExceptionMapper[Throwable]
    with Logging {

  def toApiError(ex: Throwable): ApiError = {
    ex match {
      case e: BaseException =>
        ApiError(e.getStatus.code, e.reason, e.getMessage)
      case _ =>
        ApiError(
          Status.InternalServerError.code,
          "internal_error",
          ex.getMessage
        )
    }
  }

  override def toResponse(request: Request, ex: Throwable): Response = {
    logError(ex)
    val error = toApiError(ex)
    response.status(error.code).json(error)
  }

  private def logError(ex: Throwable): Unit = {
    error(s"${ex.getClass.getName}: ${ex.getMessage}", ex)
  }
}
