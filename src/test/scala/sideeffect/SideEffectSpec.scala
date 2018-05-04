package scala.sideeffect

import org.scalatest.{Matchers, WordSpec}

import scala.sideeffect.SideEffect._

/**
  * Created by vagrant on 04.05.18.
  */
class SideEffectSpec extends WordSpec with Matchers {

  implicit def OptionToFunctor[A](o: Option[A]): Functor[A, Option] = new Functor[A, Option] {
    override def map[B](f: (A) => B): Option[B] = o.map(f)
  }

  "SideEffect" should {
    "work with an Option" in {
      var stringValue: String = null
      Option("str")
        .withSideEffect(s => stringValue = s)

      stringValue shouldBe "str"
    }
  }
}
