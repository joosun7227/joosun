import sys

# 1번째 행렬 입력받기
N1,M1 = map(int,sys.stdin.readline().split())
mx1 = []
for i in range(N1):
     lst = list(map(int,sys.stdin.readline().split()))
     mx1.append(lst)

# 2번째 행렬 입력받기
N2,M2 = map(int,sys.stdin.readline().split())
mx2 = []
for i in range(N2):
     lst = list(map(int,sys.stdin.readline().split()))
     mx2.append(lst)

# 3번째 행렬 초기화 # 범위설정에 유의
mx3 = [[0 for _ in range(M2)] for _ in range(N1)]

for i in range(N1):
    for j in range(M2):
        for k in range(M1):
             mx3[i][j] += mx1[i][k]*mx2[k][j]

for i in mx3:           
    print(*i)