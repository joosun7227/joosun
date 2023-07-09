N, M = map(int,input().split())

basket = [0 for i in range(N)]

for _ in range(M):
    i, j, k = map(int,input().split())
    i -= 1
    for num in range(i,j) :
        basket[num] = k

print(*basket)