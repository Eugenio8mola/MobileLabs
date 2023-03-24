import java.util.concurrent.locks.ReentrantLock

class BankAccount {
    val lock = ReentrantLock()
    var closed = false
    var balance : Long = 0
        get() = if(closed) throw IllegalStateException() else field

    fun adjustBalance(amount: Long){
        if(closed) throw IllegalStateException("Trying to access a Closed account")
        lock.lock()
        try {
            if(amount<0 && balance< amount) throw IllegalArgumentException("Balance Insufficient")
            else
            balance += amount
        } finally {
            lock.unlock()
        }
    }
    fun close() {
        closed = true    //change account state
    }
}
