origin = [1,1,2,2,2,8]
given = list(map(int,input().split()))
new = []
for i in range(6):
  minus = origin[i] - given[i]
  minus = float(minus)
  minus = int(minus)
  new.append(minus)

print(*new)