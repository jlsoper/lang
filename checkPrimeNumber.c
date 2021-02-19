#include <stdio.h>


int main()
{
  int n, i, flag = 0;

  printf("Enter a positive integer: ");
  scanf("%d", &n);

  for (i = 2; i <= n / 2; ++i)
  {
    // condition for non-prime
    if (n % i == 0)
    {
      flag = 1;
      break;
    }
  }

  if ((n == 0) || (n == 1))
  {
    printf("%d is neither prime nor composite.", n);
  }
  else
  {
    printf("\n");

    if (flag == 0)
      printf("%d is a prime number.", n);
    else
      printf("%d is not a prime number.", n);
  }

  printf("\n");

  printf("\n");
  return 0;
}

