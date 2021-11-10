import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
      if (amount > balance.amount) {
        Right("Cannot withdraw more than current balance.")
      }
      else if (amount <= 0) {
        Right("Cannot withdraw 0 or negative amount.")
      }
      else {
        this.balance.amount -= amount
        Left()
      }
    }
    def deposit (amount: Double): Either[Unit, String] = this.synchronized {
      if (amount <= 0) {
        Right("Cannot deposit 0 or negative amount")
      }
      else {
        this.balance.amount += amount
        Left()
      }
    }
    def getBalanceAmount: Double = this.synchronized {
      this.balance.amount
    }

    def transferTo(account: Account, amount: Double): Unit = {
        bank addTransactionToQueue (this, account, amount)
    }


}
