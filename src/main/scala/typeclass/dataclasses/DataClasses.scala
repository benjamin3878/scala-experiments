package scala.typeclass.dataclasses

/**
  * Created by Ben on 6/30/2017.
  */
object DataClasses {

  case class Automobile(manufacturer: String, model: String)
  case class Person(name: String, age: Int, automobiles: List[Automobile])

}
