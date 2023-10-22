import threading
import time

class DiningPhilosophers:
    def __init__(self):
        self.lock = threading.RLock()
        self.states = [self.State.THINKING] * 5
        self.self_conditions = [threading.Condition(self.lock) for _ in range(5)]

    class State:
        THINKING = 0
        HUNGRY = 1
        EATING = 2

    def test(self, i):
        with self.lock:
            left = (i + 4) % 5
            right = (i + 1) % 5
            if (self.states[left] != self.State.EATING and
                    self.states[right] != self.State.EATING and
                    self.states[i] == self.State.HUNGRY):
                self.states[i] = self.State.EATING
                self.self_conditions[i].notify()

    def take_forks(self, i):
        with self.lock:
            self.states[i] = self.State.HUNGRY
            self.test(i)
            while self.states[i] != self.State.EATING:
                self.self_conditions[i].wait()

    def return_forks(self, i):
        with self.lock:
            self.states[i] = self.State.THINKING
            self.test((i + 4) % 5)
            self.test((i + 1) % 5)
            for condition in self.self_conditions:
                condition.notify()  # Notify all philosophers

# Example usage
def philosopher(id, dining_philosophers):
    while True:
        print(f"Philosopher {id} is thinking")
        time.sleep(1)  # Simulate thinking
        print(f"Philosopher {id} wants to eat")
        dining_philosophers.take_forks(id)
        print(f"Philosopher {id} is eating")
        time.sleep(1)  # Simulate eating
        print(f"Philosopher {id} is done eating")
        dining_philosophers.return_forks(id)

if __name__ == "__main__":
    dining_philosophers = DiningPhilosophers()
    philosophers = []
    for i in range(5):
        philosopher_thread = threading.Thread(target=philosopher, args=(i, dining_philosophers))
        philosopher_thread.start()
        philosophers.append(philosopher_thread)

    for philosopher_thread in philosophers:
        philosopher_thread.join()
