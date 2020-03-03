
import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FunSuite

class ExecutorTest extends FunSuite with SharedSparkContext{

  test("Test RDD output") {
    val input = List(1,2,3,4)
    val expected = List(List(1),List(2),List(3),List(4))
    assert(SampleRDD.tokenize(sc.parallelize(input).collect().toList) === expected)

    sc.parallelize(List(1,2,3,4)).mapPartitionsWithIndex(
  }

}
