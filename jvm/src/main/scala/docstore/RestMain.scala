package docstore

import docstore.BaseApp
import docstore.api.*
import docstore.model.*

import java.io.File

// TODO - write your business logic for your services here (the defaults all return 'not implemented'):
val myDefaultService: DefaultService = ???

/** This is your main entry point for your REST service It extends BaseApp which defines the
  * business logic for your services
  */
object RestMain extends BaseApp(appDefaultService = myDefaultService):
  start()
