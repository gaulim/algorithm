/*
 * 최대공약수 및 최소공배수 구하기 (유클리드 호제법)
 * https://ko.wikipedia.org/wiki/유클리드_호제법
 */


#include <stdio.h>
#include <time.h>

/* 운영체제에 따른 화면 지우기 전처리기 */
#if defined(_WIN64) || defined(_WIN32)
#include <conio.h>
#else
#define clrscr() printf("\e[1;1H\e[2J")
#endif


unsigned long long gcd_minus(unsigned long long a, unsigned long long b);     /* GCD 뺄셈 연산 */
unsigned long long gcd_modulus(unsigned long long a, unsigned long long b);   /* GCD 나머지 연산 (반복문 사용) */
unsigned long long gcd_recursion(unsigned long long a, unsigned long long b); /* GCD 나머지 연산 (재귀 호출 사용) */
unsigned long long lcm(unsigned long long a, unsigned long long b);           /* LCM */


int main(int argc, char const *argv[])
{
  unsigned long long num1, num2;
  unsigned long long lcm_val = 0;
  unsigned long long gcd[3] = { 0, 0, 0 };

  double times[4] = { 0.0, 0.0, 0.0, 0.0 };
  clock_t clocks[4] = { 0, 0, 0, 0 };
  clock_t begin;

  clrscr();

  puts("");
  puts("********************************************************************************");
  puts("* 최대공약수 및 최소공배수 구하기 (유클리드 호제법) ");
  puts("********************************************************************************");
  puts("");

  printf("  자연수 입력1: ");
  scanf("%llu", &num1);

  printf("  자연수 입력2: ");
  scanf("%llu", &num2);

  /* GCD 뺄셈 연산 (숫자 2개 차가 큰 경우 매우 오래 걸리므로 주석처리) */
  /*
  begin = clock();
  gcd[0] = gcd_minus(num1, num2);
  clocks[0] = clock() - begin;
  times[0] = (double)clocks[0] / CLOCKS_PER_SEC;
  */

  /* GCD 나머지 연산 (반복문 사용) */
  begin = clock();
  gcd[1] = gcd_modulus(num1, num2);
  clocks[1] = clock() - begin;
  times[1] = (double)clocks[1] / CLOCKS_PER_SEC;

  /* GCD 나머지 연산 (재귀 호출 사용) */
  begin = clock();
  gcd[2] = gcd_recursion(num1, num2);
  clocks[2] = clock() - begin;
  times[2] = (double)clocks[2] / CLOCKS_PER_SEC;

  /* LCM */
  begin = clock();
  lcm_val = lcm(num1, num2);
  clocks[3] = clock() - begin;
  times[3] = (double)clocks[3] / CLOCKS_PER_SEC;

  puts("");
  /*
  printf("GCD_MINUS     : (%llu, %llu) = %llu (%.3lfs) \n",
                          num1, num2, gcd[0], clocks[0], times[0] );
  */
  printf("GCD_MODULUS   : (%llu, %llu) = %llu [%ld clock, %.3lf sec] \n",
                          num1, num2, gcd[1], clocks[1], times[1] );
  printf("GCD_RECURSION : (%llu, %llu) = %llu [%ld clock, %.3lf sec] \n",
                          num1, num2, gcd[2], clocks[2], times[2] );
  printf("LCM           : (%llu, %llu) = %llu [%ld clock, %.3lf sec] \n",
                          num1, num2, lcm_val, clocks[3], times[3] );

  puts("\n********************************************************************************");
  puts("");

  return 0;
}


/* 뺄셈 연산 */
unsigned long long gcd_minus(unsigned long long a, unsigned long long b)
{
  unsigned long long tmp;

  while ( b )
  {
    if ( a < b )
    {
      tmp = a;
      a = b;
      b = tmp;
    }

    a -= b;
  }

  return a;
}


/* 나머지 연산 (반복문 사용) */
unsigned long long gcd_modulus(unsigned long long a, unsigned long long b)
{
  unsigned long long tmp;

  while ( b )
  {
    tmp = a % b;
    a = b;
    b = tmp;
  }

  return a;
}


/* 나머지 연산 (재귀 호출 사용) */
unsigned long long gcd_recursion(unsigned long long a, unsigned long long b)
{
  return b ? gcd_recursion(b, (a % b)) : a;
}


/* 최소공배수 [GCD(a,b) = g 일 때 LCM(a,b) = g(a/g)(b/g)] */
unsigned long long lcm(unsigned long long a, unsigned long long b)
{
  unsigned long long gcd_val = gcd_recursion(a, b);
  return gcd_val * (a / gcd_val) * (b / gcd_val);
}

/* End of euclidean.c */

