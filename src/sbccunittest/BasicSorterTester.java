package sbccunittest;

import static java.lang.System.*;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.*;

import org.apache.commons.lang3.*;
import org.junit.*;

import sortcomparison.*;

/*
 * Unit test code to grade the sort comparison assignment.
 * 
 * @author spstrenn 10-Nov-2015
 */
public class BasicSorterTester {

	Sorter sorter;

	static final int INSERTION_SORT = 1;

	static final int MERGE_SORT = 2;

	static final int QUICK_SORT = 3;

	static final int HEAP_SORT = 4;

	final String newLine = System.getProperty("line.separator");

	static final int RANDOM_DATA = 0;

	static final int SORTED_DATA = 1;

	public static int totalScore = 0;

	public static int extraCredit = 0;


	@BeforeClass
	public static void beforeTesting() {
		totalScore = 0;
		extraCredit = 0;
	}


	@AfterClass
	public static void afterTesting() {
		out.println();
		out.println("Estimated score (w/o late penalties, etc.) = " + totalScore);
		out.println("Estimated extra credit (assuming on time submission) = " + extraCredit);
	}


	@Before
	public void setUp() throws Exception {
		sorter = new BasicSorter();
	}


	@After
	public void tearDown() throws Exception {
	}


	@Test(timeout = 15000)
	public void testQuickSort() {
		int wordLength = 5;
		int minN = 150000;
		int maxN = 600000;
		int stepN = 450000;
		int numSamplesPerTest = 3;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];
		double[] elapsedTimesSortedData = new double[numTests];

		standardSortTest(QUICK_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, true, 10, true,
				wordCounts, elapsedTimes);

		totalScore += 5;

		// Now verify that it handles sorted data sets properly.
		standardSortTest(QUICK_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, SORTED_DATA, true, 10, true,
				wordCounts, elapsedTimesSortedData);

		double sortedFactor = 2.0;
		if (elapsedTimesSortedData[elapsedTimesSortedData.length - 1] > sortedFactor
				* elapsedTimes[elapsedTimes.length - 1])
			fail("Sorted-data time limit exceeded.  Expected:  <= "
					+ String.format("%.3f", sortedFactor * elapsedTimes[elapsedTimes.length - 1]) + " sec, but was "
					+ String.format("%.3f", elapsedTimesSortedData[elapsedTimesSortedData.length - 1])
					+ " sec.  The time required to QuickSort " + wordCounts[wordCounts.length - 1]
					+ " already-sorted words must be less than " + sortedFactor
					+ " times the time to required to QuickSort " + wordCounts[wordCounts.length - 1]
					+ " words that are in random order.");

		totalScore += 5;
	}


	/**
	 * This is just a speed test for quicksort.
	 */
	// @Test(timeout = 15000)
	public void testQuickSortLarger() {
		int wordLength = 10;
		int minN = 1000000;
		int maxN = 1000000;
		int stepN = 1000000;
		int numSamplesPerTest = 10;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];

		standardSortTest(QUICK_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, true, 10, true,
				wordCounts, elapsedTimes);

	}


	/*
	 * This is just a speed test for merge sort.
	 */
	// @Test(timeout = 15000)
	public void testMergeSortLarger() {
		int wordLength = 10;
		int minN = 1000000;
		int maxN = 1000000;
		int stepN = 1000000;
		int numSamplesPerTest = 10;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];

		standardSortTest(MERGE_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, true, 10, true,
				wordCounts, elapsedTimes);
	}


	@Test(timeout = 15000)
	public void testPartition() {
		ArrayList<String> words = createRandomStrings(1024, 5);
		@SuppressWarnings("unchecked")
		ArrayList<String> expectedWords = (ArrayList<String>) words.clone();
		Collections.sort(expectedWords);

		int startNdx = 512;
		int segmentLength = 256;
		int indexOfPivot = sorter.partition(words, startNdx, segmentLength);
		// Verify that all values before indexOfPivot are <= words[indexOfPivot]
		for (int ndx = startNdx; ndx < indexOfPivot; ndx++)
			assertTrue(words.get(ndx).compareTo(words.get(indexOfPivot)) <= 0);

		// Verify that all values after indexOfPivot are >= words[indexOfPivot]
		for (int ndx = indexOfPivot + 1; ndx < (startNdx + segmentLength - 1); ndx++)
			assertTrue(words.get(ndx).compareTo(words.get(indexOfPivot)) >= 0);

		// Verify that a single partition does not result in a sorted array.
		words = createRandomStrings(100000, 5);
		sorter.partition(words, 0, 100000);
		int badNdx = verifySortOrder(expectedWords, words);
		assertFalse("The sorted data is out of order at index " + badNdx, badNdx == -1);

		totalScore += 10;
	}


	@Test(timeout = 15000)
	public void testMergeSort() {
		int wordLength = 5;
		int minN = 150000;
		int maxN = 600000;
		int stepN = 450000;
		int numSamplesPerTest = 3;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];
		double[] elapsedTimesSortedData = new double[numTests];

		standardSortTest(MERGE_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, true, 10, true,
				wordCounts, elapsedTimes);

		totalScore += 5;

		standardSortTest(MERGE_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, SORTED_DATA, true, 10, true,
				wordCounts, elapsedTimesSortedData);

		double sortedFactor = 0.5;
		if (elapsedTimesSortedData[elapsedTimesSortedData.length - 1] > sortedFactor
				* elapsedTimes[elapsedTimes.length - 1])
			fail("The elapsed time required to MergeSort " + wordCounts[wordCounts.length - 1]
					+ " already-sorted words must be less than " + sortedFactor + " times the time to MergeSort "
					+ wordCounts[wordCounts.length - 1] + " words that are in random order.");
		totalScore += 5;
	}


	@Test(timeout = 30000)
	public void testMerge() {
		ArrayList<String> originalWords = new ArrayList<String>();
		for (String s : new String[] { "M", "B", "Z", "A", "F", "D", "C", "P", "Q", "E", "V", "X" })
			originalWords.add(s);

		ArrayList<String> words = new ArrayList<String>();
		for (String s : new String[] { "M", "B", "Z", "A", "F", "D", "C", "P", "Q", "E", "V", "X" })
			words.add(s);

		ArrayList<String> expectedWords = new ArrayList<String>(words.subList(6, 12));
		Collections.sort(expectedWords);

		sorter.merge(words, 6, 3, 3);

		for (int ndx = 0; ndx < 6; ndx++)
			assertEquals(originalWords.get(ndx), words.get(ndx));

		int badNdx = verifySortOrder(expectedWords, words.subList(6, 12));
		assertTrue("The merged data is out of order at index " + badNdx, badNdx == -1);

		totalScore += 5;
	}


	@Test(timeout = 10000)
	public void testHeapSort() {
		int wordLength = 5;
		int minN = 150000;
		int maxN = 600000;
		int stepN = 450000;
		int numSamplesPerTest = 3;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];

		standardSortTest(HEAP_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, true, 10, true,
				wordCounts, elapsedTimes);

		extraCredit++;
	}


	@Test(timeout = 30000)
	public void testHeapify() {
		ArrayList<String> words = createRandomStrings(100000, 5); // 1e6 -> 0.66 sec, 4e6 -> 3.53
																	// sec
		sorter.heapify(words);
		// words[words.length - 1] = "ZZZZZ";
		verifyIsHeap(words, 0);

		extraCredit++;
	}


	@Test(timeout = 30000)
	public void testInsertionSort() {
		int wordLength = 5;
		int minN = 10000;
		int maxN = 40000;
		int stepN = 30000;
		int numSamplesPerTest = 2;
		int numTests = (maxN - minN) / stepN + 1;
		double[] wordCounts = new double[numTests];
		double[] elapsedTimes = new double[numTests];

		standardSortTest(INSERTION_SORT, wordLength, minN, maxN, stepN, numSamplesPerTest, RANDOM_DATA, false, 8.5,
				true, wordCounts, elapsedTimes);

		totalScore += 5;
	}


	void standardSortTest(int sortId, int wordLength, int minN, int maxN, int stepN, int numSamplesPerTest,
			int dataOrder, boolean ensureTimeRatioBelowThreshold, double lastFirstTimeRatioThreshold,
			boolean printResults, double[] wordCounts, double[] elapsedTimes) {

		ArrayList<String> words;
		double sumTime;
		int testNdx = 0;
		long startTime;
		double elapsedTime;

		if (printResults) {
			switch (sortId) {
			case INSERTION_SORT:
				out.print("\nInsertion Sort");
				break;

			case QUICK_SORT:
				out.print("\nQuick Sort");
				break;

			case MERGE_SORT:
				out.print("\nMerge Sort");
				break;

			case HEAP_SORT:
				out.print("\nHeap Sort");
				break;
			}
		}
		out.println(dataOrder == SORTED_DATA ? " sorted data" : " random data");

		for (int count = minN; count <= maxN; count += stepN) {
			sumTime = 0;
			for (int ndx = 0; ndx < numSamplesPerTest; ndx++) {
				words = createRandomStrings(count, wordLength); // 10000 -> 0.3 s, 40000 -> 4.3 s
				@SuppressWarnings("unchecked")
				ArrayList<String> expectedWords = (ArrayList<String>) words.clone();
				Collections.sort(expectedWords);
				if (dataOrder == SORTED_DATA)
					Collections.sort(words);

				startTime = System.nanoTime();
				switch (sortId) {
				case INSERTION_SORT:
					sorter.insertionSort(words, 0, words.size());
					break;

				case QUICK_SORT:
					sorter.quickSort(words, 0, words.size());
					break;

				case MERGE_SORT:
					sorter.mergeSort(words, 0, words.size());
					break;

				case HEAP_SORT:
					sorter.heapSort(words);
					break;
				}
				elapsedTime = (System.nanoTime() - startTime) / 1.0e9;
				sumTime += elapsedTime;
				int badNdx = verifySortOrder(expectedWords, words);
				assertTrue("The sorted data is out of order at index " + badNdx, badNdx == -1);
			}
			wordCounts[testNdx] = count;
			elapsedTimes[testNdx] = sumTime / numSamplesPerTest;
			// out.println("N = " + count + "\tTime: " + elapsedTimes[testNdx]);
			testNdx++;
		}

		double countRatio = wordCounts[elapsedTimes.length - 1] / wordCounts[0];
		double timeRatio = elapsedTimes[elapsedTimes.length - 1] / elapsedTimes[0];

		if (printResults) {
			out.println("\tN = " + wordCounts[0] + ", time = " + String.format("%.3f sec", elapsedTimes[0]));
			out.println("\tN = " + wordCounts[elapsedTimes.length - 1] + ", time = "
					+ String.format("%.3f sec", elapsedTimes[elapsedTimes.length - 1]));
			out.println("\tCount:  " + countRatio + "x,  Time: " + String.format("%.1f", timeRatio) + "x");
		}

		boolean failedTimeRatioRequirement = false;
		String failureMessage = "";

		if (ensureTimeRatioBelowThreshold) {
			if (timeRatio > lastFirstTimeRatioThreshold) {
				failedTimeRatioRequirement = true;
				failureMessage = "Expected the ratio of the time for " + wordCounts[wordCounts.length - 1]
						+ " data elements to the time for " + wordCounts[0] + " data elements to be <= "
						+ lastFirstTimeRatioThreshold + ", but it was measured as " + timeRatio + ".";
			}
		} else {
			if (timeRatio < lastFirstTimeRatioThreshold) {
				failedTimeRatioRequirement = true;
				failureMessage = "Expected the ratio of the time for " + wordCounts[wordCounts.length - 1]
						+ " data elements to the time for " + wordCounts[0] + " data elements to be >= "
						+ lastFirstTimeRatioThreshold + ", but it was measured as " + timeRatio + ".";
			}
		}

		if (failedTimeRatioRequirement) {
			StringBuilder sb = new StringBuilder();

			switch (sortId) {
			case INSERTION_SORT:
				sb.append("Insertion Sort").append(newLine);
				break;

			case QUICK_SORT:
				sb.append("Quick Sort").append(newLine);
				break;

			case MERGE_SORT:
				sb.append("Merge Sort").append(newLine);
				break;

			case HEAP_SORT:
				sb.append("Heap Sort").append(newLine);
				break;
			}

			for (int ndx = 0; ndx < elapsedTimes.length; ndx++) {
				sb.append("" + wordCounts[ndx] + "\t" + elapsedTimes[ndx]).append(newLine);
			}

			sb.append(failureMessage);
			fail(failureMessage);
		}

	}


	private void verifyIsHeap(ArrayList<String> words, int i) {
		if ((2 * i + 1) > words.size())
			return;
		if (words.get(i).compareTo(words.get(2 * i + 1)) < 0)
			fail("heapify() failed.  The parent (" + words.get(i) + ") is less than its left child ("
					+ words.get(2 * i + 1) + ").");

		if ((2 * i + 2) < words.size())
			if (words.get(i).compareTo(words.get(2 * i + 2)) < 0)
				fail("heapify() failed.  The parent (" + words.get(i) + ") is less than its right child ("
						+ words.get(2 * i + 2) + ").");

		verifyIsHeap(words, 2 * i + 1);
		if ((2 * i + 2) < words.size())
			verifyIsHeap(words, 2 * i + 2);
	}


	public ArrayList<String> createRandomStrings(int n, int wordLength) {
		ArrayList<String> words = new ArrayList<String>(n);
		for (int ndx = 0; ndx < n; ndx++)
			words.add(RandomStringUtils.randomAscii(wordLength));
		return words;
	}


	public int verifySortOrder(List<String> expectedWords, List<String> words) {
		int badNdx = -1;
		for (int ndx = 0; ndx < words.size(); ndx++) {
			if (words.get(ndx).compareTo(expectedWords.get(ndx)) != 0) {
				badNdx = ndx;
				break;
			}
		}

		return badNdx;
	}

}
