# 2중리스트
N = int(input())
rank = []
bmi = []
num = 1
# 키랑 몸무게 리스트 만들기
for i in range(N):
  k,w = map(int,input().split())
  bmi.append([k,w])

# 키랑 몸무게 비교하기
for k in range(N):
    num = 1
    for w in range(N):
      if bmi[w][1] > bmi[k][1] and bmi[w][0] > bmi[k][0]:
        num += 1
    rank.append(num)
print(*rank)  