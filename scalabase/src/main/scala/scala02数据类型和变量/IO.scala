package scala02数据类型和变量

import java.io.FileOutputStream
import scala.io.Source

object IO {
  def main(args: Array[String]): Unit = {
    Source.fromFile("scalabase/src/main/resources/test.txt").foreach(print)

    var ouputFile = new FileOutputStream("scalabase/src/main/resources/test.txt", true);
    ouputFile.write("hello".getBytes());
  }
}
