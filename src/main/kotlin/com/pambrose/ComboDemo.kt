package com.pambrose

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.varabyte.kotter.foundation.anim.renderAnimOf
import com.varabyte.kotter.foundation.anim.text
import com.varabyte.kotter.foundation.anim.textAnimOf
import com.varabyte.kotter.foundation.liveVarOf
import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.foundation.text.green
import com.varabyte.kotter.foundation.text.p
import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.foundation.text.textLine
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ComboDemo : CliktCommand() {
  val delay: Int by option("-d", "--delay").int().default(5).help("Seconds of delay")
  val answer: String by option("-a", "--answer").default("Success!").help("The result value")

  override fun run() {
    session {
      var result by liveVarOf<String?>(null)
      val spinnerAnim = textAnimOf(listOf("\\", "|", "/", "-"), 125.milliseconds)

      // Same as: val thinkingAnim = textAnimOf(listOf("", ".", "..", "..."), 500.milliseconds)
      val thinkingAnim = renderAnimOf(4, 500.milliseconds) { frameIndex ->
        text(".".repeat(frameIndex))
      }

      section {
        val stillCalculating = (result == null)
        if (stillCalculating) {
          text(spinnerAnim)
        } else {
          green { text("✓") }
        }
        text(" Calculating")
        if (stillCalculating) {
          thinkingAnim(this)
        } else {
          textLine("... Done!")
          p {
            textLine("The answer is: $result")
          }
        }
      }.run {
        delay(delay.seconds)
        result = answer
      }
    }
  }
}

fun main(args: Array<String>) = ComboDemo().main(args)
