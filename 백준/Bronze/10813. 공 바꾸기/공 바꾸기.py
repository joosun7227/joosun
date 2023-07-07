N, M = map(int,input().split())
basket = [num for num in range(1,N+1)]
for ix in range(M):
  i, j = map(int,input().split())
  ix -= 1
  basket[i-1],basket[j-1] = basket[j-1],basket[i-1]

print(*basket)