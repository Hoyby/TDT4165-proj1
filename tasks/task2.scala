object Task2 extends App{


  def initializeThread(task: () => Unit): Thread ={
    new Thread(() => task())
  }


  def deadlock(): Unit ={
  // A deadlock occurs when two processes are waiting for a resource that is locked by the the other process.
  // An example:
  // Thread 1 (T1) wants resource 1 (R1) and resource 1 (R2).
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

    initializeThread(() => println(A.x)).start()
    initializeThread(() => println(B.y)).start()
  }

}