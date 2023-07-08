import sys

N = int(sys.stdin.readline())
num =[]
for i in range(N):
  i -= 1
  in_put = int(sys.stdin.readline())
  num.append(in_put)

num.sort()

for i in range(N):
  print(num[i])