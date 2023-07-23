from collections import deque
import sys

N = int(sys.stdin.readline())
card = deque()
for i in range(1,N+1) :
    card.append(i)

for m in range(N+1):
    if len(card) > 1:
      card.popleft()
      last = card[0]
      card.append(int(last))
      card.popleft()

print(*card)