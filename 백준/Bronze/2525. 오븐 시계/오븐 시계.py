a,b = map(int,input().split())
c = int(input())
# 분에 추가 시간 더하기
b = b + c
# 60분이상 되지 않으면 그냥 출력해도 됨
if b < 60 :
    print(a, b)
# 60분이상 일때, a에 몫을 더하고 b에 나머지 출력
elif b >= 60 :
    aa = b//60
    a = a + aa
    a = int(a%24)
    b = int(b%60)
    # a는 24일때 0, b는 60일때 0
    if a == 24:
        a = 0
        print(a, b)
    elif b == 60:
        b = 0
        print(a, b)
    else :
        print(a, b)