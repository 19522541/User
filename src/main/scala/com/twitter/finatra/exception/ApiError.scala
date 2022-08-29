package com.twitter.finatra.exception

case class ApiError(code: Int, reason: String, message: String)
