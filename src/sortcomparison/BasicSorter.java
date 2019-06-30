package sortcomparison;

import java.util.*;

// ADD CODE HERE
public class BasicSorter implements Sorter {

	@Override
	public void insertionSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		int outerLoop, innerLoop;

		if (firstIndex + numberToSort > data.size()) {
			System.out.println("firstIndex + numberToSort exceeds data.size()");
		} else {
			for (outerLoop = 1 + firstIndex; outerLoop < numberToSort; outerLoop++) {
				// if (data.get(outerLoop).compareTo(data.get(outerLoop - 1)) < 0) {
				for (innerLoop = 0 + firstIndex; innerLoop < outerLoop; innerLoop++) {
					if (data.get(outerLoop).compareTo(data.get(innerLoop)) <= 0) {
						Collections.swap(data, outerLoop, innerLoop);

					}
				}

			}
		}
	}


	@Override
	public void quickSort(ArrayList<String> data, int firstIndex, int numberToSort) {
		if (numberToSort <= 15) {
			insertionSort(data, firstIndex, numberToSort);
		}
		if (numberToSort > 1) {
			int center = partition(data, firstIndex, numberToSort);
			quickSort(data, firstIndex, center - firstIndex);
			quickSort(data, center + 1, (numberToSort - (center - firstIndex) - 1));
		}
	}
	/*
	 * FURTHER THE ALPHABET DOWN THE LINE: THE GREATER IT IS SO A < Z: Z IS GREATER
	 */


	@Override
	public int partition(ArrayList<String> data, int firstIndex, int numberToPartition) {
		// // median of 3 hack
		int middleIndex = firstIndex + numberToPartition / 2;
		Collections.swap(data, middleIndex, firstIndex);
		String pivot = data.get(firstIndex);

		// // ---
		int tooBigNdx = firstIndex;
		int tooSmallNdx = firstIndex + numberToPartition - 1;

		while (tooBigNdx < tooSmallNdx) {
			while (tooBigNdx < tooSmallNdx && data.get(tooBigNdx).compareTo(pivot) <= 0)
				tooBigNdx++;
			while (tooSmallNdx > firstIndex && data.get(tooSmallNdx).compareTo(pivot) > 0)
				tooSmallNdx--;
			if (tooBigNdx < tooSmallNdx)
				Collections.swap(data, tooBigNdx, tooSmallNdx);

		}
		if (pivot.compareTo(data.get(tooSmallNdx)) >= 0) {
			Collections.swap(data, firstIndex, tooSmallNdx);
			return tooSmallNdx;
		} else
			return firstIndex;
	}


	@Override
	public void mergeSort(ArrayList<String> data, int firstIndex, int numberToSort) {

		if (numberToSort <= 0)
			return;
		// if (numberToSort <= 16)
		// insertionSort(data, firstIndex, numberToSort);

		// else {
		int midRise = firstIndex + numberToSort / 2;
		int headOfLeft = firstIndex;
		int tailOfLeft = midRise - 1;
		int headOfRight = midRise;
		int tailOfRight = firstIndex + numberToSort - 1;
		int sizeOfLeft = midRise - firstIndex;
		int sizeOfRight = tailOfRight - midRise + 1;

		ArrayList<String> leftArray = new ArrayList<>(sizeOfLeft);
		ArrayList<String> rightArray = new ArrayList<>(sizeOfRight);


		for (int i = headOfLeft; i <= tailOfLeft; i++)
			leftArray.add(data.get(i));
		for (int i = headOfRight; i <= tailOfRight; i++)
			rightArray.add(data.get(i));


		if (numberToSort > 1) {
			mergeSort(data, headOfLeft, sizeOfLeft);
			mergeSort(data, headOfRight, sizeOfRight);
			if (!(data.get(midRise - 1).compareTo(data.get(midRise)) <= 0))
				merge(data, headOfLeft, sizeOfLeft, sizeOfRight);
		}
	}


	@Override
	public void merge(ArrayList<String> data, int firstIndex, int leftSegmentSize, int rightSegmentSize) {
		if (leftSegmentSize == 0 || rightSegmentSize == 0)
			return;
		ArrayList<String> leftArray = new ArrayList<>(leftSegmentSize);
		ArrayList<String> rightArray = new ArrayList<>(rightSegmentSize);
		ArrayList<String> temp = new ArrayList<>();

		int headOfLeftSegment = firstIndex;
		int tailOfLeftSegment = firstIndex + leftSegmentSize - 1;
		int headOfRightSegment = firstIndex + leftSegmentSize;
		int tailOfRightSegment = firstIndex + leftSegmentSize + rightSegmentSize - 1;
		// adds data to left and right arrays
		for (int i = headOfLeftSegment; i <= tailOfLeftSegment; i++)
			leftArray.add(data.get(i));
		for (int i = headOfRightSegment; i <= tailOfRightSegment; i++)
			rightArray.add(data.get(i));
		int leftInitial = 0;
		int rightInitial = 0;
		while (leftArray.size() >= 1 && rightArray.size() >= 1) {
			// checks if array placement is not outside boundaries on left side
			if (leftInitial < leftSegmentSize && rightInitial < rightSegmentSize) {
				if (leftArray.get(leftInitial).compareTo(rightArray.get(rightInitial)) <= 0) {
					temp.add(leftArray.get(leftInitial)); // adds head index from the left array to temp
					leftInitial++; // increments left index to next place holder
				} else {
					// checks if array placement is not outside boundaries on right side
					temp.add(rightArray.get(rightInitial)); // adds head index from right array to the result
					rightInitial++;
				}
			} else {
				/// if the right/left array has ran out of indexes. copy the rest to temp
				for (int i = rightInitial; i < rightArray.size(); i++)
					temp.add(rightArray.get(i));
				for (int i = leftInitial; i < leftArray.size(); i++)
					temp.add(leftArray.get(i));
				break;
			}
		}
		// insert it back into data starting from the first index and incrementing
		for (int i = 0; i < temp.size(); i++)
			data.set(firstIndex++, temp.get(i));
	}


	@Override
	public void heapSort(ArrayList<String> data) {
		// TODO Auto-generated method stub
		mergeSort(data, 0, data.size());
	}


	@Override
	public void heapify(ArrayList<String> data) {

	}

}
