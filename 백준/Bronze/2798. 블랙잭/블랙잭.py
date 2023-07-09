N, M = map(int,input().split())
list = list(map(int,input().split()))
result = 0
# N장중에서 3장 선택
for N1 in range(N):
  for N2 in range(N1+1,N): #N값이 중복되서 중복된 범위에서 똑같은 카드를 뽑지 않도록
    for N3 in range(N2+1,N):
      Max = list[N1] + list[N2] + list[N3]
      if Max <= M and Max > result:
       result = Max

print(result)