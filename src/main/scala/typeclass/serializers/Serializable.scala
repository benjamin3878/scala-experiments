package scala.typeclass.serializers

/**
  * Created by Ben on 6/30/2017.
  */
trait Serializable[In, Out] extends (In => Out)

object Serialize {
  def apply[In, Out](in: In)(implicit s: Serializable[In, Out]): Out = s(in)
}

