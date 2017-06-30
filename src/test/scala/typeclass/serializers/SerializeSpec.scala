package scala.typeclass.serializers

import org.json4s.JValue
import org.json4s.JsonAST.{JInt, JObject, JString}
import org.scalatest.enablers.ValueMapping
import org.scalatest.{Matchers, WordSpec}

import scala.typeclass.dataclasses.DataClasses.{Automobile, Person}
import scala.typeclass.dataclasses.serializable.{AutomobileSerializableJValue, AutomobileSerializableString, PersonSerializableJValue, PersonSerializableString}

/**
  * Created by Ben on 6/30/2017.
  */
class SerializeSpec extends WordSpec with Matchers {

  "Automobile" should {
    "serialize to a String" in {
      val auto = Automobile("Toyota", "Tacoma")

      AutomobileSerializableString(auto) shouldBe "Automobile(Toyota, Tacoma)"
    }
    "be implicitly serialized to a String" in {
      import scala.typeclass.dataclasses.serializable.AutomobileImplicitConversions.serializeToString
      val serializedAutomobile: String = Automobile("Honda", "Civic")

      serializedAutomobile shouldBe "Automobile(Honda, Civic)"
    }
    "serialize to a JValue" in {
      val auto = Automobile("Toyota", "Tacoma")

      AutomobileSerializableJValue(auto) shouldBe JObject(List(("manufacturer",JString("Toyota")), ("model",JString("Tacoma"))))
    }
    "be implicitly serialized to a JValue" in {
      import scala.typeclass.dataclasses.serializable.AutomobileImplicitConversions.automobileToJValue
      val serializedAutomobile: JValue = Automobile("Honda", "Civic")

      serializedAutomobile shouldBe JObject(List(("manufacturer",JString("Honda")), ("model",JString("Civic"))))
    }
  }
  "Person" should {
    val toyota = Automobile("Toyota", "Tacoma")
    val honda = Automobile("Honda", "Civic")
    val person = Person("Tom", 25, List(toyota, honda))

    "serialize to a String" in {
      PersonSerializableString(person) shouldBe "Person(Tom, 25, List(Automobile(Toyota, Tacoma), Automobile(Honda, Civic)))"
    }
    "serialize to a JValue" in {
      val personJson = PersonSerializableJValue(person)

      personJson \ "name" shouldBe JString("Tom")
      personJson \ "age" shouldBe JInt(25)

      implicit val ic = org.json4s.DefaultFormats
      val automobiles = (personJson \ "automobiles").extract[List[Automobile]]

      automobiles should have size 2

      implicit val valueMapping = new ValueMapping[List[Automobile]] {
        override def containsValue(map: List[Automobile], value: Any): Boolean =
          map.contains(value.asInstanceOf[Automobile])
      }

      automobiles should contain value toyota
      automobiles should contain value honda
    }
  }
}
