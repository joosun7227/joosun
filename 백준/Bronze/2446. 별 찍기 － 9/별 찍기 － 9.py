n = int(input())


for i in range(0, n):
  print(" "*(i)+"*"*(((n-i)*2)-1))
for i in range(n-1, 0, -1):
  print(" "*(i-1)+"*"*(((n-i)*2)+1))