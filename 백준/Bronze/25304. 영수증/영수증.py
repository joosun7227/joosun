X = int(input())
N = int(input())
sum = 0

# X= 총금액 , N = 종류, a= 금액 b=수량
for i in range(1,N+1) :
  i += 1
  a,b = map(int,input().split())
  n = a*b
  sum = sum + n

if sum == X :
  print("Yes")
else :
  print("No")