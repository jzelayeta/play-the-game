package repository

import com.typesafe.config.ConfigFactory
import javax.inject.{Inject, Singleton}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.{DefaultDB, FailoverStrategy, MongoConnection, MongoConnectionOptions}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MongoConnection @Inject() (val reactiveMongoApi: ReactiveMongoApi)(implicit val executionContext: ExecutionContext) extends ReactiveMongoComponents {

  private lazy val mongoConfig: com.typesafe.config.Config = ConfigFactory.defaultApplication().getConfig("mongodb")
  private lazy val getUri: String = mongoConfig.getString("uri")
  private lazy val driver = new reactivemongo.api.MongoDriver(Some(mongoConfig))
//  val opts = MongoConnectionOptions(nbChannelsPerNode = 2)

  val database: Future[DefaultDB] = for {
    uri  <- Future.fromTry(MongoConnection.parseURI(getUri))
    con = driver.connection(List("localhost"))
    dn <- Future(uri.db.get)
    da <- con.authenticate(uri.authenticate.map(_.db).get, uri.authenticate.map(_.user).get, uri.authenticate.flatMap(_.password).get, FailoverStrategy.default)
    db <- con.database(dn)
  } yield db

}
