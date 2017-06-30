package scala.typeclass.dataclasses.serializable

import org.json4s.JValue

import scala.typeclass.dataclasses.DataClasses.Person
import scala.typeclass.serializers.Serializable

/**
  * Created by Ben on 6/30/2017.
  */
abstract class PersonSerializable[Out] extends Serializable[Person, Out]

object PersonSerializableString extends PersonSerializable[String] {
  override def apply(person: Person): String =
    s"Person(${person.name}, ${person.age}, ${person.automobiles.map(AutomobileSerializableString)})"
}

object PersonSerializableJValue extends PersonSerializable[JValue] {
  import org.json4s.Extraction
  override def apply(person: Person): JValue = Extraction.decompose(person)(org.json4s.DefaultFormats)
}