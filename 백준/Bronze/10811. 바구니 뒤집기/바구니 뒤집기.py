N,M = map(int,input().split())
bucket = [z for z in range(1, N+1)]

for itmes in range(M):
  i,j = map(int,input().split())
  for number in range ((j-i)//2+1):
    bucket[i-1+number], bucket[j-1-number] = bucket[j-1-number], bucket[i-1+number]

for i in range(N):
  print(bucket[i],end=' ')