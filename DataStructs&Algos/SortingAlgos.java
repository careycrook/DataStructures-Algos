import java.util.LinkedList;
import java.util.Random;

/**
  * Sorting implementation
  * @author Carey Crook
  * @version 1.0
  */
public class SortingAlgos implements SortingInterface {


    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        boolean flag = true;
        int counter = 0;
        while (flag && counter < arr.length) {
            flag = false;
            for (int j = 1; j < arr.length - counter; j++) {
                if (arr[j - 1].compareTo(arr[j]) > 0) {
                    T temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    flag = true;
                }
            }
            counter++;
        }
    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            T key = arr[i];
            while (j > -1 && key.compareTo(arr[j]) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[index].compareTo(arr[j]) > 0) {
                    index = j;
                }
            }
            if (!arr[index].equals(arr[i])) {
                T temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
            }
        }
    }

    /**
     * Swaps the two data in the passed idexes
     * @param arr the array that is being swapped
     * @param i the first index
     * @param j the second index
     * @param <T> generic type
     */
    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Recursive method used for quicksort
     * @param arr the array that is being sorted
     * @param r random number generator
     * @param start the index of the first value that is being sorted
     * @param end the index of the last value that is being sorted
     * @param <T> generic type that extends comparable
     */
    private <T extends  Comparable<? super T>> void quicksort(
            T[] arr, Random r, int start, int end) {
        int low = start;
        int high = end;
        int pivot = r.nextInt(high - low) + low;
        T pivotValue = arr[pivot];
        while (low <= high) {
            while (!arr[low].equals(pivotValue) && arr[low].
                    compareTo(pivotValue) < 0) {
                low++;
            }
            while (!arr[high].equals(pivotValue) && arr[high].compareTo(
                    pivotValue) > 0) {
                high--;
            }
            if (low <= high) {
                swap(arr, low, high);
                low++;
                high--;
            }
        }
        if (start < high) {
            quicksort(arr, r, start, high);
        }
        if (low < end) {
            quicksort(arr, r, low, end);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(
            T[] arr, Random r) {
        if (arr == null || arr.length == 0 || arr.length == 1) { return; }
        quicksort(arr, r, 0, arr.length - 1);
    }

    /**
     * Recursive method used in merge sort to merge two sub arrays
     * @param tempArray array that is being sorted
     * @param lowId the index of first position in the sub array being sorted
     * @param highId the index of the first position in the 2nd half of
     *               the sub array being sorted
     * @param max the last index in the sub array being sorted
     * @param <T> generic type extends comparable
     */
    private <T extends Comparable<? super T>> void merge(
            T[] tempArray, int lowId, int highId, int max) {
        int size = max - lowId + 1;
        T[] workingArr = (T[]) new Comparable[size];
        int start = lowId;
        int current = lowId;
        int mid = highId - 1;
        while (lowId <= mid && highId <= max) {
            if (tempArray[lowId].compareTo(tempArray[highId]) <= 0) {
                workingArr[current - start] = tempArray[lowId];
                current++;
                lowId++;
            } else {
                workingArr[current - start] = tempArray[highId];
                current++;
                highId++;
            }
        }
        while (lowId <= mid) {
            workingArr[current - start] = tempArray[lowId];
            current++;
            lowId++;
        }
        while (highId <= max) {
            workingArr[current - start] = tempArray[highId];
            current++;
            highId++;
        }
        for (int i = start; i < start + size; i++) {
            tempArray[i] = (T) workingArr[i - start];
        }
    }

    /**
     * Method used to recursively split up and merge sub arrays of the
     * array being sorted
     * @param tempArray the array being sorted
     * @param lowId the index of first position in the sub array being sorted
     * @param highId the index of the first position in the 2nd half of
     *               the sub array being sorted
     * @param <T> generic type extends comparable
     */
    private <T extends Comparable<? super T>> void mergeSort(
            T[] tempArray, int lowId, int highId) {
        if (lowId == highId) {
            return;
        } else {
            int mid = (lowId + highId) / 2;
            mergeSort(tempArray, lowId, mid);
            mergeSort(tempArray, mid + 1, highId);
            merge(tempArray, lowId, mid + 1, highId);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        if (arr == null || arr.length == 0 || arr.length == 1) { return; }
        mergeSort(arr, 0, arr.length - 1);
    }

    @Override
    public int[] radixsort(int[] arr) {
        LinkedList<Integer>[] bucket = new LinkedList[19];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new LinkedList<Integer>();
        }
        int place = 1;
        for (int i = 0; i < 10; i++, place *= 10) {
            for (int j = 0; j < arr.length; j++) {
                bucket[((arr[j] / place) % 10) + 9].add(arr[j]);
            }
            int current = 0;
            for (int j = 0; j < bucket.length; j++) {
                Integer temp = null;
                while ((temp = bucket[j].poll()) != null) {
                    arr[current++] = temp;
                }
                bucket[j].clear();
            }
        }
        return arr;
    }

}
