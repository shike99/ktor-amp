package ktor.amp

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module = Application::module).start()
}

fun Application.module() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, "Hello, Kotlin!!!")
        }
    }
}