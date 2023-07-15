paper = [[0 for _ in range(100)] for _ in  range(100)]
# print(paper)

# 색종이수 입력
N = int(input())

# 색종이 위치
for i in range(N):
  left,bottom = map(int,input().split())
  for y in range(bottom,bottom+10):
    for x in  range(left,left+10):
      paper[y][x] = 1 #왜 페이퍼 yx인가

result = 0
for i in range(len(paper)) :
  for k in range(len(paper[i])) :
    if paper[i][k] == 1:
      result += 1

print(result)
    