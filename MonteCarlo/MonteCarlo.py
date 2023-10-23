import threading
import random
import math
import time

BUFFER_SIZE = 5

class BoundedBuffer:
    def __init__(self):
        self.buffer = [None] * BUFFER_SIZE
        self.in_idx = 0
        self.out_idx = 0
        self.mutex = threading.Semaphore(1)
        self.empty = threading.Semaphore(BUFFER_SIZE)
        self.full = threading.Semaphore(0)

    def insert(self, item, producer_id):
        self.empty.acquire()
        self.mutex.acquire()
        self.buffer[self.in_idx] = item
        self.in_idx = (self.in_idx + 1) % BUFFER_SIZE
        self.mutex.release()
        self.full.release()

    def remove(self):
        self.full.acquire()
        self.mutex.acquire()
        item = self.buffer[self.out_idx]
        self.out_idx = (self.out_idx + 1) % BUFFER_SIZE
        self.mutex.release()
        self.empty.release()
        return item

def producer_function(buffer, producer_id):
    number_of_coordinates = 100
    circle_radius = 1
    circle_center_x = 1
    circle_center_y = 1
    points_inside_circle = 0
    
    for _ in range(number_of_coordinates):
        x_coordinate = random.random() * 2
        y_coordinate = random.random() * 2

        distance = math.sqrt((x_coordinate - circle_center_x) ** 2 + (y_coordinate - circle_center_y) ** 2)
        if distance <= circle_radius:
            points_inside_circle += 1
    
    pi = 4 * (points_inside_circle / number_of_coordinates)
    buffer.insert(pi, producer_id)
    print(f"Producer {producer_id} produced {pi}")

def consumer_function(buffer):
    while True:
        time.sleep(1)
        pi = buffer.remove()
        print(f"PI value is {pi}")

if __name__ == "__main__":
    buffer = BoundedBuffer()
    consumer_thread = threading.Thread(target=consumer_function, args=(buffer,))
    consumer_thread.start()

    producer_threads = []
    for i in range(10):
        producer_thread = threading.Thread(target=producer_function, args=(buffer, i))
        producer_threads.append(producer_thread)
        producer_thread.start()

    for thread in producer_threads:
        thread.join()
