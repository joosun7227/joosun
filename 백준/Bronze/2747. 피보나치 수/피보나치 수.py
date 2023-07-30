n = int(input())

list = [0, 1]
while len(list) < n+1:
    next_num = list[-1] + list[-2]
    list.append(next_num)

print(list[-1])