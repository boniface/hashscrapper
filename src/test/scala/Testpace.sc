// Step 1
trait Formatter[A] {
  def fmt(a:A): String
}

// Step 2

object instances{
  implicit val float: Formatter[Float] ={
    (a:Float) => s"$a f"
  }
}

