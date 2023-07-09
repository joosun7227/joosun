result = []
# 정수 받기
for i in range(10): 
  i += 1
  N = int(input())
  # 리스트에서 꺼내서 42로 나누기
  N = N%42 
  if N not in result:
    result.append(N)
  else :
    pass
num = len(result)
print(num)