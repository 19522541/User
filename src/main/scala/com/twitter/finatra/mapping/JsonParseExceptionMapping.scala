package com.twitter.finatra.mapping

import com.fasterxml.jackson.core.JsonParseException
import com.google.inject.Singleton
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.exception.ApiError
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.inject.Logging

import javax.inject.Inject

@Singleton
class JsonParseExceptionMapping @Inject()(response: ResponseBuilder)
  extends ExceptionMapper[JsonParseException]
    with Logging {
  override def toResponse(request: Request,
                          ex: JsonParseException): Response = {
    error(s"JsonParseExceptionMapping: ${ex.getMessage}", ex)
    response.badRequest.json(
      ApiError(
        Status.BadRequest.code,
        reason = "invalaid_json_format",
        ex.getMessage
      )
    )
  }
}
