import reactivemongo.bson.BSONObjectID

def getRandomId: String = BSONObjectID.generate().stringify

(1 to 100).toList.map(x => BSONObjectID.generate()).map(_.stringify)