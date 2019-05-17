package com.zeta.playthegame.util

import org.slf4j.{Logger, LoggerFactory}

trait LoggerPerClassAware {
  protected val logger: Logger = LoggerFactory.getLogger(getClass)
}
