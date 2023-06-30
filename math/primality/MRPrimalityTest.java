/*
 * 밀러-라빈 소수판별법
 * https://ko.wikipedia.org/wiki/밀러-라빈_소수판별법
 */


import java.util.Scanner;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;


public class MRPrimalityTest
{
  public static void main(String[] args)
  {
    clearScreen();

    System.out.println("");
    System.out.println("********************************************************************************");
    System.out.println("* 밀러-라빈 소수판별법 ");
    System.out.println("********************************************************************************");
    System.out.println("");

    System.out.print("  자연수 입력: ");

    final Scanner sc = new Scanner(System.in);
    final BigInteger bignum = sc.nextBigInteger();
    final long num = bignum.longValue();

    System.out.println("");
    System.out.println(String.format("BigNum: %s, Num: %d \n", bignum.toString(), num));
    System.out.println("");

    // 10번 반복하여 검사 수행
    final long beginTime = System.nanoTime();
    final boolean result = isPrimality(num, 10);
    final long endTime = System.nanoTime();

    // 나노초 -> 밀리초
    final float msec = (endTime - beginTime) / 1000000.0f;

    if ( result )
      System.out.println(String.format("소수여부: 소수임 [%.4f ms]", msec));
    else
      System.out.println(String.format("소수여부: 소수아님 [%.4f ms]", msec));

    System.out.println("\n********************************************************************************");
    System.out.println("");
  }


  // 화면 청소
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  // 입력으로 주어진 수가 소수인지 아닌지 판별
  // 숫자 'n'에 대해 소수판별법을 'k'번 실행하여 결정
  private static boolean isPrimality(long n, int k)
  {
    if ( 2L == n )
      return true;

    if ( (1 == n) || (0 == (n & 1)) )
      return false;

    long d = n - 1L;

    while ( 0 == (d & 1) )
      d >>= 1;

    for ( int i = 0; i < k; ++i )
    {
      long a = ThreadLocalRandom.current().nextLong(n - 2L) + 1L;
      long t = d;
      long y = calcMod(a, t, n);

      while ( ((n - 1L) != t) && (1 != y) && ((n - 1L) != y) )
      {
        // y = (y * y) % n;
        // y = ((y % n) * (y % n)) % n;
        y = (Double.valueOf(Math.pow((y % n), 2))).longValue() % n;
        t <<= 1;
      }

      if ( (y != (n - 1L)) && (0 == (t & 1L)) )
        return false;
    }

    return true;
  }


  private static long calcMod(long base, long power, long mod)
  {
    long result = 1L;

    while ( 0L < power )
    {
      if ( 1L == (power & 1L) )
      {
        // result = (result * base) % mod;
        result = ((result % mod) * (base % mod)) % mod;
      }

      // base = (base * base) % mod;
      // base = ((base % mod) * (base % mod)) % mod;
      base = (Double.valueOf(Math.pow((base % mod), 2))).longValue() % mod;
      power >>= 1;
    }

    return result;
  }

  // End of MRPrimalityTest.class
}

// End of MRPrimalityTest.java
