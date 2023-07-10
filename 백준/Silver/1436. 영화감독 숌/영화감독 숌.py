N = int(input())
lst = [666,1666]
count = 2  # 종말의 수를 찾은 횟수를 세는 변수
num = 1667  # 종말의 수를 생성하기 위한 변수, 초기값은 666

while N > count :  # 종료 조건을 만날 때까지 반복합니다.
    if '666' in str(num):
        lst.append(num)  # num을 문자열로 변환하여 '666'이 포함되어 있는지 확인합니다.
        count += 1  # '666'이 포함되어 있다면 count를 증가시킵니다.      
    num += 1  # 종말의 수를 생성하기 위해 num을 1씩 증가시킵니다.

print(lst[N-1])