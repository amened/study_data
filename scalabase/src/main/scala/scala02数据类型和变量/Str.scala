package scala02数据类型和变量

import scala.io.StdIn

object Str {
  def main(args: Array[String]): Unit = {
     var str =
       """
         | select
         | *
         | from
         | user
         |""".stripMargin
    print(str)
  }
}
