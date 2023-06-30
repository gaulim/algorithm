/*
 * 매우 큰 숫자 범위에서 임의의 수 구하기
 * https://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
 */


import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class LongRandom
{
  public static void main(String[] args)
  {
    clearScreen();

    System.out.println("");
    System.out.println("********************************************************************************");
    System.out.println("* 매우 큰 숫자 범위에서 임의의 수 구하기 ");
    System.out.println("********************************************************************************");
    System.out.println("");

    System.out.print("  자연수 최대값 입력: ");
    final Scanner scMax = new Scanner(System.in);
    final BigInteger bignumMax = scMax.nextBigInteger();
    final long numMax = bignumMax.longValue();

    System.out.print("  자연수 최소값 입력: ");
    final Scanner scMin = new Scanner(System.in);
    final BigInteger bignumMin = scMin.nextBigInteger();
    final long numMin = bignumMin.longValue();

    System.out.println("");
    System.out.println(String.format("최대값 - BigNum: %s, Num: %d \n", bignumMax.toString(), numMax));
    System.out.println(String.format("최소값 - BigNum: %s, Num: %d \n", bignumMin.toString(), numMin));
    System.out.println("");

    long beginTime, endTime;
    float[] msecs = { 0.0f, 0.0f, 0.0f };
    
    long[] rnums = { 0L, 0L, 0L };
    
    final Random rand = new Random();
    
    // 'ThreadLocalRandom' 클래스 사용
    beginTime = System.nanoTime();
    rnums[0] = getLongRandomByTLR(numMax);
    endTime = System.nanoTime();
    msecs[0] = (endTime - beginTime) / 1000000.0f;

    // 'nextDouble()' 메서드 사용
    beginTime = System.nanoTime();
    rnums[1] = getLongRandomByRD(rand, numMax, numMin);
    endTime = System.nanoTime();
    msecs[1] = (endTime - beginTime) / 1000000.0f;

    // 'nextLong()' 메서드 사용
    beginTime = System.nanoTime();
    rnums[2] = getLongRandomByRL(rand, numMax, numMin);
    endTime = System.nanoTime();
    msecs[2] = (endTime - beginTime) / 1000000.0f;

    System.out.println(String.format("Random(TLR): %d [%.4f ms]", rnums[0], msecs[0]));
    System.out.println(String.format("Random(RD) : %d [%.4f ms]", rnums[1], msecs[1]));
    System.out.println(String.format("Random(RL) : %d [%.4f ms]", rnums[2], msecs[2]));

    System.out.println("\n********************************************************************************");
    System.out.println("");
  }


  // 화면 청소
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  /**
   * <p>'ThreadLocalRandom' 클래스 사용</p>
   *
   * @param max 최대값
   *
   * @return 0L ~ (최대값 - 1) 아무 정수
   *
   * @throws IllegalArgumentException
   */
  public static long getLongRandomByTLR(long max)
  {
    if ( 1L > max ) throw new IllegalArgumentException("max < 1");
    return ThreadLocalRandom.current().nextLong(max);
  }


  /**
   * <p>'nextDouble()' 메서드 사용</p>
   *
   * @param rand Random 객체
   * @param max 최대값
   * @param min 최소값
   *
   * @return 최소값 ~ (최대값 - 1) 아무 정수
   *
   * @throws IllegalArgumentException
   */
  public static long getLongRandomByRD(Random rand, long max, long min)
  {
    if ( 0L > min ) throw new IllegalArgumentException("1 > min");
    if ( max < min ) throw new IllegalArgumentException("max < min");
    if ( max == min ) return max;
    
    return min + (long)(rand.nextDouble() * (max - min));
  }


  /**
   * <p>'nextLong()' 메서드 사용</p>
   *
   * @param rand Random 객체
   * @param max 최대값
   * @param min 최소값
   *
   * @return 최소값 ~ (최대값 - 1) 아무 정수
   *
   * @throws IllegalArgumentException
   */
  public static long getLongRandomByRL(Random rand, long max, long min)
  {
    if ( 0L > min ) throw new IllegalArgumentException("1 > min");
    if ( max < min ) throw new IllegalArgumentException("max < min");
    if ( max == min ) return max;
    
    long n = rand.nextLong();
    
    // abs (use instead of Math.abs, which might return min value):
    n = (Long.MIN_VALUE == n) ? 0L : ((0L > n) ? -n : n);
    
    // limit to range:
    n %= (max - min);
    
    return min + n;
  }

  // End of LongRandom.class
}

// End of LongRandom.java
