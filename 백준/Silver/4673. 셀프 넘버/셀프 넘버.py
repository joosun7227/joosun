# num 리스트 작성 1!~ 10001이 있음
num = set()
minus = set()
result = set()
for i in range(1,10001):
    num.add(i)

# 1부터 시작해서 생성자를 더이상 제거할 수 없으면 멈춤
for i in range(1,10001):
    nums = list(map(int,str(i))) 
    # i를 문자열로 바꾼다음 map으로 하나씩 풀어주고
    # 그것들을 다시 숫자로 바꿔준다음 list에 집어넣음
    sums = (sum(nums)) + i 
    # i리스트들을 전부 숫자를 더해준 다음 i랑 더해지면 
    # sums는 생성자가 있는 숫자임
    # 이를마이너스 셋에 더해주고
    minus.add(sums)

result = num - minus
result = list(result)
result.sort()
for i in range(len(result)):
  print(result[i])