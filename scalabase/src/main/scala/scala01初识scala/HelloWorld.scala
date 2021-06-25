package scala01初识scala

class HelloWorld {
  private var name: String = null;
}

object HelloWorld{
  def main(args: Array[String]): Unit = {
    val world = new HelloWorld
    print(world.name)
  }
}
