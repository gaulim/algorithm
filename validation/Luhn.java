/*
 * 신용카드번호 검증 Luhn (MOD 10) 알고리즘
 * http://www.getcreditcardnumbers.com
 */


import java.util.Scanner;


public class Luhn
{
  public static void main(String[] args)
  {
    clearScreen();

    System.out.println("");
    System.out.println("********************************************************************************");
    System.out.println("* 신용카드번호 검증 Luhn (MOD 10) 알고리즘 ");
    System.out.println("********************************************************************************");
    System.out.println("");

    System.out.print("  신용카드번호 입력: ");
    final Scanner scCardNum = new Scanner(System.in);
    final String cardNum = scCardNum.nextLine();
    scCardNum.close();

    System.out.println("");
    System.out.println(String.format("카드번호: %s\n", cardNum));
    System.out.println("");

    // 신용카드번호 검증
    final long beginTime = System.nanoTime();
    final boolean result = isValidCreditCardNum(cardNum);
    final long endTime = System.nanoTime();

    // 나노초 -> 밀리초
    final float msec = (endTime - beginTime) / 1000000.0f;

    if ( result )
      System.out.println("신용카드번호 검증: 올바름!");
    else
      System.out.println("신용카드번호 검증: 잘못됨!");

    System.out.println("\n*******************************************************************************");
    System.out.println("");
  }


  // 화면 청소
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }


  /**
   * 신용/체크카드 번호 유효성 검사 (룬 공식 - LUHN Formula == 모듈러스 10 == mod 10)
   * 참고: https://ko.wikipedia.org/wiki/%EC%B9%B4%EB%93%9C_%EB%B2%88%ED%98%B8%EC%9D%98_%EA%B5%AC%EC%84%B1
   *
   * @param cardNum 카드번호
   *
   * @return true 유효, false 무효
   */
  public static boolean isValidCreditCardNum(final String cardNum)
  {
      if ( null == cardNum )
          return false;

      final String number = cardNum.trim().replace("-", "").replace(" ", "");
      final int len = number.length();

      if ( (13 > len) || (19 < len) )
          return false;

      int num, sum = 0;

      // 마지막 숫자는 Check Digit 이므로 계산에서 제외한다.
      for ( int ii = (len - 2), loop = 1; ii >= 0; --ii, ++loop )
      {
          num = number.charAt(ii) - '0';

          // Check Digit 바로 왼쪽부터 한 자리씩 건너뛰며 2를 곱한다.
          if ( 0 != (loop % 2) )
          {
              num *= 2;

              // 2를 곱한 값이 9보다 크면 9를 빼준다.
              // 각 자리수를 각각 더해도 결과는 같다.
              // 14: (1 + 4) == (14 - 9)
              if ( 9 < num )
                  num -= 9;
          }

          sum += num;
      }

      final int checkDigit = (sum * 9) % 10;
      final int lastDigit = number.charAt(len - 1) - '0';

      // 마지막 자리수와 계산 결과가 같으면 유효함.
      return ( lastDigit == checkDigit );
  }

  // End of Luhn.class
}

// End of Luhn.java
