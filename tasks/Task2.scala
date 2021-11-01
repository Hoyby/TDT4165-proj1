object Task2 extends App{
  /* Task 2a*/

  def initializeThread(task: () => Unit): Thread = {
    new Thread(() => task())
  }

  /* Task 2b */

  /* Had to modify the incr counter method, as it would always print the same "correct" output.
  *  Added looping to actually see that the results varied between runs. Probably due to the
  *  small amount of computation needed, as the thread would be finished before the main thread
  *  is able to start the next thread*/
  def increaseCounter(): Unit = {
    for (_ <- 0 to 5) {
      counter += 1
    }
  }

  def increaseCounterAtomic(): Unit = this.synchronized {
    for (_ <- 0 to 5) {
      counter += 1
    }
  }

  def printCounter(): Unit = {
    System.out.println(counter)
  }

  def runIncrPrint(incrMethodToUse: () => Unit): Unit = {
    val incrThreads = {
      val t1: Thread = initializeThread(incrMethodToUse)
      val t2: Thread = initializeThread(incrMethodToUse)
      List[Thread](t1, t2)
    }
    val t3: Thread = initializeThread(printCounter)
    incrThreads.foreach(t => t.start())

    /* Make sure threads are done before exiting printing */
    incrThreads.foreach(t => t.join())
    /* Start and wait for the print thread before exiting*/
    t3.start()
    t3.join()
  }

  def deadlock(): Unit ={
    // A deadlock occurs when two processes are waiting for a resource that is locked by the the other process.
    // An example:
    // Thread 1 (T1) wants resource 1 (R1) and resource 2 (R2).
    // Thread 2 (T2) also wants R1 and R2.
    // T1 locks R1 but gets interrupted by T2 locking R2.
    // Now both T1 and T2 are waiting for R2 and R1 respectively -> We have a deadlock.

    // Preventing deadlocks can be done by eliminating any of the 4 deadlock conditions:
    // 1. Mutual exclusion - Each resource is either currently allocated to exactly one process or it is available.
    //    (Two processes cannot simultaneously control the same resource or be in their critical section).
    // 2. Hold and Wait - processes currently holding resources can request new resources
    // 3. No preemption - Once a process holds a resource, it cannot be taken away by another process or the kernel.
    // 4. Circular wait - Each process is waiting to obtain a resource which is held by another process.

    object A {
      lazy val n: Int = 10
      lazy val x: Int = B.y
    }

    object B {
      lazy val y: Int = A.n
    }


    val threads = {
      val t1: Thread = initializeThread(() => println(A.x))
      val t2: Thread =  initializeThread(() => println(B.y))
      List[Thread](t1, t2)
    }
    threads.foreach(t => t.start())
    threads.foreach(t => t.join())
  }

  /* Running task 2b and c*/
  private var counter: Int = 0
  //b, non-atomic
  runIncrPrint(increaseCounter)
  //c, atomic
  counter = 0
  runIncrPrint(increaseCounterAtomic)

  /* Uncomment for deadlock test */
  //deadlock()
}