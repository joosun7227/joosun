n = int(input())

for i in range(0, n):
  if i == 0:
    print("*"*(n-i))
  else:
    print(" "*(i-1),"*"*(n-i))