a,b = input().split()
first = []
second = []
# 두 수 거꾸로 배열하기
for i in range(1,len(str(a))+1):
    first.append(str(a)[-i])
for j in range(1,len(str(b))+1):
    second.append(str(b)[-j])

first = int(''.join(first))
second = int(''.join(second))

print(max(first,second))