package scala.sideeffect

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.generic.CanBuildFrom

object SideEffect {

  /*

  // needs an ec
  // def map[S](f: T => S)(implicit executor: ExecutionContext): Future[S]
  implicit class SideFuture[A](f: Future[A])(implicit ec: ExecutionContext) {
    def withSideEffect(f: A => Unit): Future[A] = this.f.map { a => f(a); a }
  }

  // def map[B](f: A => B): Option[B]
  implicit class SideOption[A](f: Option[A]) {
    def withSideEffect(f: A => Unit): Option[A] = this.f.map { a => f(a); a }
  }

  // needs a higher kind to return the correct subtype
  // def map[B, That](f: A => B)(implicit bf: CanBuildFrom[Repr, B, That]): That
  implicit class SideTraversable[A, Box[B] <: Traversable[B]](f: Box[A])(implicit bf: CanBuildFrom[Traversable[A], A, Box[A]]) {
    def withSideEffect(f: A => Unit): Box[A] = this.f.map[A, Box[A]] { a => f(a); a }
  }

*/


  trait Functor[A, Boxed[_]] {
    def map[B](f: A => B): Boxed[B]
  }

  implicit def OptionToFunctor[A](o: Option[A]): Functor[A, Option] = new Functor[A, Option] {
    override def map[B](f: (A) => B): Option[B] = o.map(f)
  }


  implicit class SideFunctor[A, Box[_]](box: Box[A])(implicit vb: Box[A] => Functor[A, Box]) {
    def withSideEffect(f: A => Unit): Box[A] = box.map { ele => f(ele); ele }
  }

}
