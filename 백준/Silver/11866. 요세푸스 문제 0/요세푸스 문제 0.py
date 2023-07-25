from collections import deque

N,K = map(int,input().split())
ppl = deque([])
dead = deque([])

for i in range(1,N+1) :
    ppl.append(i)
i = 0
while len(ppl) != 0:
    for i in range (K-1):
        ppl.append(ppl.popleft())  
    dead.append(ppl.popleft())

print("<",end='')
print(*dead,sep=', ',end='')
print(">")