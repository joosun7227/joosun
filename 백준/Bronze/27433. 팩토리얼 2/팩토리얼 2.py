def my_factorial(n):
 if (n>1):
  return n * my_factorial(n-1)
 else:
  return 1

a = int(input())
print(my_factorial(a))