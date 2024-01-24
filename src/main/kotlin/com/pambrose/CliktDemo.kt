package com.pambrose

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import com.pambrose.BuildConfig.BUILD_TIME
import com.pambrose.BuildConfig.VERSION
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CliktDemo : CliktCommand() {
  init {
    versionOption(
      version = VERSION,
      names = setOf("-v", "--version"),
      message = {
        val epochTimeMillis = BUILD_TIME
        val instant = Instant.ofEpochMilli(epochTimeMillis)
        val zoneId = ZoneId.systemDefault()
        val localDateTime = instant.atZone(zoneId).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = localDateTime.format(formatter)
        "Version: $VERSION Build Time: $formattedDateTime"
      },
    )
  }

  val count: Int by option("-c", "--count").int().default(1).help("Number of greetings")
  val name: String by option("-n", "--name").prompt("Your name").help("The person to greet")
  // val arg: String by argument(help = "an argument")

  override fun run() {
    repeat(count) {
      echo("Hello $name!")
    }
  }
}

fun main(args: Array<String>) = CliktDemo().main(args)
