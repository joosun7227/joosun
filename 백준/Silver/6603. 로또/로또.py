def backtrack(x):
    if len(temp) == 6:
        print(*temp)
        return
    for i in range(x, len(N)):
        if N[i] not in temp:
            temp.append(N[i])
            backtrack(i)
            temp.pop()
while True:
    N = list(map(int,input().split()))
    if N[0] == 0:
        break
    K = N.pop(0)
    temp = []
    backtrack(0)
    print()