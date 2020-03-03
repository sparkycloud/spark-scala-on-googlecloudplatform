object SampleRDD {

  def tokenize(list:List[Int]) = {
    list.map(x=>List(x))
  }
}
