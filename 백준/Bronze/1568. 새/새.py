N = int(input())
K = 1
count = 0
while N != 0:
    N = N - K 
    K += 1
    count += 1
    if K > N :
        K = 1

print(count)