package project;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class ElevatorOptimization {
	public static int numOfStops;
	public static int[] floorArray;
	public static int[] peoplePerFloorArray;
	public static ArrayList<Integer> stoppedFloorArray = new ArrayList<>();
	public static int[] unvisitedFloorArray;

	public static void main(String[] args) {
		ElevatorOptimization elevator = new ElevatorOptimization();
		elevator.getData();
		System.out.println();

		System.out.print("Floors requested: ");
		printList(floorArray);

		System.out.print("Number of People Each Floor: ");
		printList(peoplePerFloorArray);

		optimizeGreedy();
	}
	

	// Prints the floors it stops in along with the total cost of walking.
	private static void optimizeGreedy() {
		if (stoppedFloorArray == null) {
			stoppedFloorArray = new ArrayList<>(); // Safety check
		}

		// Shows 0 if it has not stopped at that floor otherwise Shows 1. Shows floor between the lowest and highest floor requested.
		unvisitedFloorArray = new int[(floorArray[floorArray.length - 1] - floorArray[0]) + 1];

		// Initializing Total cost of walking up or down the stairs.
		int totalCost = 0;

		// Loop is Finding all the best floor to stop at.
		while (numOfStops != 0) {

			// Initializing The floor with the minimum cost of walking.
			int currentMinCost = Integer.MAX_VALUE;
			int currentMinFloor = floorArray[0];

			// The Loop Finds the current best floor to stop at. (Takes the locally optimal floor).
			for (int i = 0; i < unvisitedFloorArray.length; i++) {
				
				// Skips calculating that floor if it is already visited/stopped.
				if (unvisitedFloorArray[i] == 1)
					continue;

				int currentCost = 0;

				for (int j = 0; j < floorArray.length; j++) {
					
					// Skips calculating the floor in which its cost has been calculated in Line 80.
					if (peoplePerFloorArray[j] == 0) {
						continue;
					}

					// Difference in levels between requested floor and currently checked floor.
					int differenceBetweenFloor = Math.abs(floorArray[0] + i - floorArray[j]);
					int costOfWalkingToFloorI = differenceBetweenFloor * peoplePerFloorArray[j];
					
					costOfWalkingToFloorI += adjustCostForSparseHighRanges(floorArray[j], differenceBetweenFloor);

					currentCost += costOfWalkingToFloorI;
				}

				// if the currently checked floor has the minimum total cost of all the
				// requested Floors in which its cost has not been calculated in Line 80 it
				// becomes the current best floor for now.
				if (currentCost < currentMinCost) {
					currentMinCost = currentCost;
					currentMinFloor = floorArray[0] + i;
				}
			}

			// Finds the closest requested Floor to the current best stopped floor
			// (currentMinFloor) and calculates its
			// walking cost to the current best stopped floor (currentMinFloor) and adds to
			// the totalCost
			int[] closestFloorAndCost = findClosestFloor(currentMinFloor);
			int index = getIndex(closestFloorAndCost[0], floorArray);
			totalCost += closestFloorAndCost[1];
			
			// Sets the closest Floor weight (no. of people) to 0 to show that its cost has been calculated.
			peoplePerFloorArray[index] = 0; 

			// Finds position of the best stopped floor (currentMinFloor) in the
			// unvistedArrayFloor and convert its
			// index to 1 showing that it is visited/stopped.
			int position = currentMinFloor - floorArray[0];
			unvisitedFloorArray[position] = 1;

			// Adds the current best floor to stop at.
			stoppedFloorArray.add(currentMinFloor);

			// Decrements number of stops since one stopped Floor has been calculated.
			numOfStops--;
		}

		// Sorts the stopped Floors
		Collections.sort(stoppedFloorArray);

		totalCost += remainingCost();

		// Prints result.
		System.out.println("Stopped Floors: " + stoppedFloorArray);
		System.out.println("Total Cost: " + totalCost);
	}
	
	// Adjusting cost for high-range floors, considering them more strategically if they are distant
	private static int adjustCostForSparseHighRanges(int floor, int distance) {
		// Threshold for considering a floor "high-range"
		int threshold = (floorArray[floorArray.length - 1] - floorArray[0]) / 4; 
		if (floor > floorArray[floorArray.length - 1] - threshold) {
			return distance * 2; // Increasing the cost penalty for floors beyond the threshold
		}
		return 0;
	}

	
	private static int[] findClosestFloor(int currentMinFloor) {
		int closestStopDistance = Integer.MAX_VALUE;
		int closestFloor = -1;
		for (int i = 0; i < floorArray.length; i++) {
			if (peoplePerFloorArray[i] == 0)
				continue;
			int distance = Math.abs(currentMinFloor - floorArray[i]);
			if (distance < closestStopDistance) {
				closestStopDistance = distance;
				closestFloor = floorArray[i];
			}
		}
		return new int[] { closestFloor, closestStopDistance };
	}

	
	private static int getIndex(int value, int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == value) {
				return i;
			}
		}
		return -1;
	}

	
	private static int remainingCost() {
		int totalCost = 0;

		for (int i = 0; i < floorArray.length; i++) {
			if (peoplePerFloorArray[i] == 0) {
				continue;
			}
			int floor = floorArray[i];

			int closestStopDistance = Integer.MAX_VALUE;
			for (int stop : stoppedFloorArray) {
				int distance = Math.abs(stop - floor);
				if (distance < closestStopDistance) {
					closestStopDistance = distance;
				}
			}

			int floorCost = closestStopDistance * peoplePerFloorArray[i];
			totalCost += floorCost;
		}

		return totalCost;
	}

	
	// Asks user input for requested floors and the number of people that requested
	// for it.
	private void getData() {
		Scanner scanner = new Scanner(System.in);

		// Prompt and validate the number of floors requested
		System.out.println();
		System.out.print("Enter the number of floors requested: ");
		int numOfFloors = scanner.nextInt();
		while (numOfFloors <= 0) {
			System.out.println("The number of floors must be greater than zero. Please enter a valid number: ");
			numOfFloors = scanner.nextInt();
		}
		System.out.println();

		floorArray = new int[numOfFloors];
		peoplePerFloorArray = new int[numOfFloors];

		for (int i = 0; i < numOfFloors; i++) {
			System.out.print("Enter the requested floor (Request No.: " + (i + 1) + "): ");
			int floorRequest = scanner.nextInt();

			// Validate the floor request is not zero
			while (floorRequest <= 0) {
				System.out.println();
				System.out.print("Floor number must be greater than zero. Please enter a valid floor: ");
				floorRequest = scanner.nextInt();
			}

			// Check if the floor has already been requested by searching the existing
			// entries in the array
			while (isFloorRequested(floorArray, floorRequest, i)) {
				System.out.println();
				System.out.print("This floor has already been requested. Please enter a different floor: ");
				floorRequest = scanner.nextInt();
			}

			floorArray[i] = floorRequest;
			System.out.println();
			System.out.print("Enter the number of people requested for floor " + floorArray[i] + ": ");
			peoplePerFloorArray[i] = scanner.nextInt();

			// Validate the number of people is not zero
			while (peoplePerFloorArray[i] <= 0) {
				System.out.println();
				System.out.println("The number of people must be greater than zero. Please enter a valid number: ");
				peoplePerFloorArray[i] = scanner.nextInt();
			}

			System.out.println();
		}

		sortArrays(numOfFloors);

		System.out.print("Enter the number of stops: ");
		numOfStops = scanner.nextInt();
		System.out.println();

		// Ensure that the number of stops is greater than zero and does not exceed the
		// number of unique floors requested
		while (numOfStops <= 0 || numOfStops > numOfFloors) {
			if (numOfStops <= 0) {
				System.out.println("The number of stops must be greater than zero. Please enter a valid number:");
			} else {
				System.out.println(
						"The number of stops cannot exceed the number of floors requested. Please enter a valid number of stops:");
			}
			numOfStops = scanner.nextInt();
		}
		scanner.close();
	}

	
	// Check if a floor has already been requested
	private boolean isFloorRequested(int[] floors, int floor, int currentIndex) {
		for (int j = 0; j < currentIndex; j++) {
			if (floors[j] == floor) {
				return true;
			}
		}
		return false;
	}

	
	// Organizes the two arrays so that each corresponding value means the Requested
	// Floor has n people.
	public static void sortArrays(int size) {
		if (size < 2) {
			return; // No need to sort
		}
		int mid = size / 2;
		int[] leftFloors = new int[mid];
		int[] rightFloors = new int[size - mid];
		int[] leftPeople = new int[mid];
		int[] rightPeople = new int[size - mid];

		// Dividing the arrays into two halves
		for (int i = 0; i < mid; i++) {
			leftFloors[i] = floorArray[i];
			leftPeople[i] = peoplePerFloorArray[i];
		}
		for (int i = mid; i < size; i++) {
			rightFloors[i - mid] = floorArray[i];
			rightPeople[i - mid] = peoplePerFloorArray[i];
		}
		sortArrays(leftFloors, leftPeople, mid);
		sortArrays(rightFloors, rightPeople, size - mid);

		// Merging the sorted halves
		merge(floorArray, peoplePerFloorArray, leftFloors, rightFloors, leftPeople, rightPeople, mid, size - mid);
	}

	
	// Recursive sort function
	private static void sortArrays(int[] floors, int[] people, int size) {
		if (size < 2) {
			return;
		}
		int mid = size / 2;
		int[] leftFloors = new int[mid];
		int[] rightFloors = new int[size - mid];
		int[] leftPeople = new int[mid];
		int[] rightPeople = new int[size - mid];

		for (int i = 0; i < mid; i++) {
			leftFloors[i] = floors[i];
			leftPeople[i] = people[i];
		}
		for (int i = mid; i < size; i++) {
			rightFloors[i - mid] = floors[i];
			rightPeople[i - mid] = people[i];
		}
		sortArrays(leftFloors, leftPeople, mid);
		sortArrays(rightFloors, rightPeople, size - mid);

		merge(floors, people, leftFloors, rightFloors, leftPeople, rightPeople, mid, size - mid);
	}

	
	// Merging the sorted arrays back together
	private static void merge(int[] floors, int[] people, int[] leftFloors, int[] rightFloors, int[] leftPeople,
			int[] rightPeople, int left, int right) {
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
			if (leftFloors[i] <= rightFloors[j]) {
				floors[k] = leftFloors[i];
				people[k] = leftPeople[i];
				i++;
			} else {
				floors[k] = rightFloors[j];
				people[k] = rightPeople[j];
				j++;
			}
			k++;
		}
		while (i < left) {
			floors[k] = leftFloors[i];
			people[k] = leftPeople[i];
			i++;
			k++;
		}
		while (j < right) {
			floors[k] = rightFloors[j];
			people[k] = rightPeople[j];
			j++;
			k++;
		}
	}

	
	private static void printList(int[] array) {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if (i < array.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}
}