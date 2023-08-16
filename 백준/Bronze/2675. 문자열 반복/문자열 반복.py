N = int(input())
for i in range(N):
    R,word = input().split()
    R = int(R)
    result = ''
    for j in range(len(word)):
        result += str(word[j]*R)
    print(result)