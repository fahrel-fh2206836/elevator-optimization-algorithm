# Elevator Optimization Algorithm
A Java-based elevator optimization algorithm utilizing the Greedy Algorithm technique aims
to minimize the total number of floors individuals must walk, either upward or downward,
from a floor that the elevator stops in while ensuring that each run does not exceed 
𝑘 stops.

## Task specification

**Specifications:**

1. Riders enter their intended destinations at the beginning of the trip.
2. The elevator decides which floors to stop at along the way, limiting to at most k stops on any
given run.
3. The goal is to minimize the total number of floors people have to walk either up or down.
4. Break ties among equal-cost solutions by giving preference to stopping the elevator at the
lowest floor possible.
5. The elevator is smart enough to know how many people want to get off at each floor.

The objective is to optimize the elevator's stop sequence to reduce the overall walking distance for passengers.

## Problem Description (Case Study)

Assume that you work in a very tall building with a slow elevator. The
elevator is frustratingly interrupted when people press buttons for many neighboring floors during
a trip. Encountering interruptions during an upward journey becomes especially frustrating when
individuals press buttons for several neighboring floors, like 13, 14, and 15. The ascent is repeatedly
interrupted three times, once at each of these floors. A more considerate approach would be for those
passengers to collectively agree to press only the button for floor 14. Subsequently, individuals on
floors 13 and 15 could opt to use the stairs for a single floor, taking advantage of the opportunity to
walk. We presume that the cost of ascending a flight of stairs is equivalent to descending -
considering the health benefits of exercise. To resolve ties between solutions with equal costs,
management suggests prioritizing elevator stops at the lowest floor possible, as this consumes less
electricity. Importantly, the elevator is not obligated to stop precisely at the floors specified by riders;
for instance, if riders indicate floors 27 and 29, the elevator can choose to stop at floor 28 instead.

## More about the Algorithm
- Java Source Code: **_Elevator-Optimizer-Algorithm.zip_**
- Algorithm Documentation: **_Algorithm-Report.docx_**

## Setup and Launch

### Prerequisites
- Java SE Development Kit (JDK) already installed on your device ([JDK Installation](https://www.oracle.com/java/technologies/downloads/))
- Install Eclipse IDE ([Eclipse Installation Guide](https://www.geeksforgeeks.org/how-to-install-eclipse-ide-for-java/)) or any IDE of your choice.

### Setup

1. Clone the repository
    ```bash
    git clone https://github.com/fahrel-fh2206836/elevator-optimization-algorithm.git
    ```

2. Import Java Project into IDE, the java project was originally developed using Eclipse IDE.

    - Using Eclipse IDE:
        1. Import the **_Elevator-Optimizer-Algorithm.zip_** in Eclipse.
        2. Run the Java Program.

    - Using another IDE:
        1. Extract **_Elevator-Optimizer-Algorithm.zip_** file.
        2. Navigate to **_Elevator-Greedy-Optimizer\src\project\ElevatorOptimization.java_**.
        3. Create a Java Project in the IDE of your choice and paste the **_ElevatorOptimization.java_** file.
        4. If you have pasted the file in a package, replace line 1 of **_ElevatorOptimization.java_** with `package (your package name);`. Otherwise, remove line 1.

## Contributors
- Fahrel Azki Hidayat
- Marcus Wein Monteiro





