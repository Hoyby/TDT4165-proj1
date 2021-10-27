object Task1 extends App{

  def subtask_a: Array[Int] ={
    // Generate an array containing the values 1 up to (and including 50 using a for loop.
    var arr: Array[Int] = Array()
    for(i <- 1 to 50) arr :+= i
    arr
  }


  def subtask_b(arr:Array[Int]): Int ={
    // Create a function that sums the elements in an array of integers using a for loop. (13p)
    var result: Int = 0
    arr.foreach(result += _)
    result
  }

  def subtask_c(arr:Array[Int]): Int ={
    // Create a function that sums the elements in an array of integers using recursion.
    if(arr.isEmpty) 0
    else arr(0) + subtask_c(arr.drop(1))
  }

  def subtask_d(n: BigInt): BigInt ={
    // Create a function to compute the nth Fibonacci number using recursion without using memoization
    //(or other optimizations). Use BigInt instead of Int. What is the difference between these two data
    //types?
    if (n <= 1) n
    else subtask_d(n-1) + subtask_d(n-2)
  }

  //println(subtask_b(subtask_a)) // prints 1275
  //println(subtask_c(subtask_a)) // prints 1275
  //println(subtask_d(10)) // prints 55

  // BigInt is 8 byte whereas Int is 4 bytes. This results in a bigger range and more memory usage, but
  // prevents overflow when doing arithmetic with bigger numbers.

}