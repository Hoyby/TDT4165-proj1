class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {

      // Create a new transaction object
      // Put transaction object in the queue
      transactionsQueue.push(new Transaction(
        transactionsQueue,
        transactionsQueue,
        from,
        to,
        amount,
        allowedAttempts))

      // spawn a thread that calls processTransactions
      Main.thread(processTransactions)

    }

    private def processTransactions: Unit = {

      // Pop a transaction from the queue
      val transaction = transactionsQueue.pop

      // Spawns a thread to execute the transaction.
      transaction.run

      // Finally do the appropriate thing, depending on whether
      // the transaction succeeded or not
      if (transaction.attempt >= transaction.allowedAttempts) {
        transaction.status = TransactionStatus.FAILED
      } else if (transaction.status == TransactionStatus.PENDING) {
        print(transaction.attempt)
        transactionsQueue.push(transaction)
        processTransactions // retry
      } else { // success
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
