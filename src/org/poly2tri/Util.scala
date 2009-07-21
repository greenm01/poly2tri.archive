
package org.poly2tri

import scala.collection.mutable.ArrayBuffer

object Util {
  
  // From "Scala By Example," by Martin Odersky
  def msort[A](less: (A, A) => Boolean)(xs: List[A]): List[A] = {
    def merge(xs1: List[A], xs2: List[A]): List[A] =
      if (xs1.isEmpty) xs2
      else if (xs2.isEmpty) xs1
      else if (less(xs1.head, xs2.head)) xs1.head :: merge(xs1.tail, xs2)
      else xs2.head :: merge(xs1, xs2.tail)
      val n = xs.length/2
      if (n == 0) xs
      else merge(msort(less)(xs take n), msort(less)(xs drop n))
  }
  
  def insertionSort(values : List[Point], matcher : (Float, Float) => Boolean) : List[Point] = {
       def iSort(values : List[Point]) : List[Point] = {
           val result = values match {
               case List() => List()
               case value :: valuesTail => insert(value, iSort(valuesTail))
           }
           result
       }
       def insert(value : Point, values : List[Point]) : List[Point] = {
           val result = values match {
               // if list is empty return new list with single element in it
               case List() => List(value)
               // otherwise insert into list in order, recursively
               case x :: xTail =>
                   if (matcher(value.x, x.x)) 
                       value :: values
                   else 
                       x :: insert(value, xTail)
           }
           result
       }
       iSort(values)
   }
}

/** The object <code>Random</code> offers a default implementation
 *  of scala.util.Random and random-related convenience methods.
 *
 *  @since 2.8
 *  From Scala 2.8 standard library
 */
object Random extends scala.util.Random {
  
  /** Returns a new sequence in random order.
   *  @param  seq   the sequence to shuffle
   *  @return       the shuffled sequence
   */
  def shuffle[T](buf: ArrayBuffer[T]): ArrayBuffer[T] = {
    // It would be better if this preserved the shape of its container, but I have
    // again been defeated by the lack of higher-kinded type inference.  I can
    // only make it work that way if it's called like
    //   shuffle[Int,List](List.range(0,100))
    // which nicely defeats the "convenience" portion of "convenience method".
       
    def swap(i1: Int, i2: Int) {
      val tmp = buf(i1)
      buf(i1) = buf(i2)
      buf(i2) = tmp
    }
   
    for (n <- buf.length to 2 by -1) {
      val k = nextInt(n)
      swap(n - 1, k)
    }
    buf
  } 
}