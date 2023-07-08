N = int(input())
num =[]
for i in range(N):
  i -= 1
  in_put = int(input())
  num.append(in_put)

num.sort()

for i in range(N):
  print(num[i])