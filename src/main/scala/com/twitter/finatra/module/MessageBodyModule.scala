package com.twitter.finatra.module

import com.twitter.finatra.http.internal.marshalling.DefaultMessageBodyReaderImpl
import com.twitter.finatra.http.marshalling.{DefaultMessageBodyReader, DefaultMessageBodyWriter}
import com.twitter.inject.{InjectorModule, TwitterModule}

/**
  * @author tvc12 - Thien Vi
  * @created 06/06/2021 - 8:21 PM
  */
object MessageBodyModule extends TwitterModule {

  flag(
    "http.response.charset.enabled",
    true,
    "Return HTTP Response Content-Type UTF-8 Charset"
  )

  override val modules = Seq(InjectorModule)

  protected override def configure(): Unit = {
    bindSingleton[DefaultMessageBodyReader].to[DefaultMessageBodyReaderImpl]
    bindSingleton[DefaultMessageBodyWriter].to[CustomResponseWriter]
  }
}
