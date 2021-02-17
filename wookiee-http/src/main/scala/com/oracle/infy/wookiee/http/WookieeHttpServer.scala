package com.oracle.infy.wookiee.http

import cats.effect.{IO, _}
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext

object WookieeHttpServer {

  def of(host: String, port: Int, httpRoutes: HttpRoutes[IO], executionContext: ExecutionContext)(
      implicit timer: Timer[IO],
      cs: ContextShift[IO]
  ): Stream[IO, ExitCode] = {
    BlazeServerBuilder[IO](executionContext)
      .bindHttp(port, host)
      .withoutBanner
      .withHttpApp(httpRoutes.orNotFound)
      .serve
  }
}
