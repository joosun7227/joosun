num = int(input())
for i in range(num+1):
    nums = list(map(int,str(i))) 
    # i를 문자열로 바꾼다음 map으로 하나씩 풀어주고
    # 그것들을 다시 숫자로 바꿔준다음 list에 집어넣음
    sums = (sum(nums)) + i 
    # i리스트들을 전부 숫자를 더해준 다음 i랑 더해지면 
    # i는 생성자임
    if sums == num:
        print(i)
        break
    if i == num:
        print(0)