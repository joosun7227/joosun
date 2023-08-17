# 색종이수 N입력받음
N = int(input())
# 도화지 생성
paper = [[0 for m in range(100)] for l in range(100)]

# for 문안에 색종이 붙이기
for i in range(N):
    x,y = map(int,input().split())
    for j in range(x,x+10):
        for k in range(y,y+10):
          paper[j][k] = 1

count = 0
for a in range(100):
   for b in range(100):
      count = count + paper[a][b] 

print(count)