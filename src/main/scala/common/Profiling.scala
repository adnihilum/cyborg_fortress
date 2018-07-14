package common

object Profiling {
  def time[R](block: => R): (Int, R) = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    val time = (t1 - t0) / 1000000
    (time.toInt, result)
  }
}
