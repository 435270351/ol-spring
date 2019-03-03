package demo;

import java.util.Arrays;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-03-03
 * @since (版本)
 */
public class ArDemo {

    public void partitionArray(int[] nums) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return;
        }

        Arrays.sort(nums);

        int evenCount = 0;
        for (int num : nums) {
            if (num % 2 == 0) {
                evenCount++;
            }
        }

        int evenIndex = nums.length - evenCount;
        int oddIndex = evenIndex - 1;

        for (int i = nums.length - 1; i >= evenIndex; i--) {
            if (nums[i] % 2 == 0) {
                continue;
            }
            for (int j = i - 1; j >= 0; j--) {
                int num = nums[j];
                if (num % 2 == 0) {
                    nums[j] = nums[oddIndex];
                    nums[oddIndex--] = nums[i];
                    nums[i] = num;
                    break;
                }
            }
        }

    }

    public void partitionArray2(int[] nums) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            while (left < right && nums[left] % 2 != 0) {
                left++;
            }

            while (left < right && nums[right] % 2 == 0) {
                right--;
            }

            int val = nums[left];
            nums[left] = nums[right];
            nums[right] = val;

            left++;
            right--;
        }
    }

    public int findPosition(int[] nums, int target) {
        // write your code here
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int val = nums[mid];

            if (val == target) {
                return mid;
            } else if (val > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        ArDemo arDemo = new ArDemo();

        int[] arr = { 1,2,2,4,5,5 };
        System.out.println(arDemo.findPosition(arr,6));
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}
