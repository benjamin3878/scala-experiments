package scala.typeclass.dataclasses.serializable

import org.json4s.JValue

import scala.typeclass.dataclasses.DataClasses.Automobile
import scala.typeclass.serializers.Serializable

/**
  * Created by Ben on 6/30/2017.
  */
abstract class AutomobileSerializable[Out] extends Serializable[Automobile, Out]

object AutomobileSerializableString extends AutomobileSerializable[String] {
  override def apply(automobile: Automobile): String = s"Automobile(${automobile.manufacturer}, ${automobile.model})"
}

object AutomobileSerializableJValue extends AutomobileSerializable[JValue] {
  import org.json4s.Extraction
  override def apply(automobile: Automobile): JValue = Extraction.decompose(automobile)(org.json4s.DefaultFormats)
}

object AutomobileImplicitConversions {
  implicit def serializeToString(automobile: Automobile): String = AutomobileSerializableString(automobile)
  implicit def automobileToJValue(automobile: Automobile): JValue = AutomobileSerializableJValue(automobile)
}