import org.apache.spark.sql.SparkSession

object  Executor extends App {

  val spark = SparkSession.builder().appName("test").getOrCreate()

  spark.sql("select 1").show(100,false)

}
