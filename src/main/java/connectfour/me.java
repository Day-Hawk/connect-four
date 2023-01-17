package connectfour;

import java.util.Arrays;

public class me {

  public static void main(String[] args) {
    System.out.println(arrayCount9(new int[]{5, 34, 3, 456, 56, 90, 4}));
  }

  public static void string(int i) {
    System.out.println("Wert: " + i);
  }

  public static int check(int i) {
    if (i >= 10) {
      throw new IllegalArgumentException("Wert liegt auserhalb des bereiches.");
    }
    return i;
  }

  public static boolean arrayCount9(int[] nums) {
    return (int) Arrays.stream(Arrays.copyOfRange(nums, 0, Math.min(4, nums.length)))
      .filter(value -> value == 9)
      .count() > 0;
  }

  /**
   * Todo cheatsheet
   *
   * @param nums
   * @return
   */

  public boolean array123(int[] nums) {
    // Note: iterate < length-2, so can use i+1 and i+2 in the loop
    for (int i = 0; i < (nums.length - 2); i++) {
      if (nums[i] == 1 && nums[i + 1] == 2 && nums[i + 2] == 3) return true;
    }
    return false;
  }

  public int stringMatch(String a, String b) {
    int counter = 0;
    for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
      if (i >= a.length() || i >= b.length()) {
        continue;
      }

      if (a.charAt(i) == b.charAt(i)) {
        if (i > 0 && a.charAt(i - 1) == a.charAt(i - 1)) {
          continue;
        }
        counter++;
      }
    }
    return counter;
  }

  public String stringX(String str) {
    if (str == null || str.isEmpty()) {
      return "";
    }

    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (!(i == 0 || i == str.length() - 1) && str.charAt(i) == 'x') {
        continue;
      }
      stringBuilder.append(str.charAt(i));
    }
    return stringBuilder.toString();
  }

  public String altPairs(String str) {
    if (str == null || str.length() < 3) {
      return str;
    }

    final StringBuilder stringBuilder = new StringBuilder();
    int currentIndex = 0;
    for (int i = 0; i < str.length(); i++) {
      if(currentIndex < str.length()) {
        stringBuilder.append(str.charAt(currentIndex));
      }
      currentIndex = this.nextIndex(currentIndex);
    }

    return stringBuilder.toString();
  }

  private int nextIndex(int current) {
    return current % 2 == 0 ? current + 1 : current + 3;
  }

  public void test() {

  }

}
