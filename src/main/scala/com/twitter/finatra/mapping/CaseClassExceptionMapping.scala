package com.twitter.finatra.mapping

import com.google.inject.Singleton
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.exception.ApiError
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.finatra.jackson.caseclass.exceptions.CaseClassMappingException
import com.twitter.inject.Logging

import javax.inject.Inject

/**
  * @author tvc12 - Thien Vi
  * @created 06/06/2021 - 8:22 PM
  */
@Singleton
class CaseClassExceptionMapping @Inject()(response: ResponseBuilder)
    extends ExceptionMapper[CaseClassMappingException]
    with Logging {
  override def toResponse(request: Request,
                          ex: CaseClassMappingException): Response = {
    logError(ex)
    response.badRequest.json(
      ApiError(Status.BadRequest.code, "bad_request", ex.errors.head.getMessage)
    )
  }

  private def logError(ex: Throwable): Unit = {
    error(s"${ex.getClass.getName}: ${ex.getMessage}", ex)
  }
}
