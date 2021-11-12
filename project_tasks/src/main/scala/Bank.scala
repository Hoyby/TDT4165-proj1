import scala.annotation.tailrec

class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {

      // Create a new transaction object
      // Put transaction object in the queue
      transactionsQueue.push(new Transaction(
        transactionsQueue,
        processedTransactions,
        from,
        to,
        amount,
        allowedAttempts))

      // spawn a thread that calls processTransactions
      new Thread(() => processTransactions()).start()

    }

    @tailrec
    private def processTransactions(): Unit = {

      // Pop a transaction from the queue
      val transaction = transactionsQueue.pop

      // Spawns a thread to execute the transaction.
      val transactionThread = new Thread(transaction)
      transactionThread.start()
      transactionThread.join()

      // Finally do the appropriate thing, depending on whether
      // the transaction succeeded or not

      if (transaction.status == TransactionStatus.PENDING) {
        transactionsQueue.push(transaction)
        processTransactions() // retry
      } else { // success or failed
        //processedTransactions.push(transaction)
        processedTransactions.push(transaction)
      }
    }


    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
