import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr1 = {0,32131,231,2,13,2,4534,65,345,3,2342,3,567,56756756,65756,56656,657};
        quicksort(arr1 , 1 ,arr1.length - 1);
        System.out.println(Arrays.toString(arr1));
    }

    public static int partition(int[] arr , int low , int high){
        int pivotkey = arr[low];

        while (low < high){
            while (low < high && arr[high] >= pivotkey){
                high--;
            }
            arr[low]=arr[high];
            while (low < high && arr[low] <= pivotkey){
                low++;
            }
            arr[high]=arr[low];
        }

        arr[low]=pivotkey;
        return low;
    }

    public static void quicksort(int[] arr , int low , int high ){
        if (low < high){
            int pivotloc = partition(arr , low , high);
            quicksort(arr , low , pivotloc - 1);
            quicksort(arr , pivotloc + 1 , high);
        }else{
            return;
        }
    }
}
